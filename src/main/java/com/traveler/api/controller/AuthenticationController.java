package com.traveler.api.controller;

import com.traveler.api.controller.dto.AuthenticationDto;
import com.traveler.api.controller.dto.LoginResponseDto;
import com.traveler.api.controller.dto.RegisterDto;
import com.traveler.api.entity.Usuario;
import com.traveler.api.infra.security.TokenService;
import com.traveler.api.repository.UsuarioRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.sql.SQLException;
import java.sql.Timestamp;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {

    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private UsuarioRepository repository;
    @Autowired
    private TokenService tokenService;

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody AuthenticationDto data) {
        try {
            var usernamePassword = new UsernamePasswordAuthenticationToken(data.nome(), data.senha());
            var auth = this.authenticationManager.authenticate(usernamePassword);

            // gerar o token para utilizar nas próximas requisições
            var token = tokenService.generateToken((Usuario) auth.getPrincipal());

            return ResponseEntity.ok(new LoginResponseDto(token));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Credenciais inválidas.");
        }
    }

    @PostMapping("/registrar")
    public ResponseEntity<?> register(@RequestBody RegisterDto data, BindingResult result) {
        if (result.hasErrors()) {
            return ResponseEntity.badRequest().body(result.getAllErrors());
        }

        if(data.senha().isBlank()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("O campo 'senha' é obrigatório");
        }

        String encryptedPassword = new BCryptPasswordEncoder().encode(data.senha());
        Usuario newUser = new Usuario(data.nome(), data.email(), encryptedPassword);
        newUser.setDataCriacao(new Timestamp(System.currentTimeMillis()));


        try {
            if(newUser.getNome().isBlank()) return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("O campo 'nome' é obrigatório");
            if(newUser.getEmail().isBlank()) return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("O campo 'email' é obrigatório");

            this.repository.save(newUser);
            return ResponseEntity.ok().body("Usuário registrado com sucesso!");
        } catch (DataIntegrityViolationException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("E-mail já registrado.");
        } catch (DataAccessException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao acessar os dados.");
        }

    }
}