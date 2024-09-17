package com.traveler.api.service;

import com.traveler.api.controller.dto.AtualizarSenhaDto;
import com.traveler.api.controller.dto.AtualizarUsuarioDto;
import com.traveler.api.controller.dto.CriarUsuarioDto;
import com.traveler.api.entity.Usuario;
import com.traveler.api.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    public Usuario criarUsuario(CriarUsuarioDto criarUsuarioDto) {

        var entity = new Usuario(
            criarUsuarioDto.nome(), criarUsuarioDto.email(), criarUsuarioDto.senha());



        return usuarioRepository.save(entity);


    }

    public List<Usuario> buscarUsuarios() {
        return usuarioRepository.findAll();
    }

    public Optional<Usuario> buscarUsuarioPorId(String usuarioId) {
        return usuarioRepository.findById(Integer.parseInt(usuarioId) );
    }

    public void atualizarUsuario(String usuarioId, AtualizarUsuarioDto atualizarUsuarioDto) {
        var id = Integer.parseInt(usuarioId);
        var entity = usuarioRepository.findById(id);

        if(entity.isPresent()) {
            var usuario = entity.get();

            if (atualizarUsuarioDto.nome() != null) {
                usuario.setNome(atualizarUsuarioDto.nome());
            }

            usuarioRepository.save(usuario);
        }
    }

    public void atualizarSenha(String usuarioId, AtualizarSenhaDto atualizarSenhaDto) {
        var id = Integer.parseInt(usuarioId);
        var entity = usuarioRepository.findById(id);

        if(entity.isPresent()) {
            var usuario = entity.get();

            if (atualizarSenhaDto.senha() != null) {
                usuario.setSenha(atualizarSenhaDto.senha());
            }

            usuarioRepository.save(usuario);
        }
    }

    public void deletarUsuario(String usuarioId) {
        var id = Integer.parseInt(usuarioId);

        var usuarioExistente = usuarioRepository.findById(id);

        if (usuarioExistente.isPresent()) {
            usuarioRepository.deleteById(id);
        }
    }
}