package com.traveler.api.controller;

import com.traveler.api.controller.dto.AtualizarSenhaDto;
import com.traveler.api.controller.dto.AtualizarUsuarioDto;
import com.traveler.api.controller.dto.CriarUsuarioDto;
import com.traveler.api.entity.Usuario;
import com.traveler.api.service.UsuarioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;


/**
 * Métodos para criação/exclusão/deleção/alteração do usuário no banco de dados.
 *
 * @author Gabriel Brocco de Oliveira
 * @since 17/09/2024
 */

@RestController
@RequestMapping("/usuario")
@Tag(name = "Usuário")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

//    @Operation(summary = "Criar um usuário", description = "Criar um novo usuario")
//    @PostMapping
//    public ResponseEntity<Usuario> criarUsuario(@RequestBody CriarUsuarioDto criarUsuarioDto) {
//
//        try {
//           var usuario =  usuarioService.criarUsuario(criarUsuarioDto);
//
////            return ResponseEntity.created(URI.create("/usuario/" + usuarioId.toString())).build();
//            return new ResponseEntity<>(usuario, HttpStatus.CREATED);
//        } catch (Exception e) {
//            return ResponseEntity.badRequest().build();
//        }
//    }

    @Operation(summary = "Buscar todos os usuários", description = "Buscar todos os usuários criados no banco de dados")
    @GetMapping
    public ResponseEntity<List<Usuario>> buscarUsuarios() {
        var usuarios = usuarioService.buscarUsuarios();

        return ResponseEntity.ok(usuarios);
    }

    @Operation(summary = "Buscar um usuário específico", description = "Buscar um usuário específico")
    @GetMapping("/{usuarioId}")
    public ResponseEntity<Usuario> buscarUsuarioPorId(@PathVariable("usuarioId") String usuarioId) {
        Optional<Usuario> usuario = usuarioService.buscarUsuarioPorId(usuarioId);

        if(usuario.isPresent()) {
            return ResponseEntity.ok(usuario.get());
        } else {
            return ResponseEntity.notFound().build();
        }

    }

    @Operation(summary = "Atualizar um usuário", description = "Atualizar dado de um usuario")
    @PutMapping("/{usuarioId}")
    public ResponseEntity<?> atualizarUsuario(@PathVariable("usuarioId") String usuarioId,
                                                    @RequestBody AtualizarUsuarioDto atualizarUsuarioDto) {
        try {

        usuarioService.atualizarUsuario(usuarioId, atualizarUsuarioDto);
        return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }

    }

    @Operation(summary = "Atualizar a senha do usuário", description = "Atualizar a senha de um usuário")
    @PutMapping("/{usuarioId}/senha")
    public ResponseEntity<?> atualizarSenha(@PathVariable("usuarioId") String usuarioId,
                                               @RequestBody AtualizarSenhaDto atualizarSenhaDto) {
        try {

        usuarioService.atualizarSenha(usuarioId, atualizarSenhaDto);
        return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }

    }

    @Operation(summary = "Deletar um usuário", description = "Deletar um usuário do banco de dados")
    @DeleteMapping("/{usuarioId}")
    public ResponseEntity<?> deletarUsuario(@PathVariable("usuarioId") String usuarioId) {
        try {

        usuarioService.deletarUsuario(usuarioId);
        return ResponseEntity.noContent().build();
        }  catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

}
