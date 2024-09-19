package com.traveler.api.controller;

import com.traveler.api.controller.dto.CriarViagemDto;
import com.traveler.api.entity.Usuario;
import com.traveler.api.entity.Viagem;
import com.traveler.api.infra.security.CustomUserDetails;
import com.traveler.api.repository.UsuarioRepository;
import com.traveler.api.service.ViagemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/viagem")
public class  ViagemController {

    @Autowired
    private ViagemService viagemService;

    @Autowired
    private UsuarioRepository usuarioRepository;


    @PostMapping
    public ResponseEntity<Viagem> criarViagem(@RequestBody CriarViagemDto criarViagemDto, @AuthenticationPrincipal UserDetails userDetails) {
        try {

            Usuario usuario = (Usuario) usuarioRepository.findByNome(userDetails.getUsername());

            Viagem viagem = viagemService.criarViagem(criarViagemDto, usuario);

            return new ResponseEntity<>(viagem, HttpStatus.CREATED);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

}
