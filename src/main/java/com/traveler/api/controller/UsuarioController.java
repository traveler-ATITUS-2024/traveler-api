package com.traveler.api.controller;

import com.traveler.api.controller.dto.AtualizarSenhaDto;
import com.traveler.api.controller.dto.AtualizarUsuarioDto;
import com.traveler.api.controller.dto.CriarUsuarioDto;
import com.traveler.api.entity.Usuario;
import com.traveler.api.service.UsuarioService;
import jakarta.validation.Valid;
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
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    // criar usuário novo e retornar o json com os dados
    @PostMapping
    public ResponseEntity<Usuario> criarUsuario(@RequestBody CriarUsuarioDto criarUsuarioDto) {

        try {
           var usuario =  usuarioService.criarUsuario(criarUsuarioDto);

//            return ResponseEntity.created(URI.create("/usuario/" + usuarioId.toString())).build();
            return new ResponseEntity<>(usuario, HttpStatus.CREATED);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    // busca todos usuários criados no banco
    @GetMapping
    public ResponseEntity<List<Usuario>> buscarUsuarios() {
        var usuarios = usuarioService.buscarUsuarios();

        return ResponseEntity.ok(usuarios);
    }

    // busca um usuário específico pelo id
    @GetMapping("/{usuarioId}")
    public ResponseEntity<Usuario> buscarUsuarioPorId(@PathVariable("usuarioId") String usuarioId) {
        Optional<Usuario> usuario = usuarioService.buscarUsuarioPorId(usuarioId);

        if(usuario.isPresent()) {
            return ResponseEntity.ok(usuario.get());
        } else {
            return ResponseEntity.notFound().build();
        }

    }

    // atualiza o nome do usuário específico pelo id
    @PutMapping("/{usuarioId}")
    public ResponseEntity<Void> atualizarUsuario(@PathVariable("usuarioId") String usuarioId,
                                                    @RequestBody AtualizarUsuarioDto atualizarUsuarioDto) {
        usuarioService.atualizarUsuario(usuarioId, atualizarUsuarioDto);
        return ResponseEntity.noContent().build();

    }

    // atualiza a senha do usuário específico pelo id
    @PutMapping("/{usuarioId}/senha")
    public ResponseEntity<Void> atualizarSenha(@PathVariable("usuarioId") String usuarioId,
                                               @RequestBody AtualizarSenhaDto atualizarSenhaDto) {

        usuarioService.atualizarSenha(usuarioId, atualizarSenhaDto);
        return ResponseEntity.noContent().build();
    }

    // deleta o usuário pelo id
    @DeleteMapping("/{usuarioId}")
    public ResponseEntity<Void> deletarUsuario(@PathVariable("usuarioId") String usuarioId) {
        usuarioService.deletarUsuario(usuarioId);
        return ResponseEntity.noContent().build();
    }

}
