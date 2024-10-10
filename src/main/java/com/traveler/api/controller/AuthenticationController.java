package com.traveler.api.controller;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.traveler.api.controller.dto.*;
import com.traveler.api.entity.Usuario;
import com.traveler.api.infra.security.TokenService;
import com.traveler.api.repository.UsuarioRepository;
import com.traveler.api.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Optional;

@RestController
@RequestMapping("/auth")
@Tag(name = "Autenticação")
public class AuthenticationController {

    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private AuthService authService;
    @Autowired
    private UsuarioRepository repository;
    @Autowired
    private TokenService tokenService;
    @Value("${api.security.token.secret}")
    private String secret;

    @Operation(summary = "Realizar o login do usuário", description = "Realizar o login do usuário")
    @PostMapping("/login")
    public ResponseEntity login(@RequestBody AuthenticationDto data) {
        try {
            var usernamePassword = new UsernamePasswordAuthenticationToken(data.email(), data.senha());
            var auth = this.authenticationManager.authenticate(usernamePassword);

            // Gerar o token para utilizar nas próximas requisições
            var token = tokenService.generateToken((Usuario) auth.getPrincipal());

            return ResponseEntity.ok(new LoginResponseDto(token));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Credenciais inválidas.");
        }
    }

    @Operation(summary = "Realizar o registro de um novo usuário", description = "Realizar o registo de um novo usuário")
    @PostMapping("/registrar")
    public ResponseEntity<?> register(@RequestBody RegisterDto data, BindingResult result) {
        if (result.hasErrors()) {
            return ResponseEntity.badRequest().body(result.getAllErrors());
        }

        if (data.senha().isBlank()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("O campo 'senha' é obrigatório");
        }

        String encryptedPassword = new BCryptPasswordEncoder().encode(data.senha());
        Usuario newUser = new Usuario(data.nome(), data.email(), encryptedPassword);
        newUser.setDataCriacao(new Timestamp(System.currentTimeMillis()));


        try {
            if (newUser.getNome().isBlank())
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("O campo 'nome' é obrigatório");
            if (newUser.getEmail().isBlank())
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("O campo 'email' é obrigatório");

            this.repository.save(newUser);
            return ResponseEntity.ok().body("Usuário registrado com sucesso!");
        } catch (DataIntegrityViolationException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("E-mail já registrado.");
        } catch (DataAccessException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao acessar os dados.");
        }

    }

    @PostMapping("/esqueci-minha-senha")
    public ResponseEntity<?> esqueciMinhaSenha(@RequestBody EsqueciSenhaDto esqueciSenhaDto) {
        try {
            authService.esqueciMinhaSenha(esqueciSenhaDto.getEmail());
            return ResponseEntity.ok().body("E-mail de recuperação enviado com sucesso.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PostMapping("/redefinir-senha")
    public ResponseEntity<?> redefinirSenha(@RequestBody RedefinirSenhaDto data) {
        if (data.novaSenha().isBlank() || data.confirmarSenha().isBlank()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Os campos 'nova senha' e 'confirmar senha' são obrigatórios.");
        }
        if (!data.novaSenha().equals(data.confirmarSenha())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("As senhas não conferem");
        }

        try {
            String token = data.token();
            DecodedJWT decodedJWT = JWT.require(Algorithm.HMAC256(secret))
                    .withIssuer("auth-api")
                    .build()
                    .verify(token);

            String email = decodedJWT.getSubject();
            Usuario usuario = repository.findByEmail(email)
                    .orElseThrow(() -> new RuntimeException("Usuário não encontrado."));

            if (usuario == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuário não encontrado.");
            }

            String encryptedPassword = new BCryptPasswordEncoder().encode(data.novaSenha());

            usuario.setSenha(encryptedPassword);
            repository.save(usuario);

            return ResponseEntity.ok().body("Senha redefinida com sucesso.");

        } catch (JWTVerificationException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Token inválido ou expirado.");
        } catch (DataAccessException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao acessar os dados.");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }

    }

}
