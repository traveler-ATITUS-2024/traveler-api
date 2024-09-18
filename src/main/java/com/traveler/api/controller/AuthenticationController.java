package com.traveler.api.controller;

import com.traveler.api.controller.dto.AuthenticationDto;
import com.traveler.api.controller.dto.LoginResponseDto;
import com.traveler.api.controller.dto.RegisterDto;
import com.traveler.api.entity.Usuario;
import com.traveler.api.infra.security.TokenService;
import com.traveler.api.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
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
        var usernamePassword = new UsernamePasswordAuthenticationToken(data.nome(), data.senha());
        var auth = this.authenticationManager.authenticate(usernamePassword);

        // gerar o token para utilizar nas próximas requisições
        var token = tokenService.generateToken((Usuario) auth.getPrincipal());

        return ResponseEntity.ok(new LoginResponseDto(token));
    }

    @PostMapping("/registrar")
    public ResponseEntity register(@RequestBody RegisterDto data) {
//        if(this.repository.findByNome(data.nome()) != null) return ResponseEntity.badRequest().build();

        String encryptedPassword = new BCryptPasswordEncoder().encode(data.senha());
        Usuario newUser = new Usuario(data.nome(), data.email(), encryptedPassword);
        newUser.setDataCriacao(new Timestamp(System.currentTimeMillis()));
            this.repository.save(newUser);

            return ResponseEntity.ok().build();

    }
}
