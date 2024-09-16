package com.traveler.api.service;

import com.traveler.api.controller.dto.CriarUsuarioDto;
import com.traveler.api.entity.Usuario;
import com.traveler.api.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    public Usuario criarUsuario(CriarUsuarioDto criarUsuarioDto) {

        var entity = new Usuario(
            criarUsuarioDto.nome(), criarUsuarioDto.email(), criarUsuarioDto.senha());



        return usuarioRepository.save(entity);


    }

}
