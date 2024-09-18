package com.traveler.api.controller;

import com.traveler.api.controller.dto.CriarViagemDto;
import com.traveler.api.entity.Viagem;
import com.traveler.api.infra.security.CustomUserDetails;
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


    @PostMapping
    public ResponseEntity<Viagem> criarViagem(@RequestBody CriarViagemDto criarViagemDto, @AuthenticationPrincipal UserDetails userDetails) {
        try {

            Viagem viagem = viagemService.criarViagem(criarViagemDto);

            return new ResponseEntity<>(viagem, HttpStatus.CREATED);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

}
