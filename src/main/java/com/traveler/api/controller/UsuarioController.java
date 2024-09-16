package com.traveler.api.controller;

import com.traveler.api.controller.dto.CriarUsuarioDto;
import com.traveler.api.entity.Usuario;
import com.traveler.api.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/usuario")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

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

}
