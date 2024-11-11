package com.traveler.api.service;

import com.traveler.api.controller.dto.AtualizarSenhaDto;
import com.traveler.api.controller.dto.AtualizarUsuarioDto;
import com.traveler.api.controller.dto.CriarUsuarioDto;
import com.traveler.api.entity.Usuario;
import com.traveler.api.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Métodos para criação/exclusão/deleção/alteração do usuário no banco de dados.
 *
 * @author Gabriel Brocco de Oliveira
 * @since 17/09/2024
 */

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

//    public Usuario criarUsuario(CriarUsuarioDto criarUsuarioDto) {
//
//        var entity = new Usuario(
//                criarUsuarioDto.nome(), criarUsuarioDto.email(), criarUsuarioDto.senha());
//
//
//
//        return usuarioRepository.save(entity);
//
//
//    }

    public List<Usuario> buscarUsuarios() {
        return usuarioRepository.findAll();
    }

    public Optional<Usuario> buscarUsuarioPorId(String usuarioId) {
        return usuarioRepository.findById(Long.parseLong(usuarioId) );
    }

    public void atualizarUsuario(String usuarioId, AtualizarUsuarioDto atualizarUsuarioDto) throws Exception {
        var id = Long.parseLong(usuarioId);
        var entity = usuarioRepository.findById(id);

        if(entity.isPresent()) {
            var usuario = entity.get();

            if (atualizarUsuarioDto.nome() != null) {
                usuario.setNome(atualizarUsuarioDto.nome());
            }

            usuarioRepository.save(usuario);
        } else {
            throw new Exception("Usuário não encontrado!");
        }
    }

   public void atualizarSenha(String usuarioId, AtualizarSenhaDto atualizarSenhaDto) throws Exception{
        var id = Long.parseLong(usuarioId);
        var entity = usuarioRepository.findById(id);

        if(entity.isPresent()) {
            var usuario = entity.get();
            BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

            if (atualizarSenhaDto.senhaAtual() != null && passwordEncoder.matches(atualizarSenhaDto.senhaAtual(), usuario.getSenha())) {
                String encryptedPassword = passwordEncoder.encode(atualizarSenhaDto.senha());
                usuario.setSenha(encryptedPassword);
                usuarioRepository.save(usuario);
            } else {
                throw new Exception("Senha atual incorreta!");
            }

        }  else {
            throw new Exception("Usuário não encontrado!");
        }
    }

    public void deletarUsuario(String usuarioId) throws Exception{
        var id = Long.parseLong(usuarioId);

        var usuarioExistente = usuarioRepository.findById(id);

        if (usuarioExistente.isPresent()) {
            usuarioRepository.deleteById(id);
        } else {
            throw new Exception("Usuário não encontrado!");
        }
    }
}
