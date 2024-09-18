package com.traveler.api.controller;

import com.traveler.api.entity.Viagem;
import com.traveler.api.service.ViagemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/viagem")
public class  ViagemController {

    @Autowired
    private ViagemService viagemService;

//    @PostMapping
//    public ResponseEntity<Viagem> criarViagem(@RequestBody CriarViagemDto criarViagemDto) {
//
//    }
}
