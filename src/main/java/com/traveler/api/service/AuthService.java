package com.traveler.api.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.resend.Resend;
import com.resend.core.exception.ResendException;
import com.resend.services.emails.model.CreateEmailOptions;
import com.resend.services.emails.model.CreateEmailResponse;
import com.traveler.api.controller.dto.EsqueciSenhaDto;
import com.traveler.api.entity.Usuario;
import com.traveler.api.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ResourceLoader;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Date;

@Service
public class AuthService implements UserDetailsService {

    @Autowired
    UsuarioRepository repository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return repository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado com o email: " + email));
    }

    @Value("${api.security.token.secret}")
    private String secret;

    private String loadFileFromResources(String filePath) throws IOException {
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        try (InputStream inputStream = classLoader.getResourceAsStream(filePath)) {
            if (inputStream == null) {
                throw new IllegalArgumentException("Arquivo não encontrado: " + filePath);
            }
            return new String(inputStream.readAllBytes(), StandardCharsets.UTF_8);
        }
    }

    public void esqueciMinhaSenha(String email) throws Exception {
        Usuario usuario = repository.findByEmail(email)
                .orElseThrow(() -> new Exception("Usuário não encontrado com o email: " + email));

        String token = JWT.create()
                .withIssuer("auth-api")
                .withSubject(usuario.getEmail())
                .withClaim("id", usuario.getId().toString())
                .withClaim("nome", usuario.getNome())
                .withClaim("email", usuario.getEmail())
                .withClaim("data_criacao", usuario.getDataCriacao())
                .withExpiresAt(Date.from(LocalDateTime.now().plusMinutes(2)
                        .atZone(ZoneOffset.systemDefault()).toInstant()))
                .sign(Algorithm.HMAC256(secret));

        String template = loadFileFromResources("templates/email-recuperacao-senha.html");
        template = template.replace("{{nome}}", usuario.getNome());
        template = template.replace("{{link}}", "https://travelerbrasil.com/recuperar-senha?token=" + token);

        Resend resend = new Resend("re_ijCdmN6g_J3FtL77CJ1S3yfBN4SY2ULJG");

        CreateEmailOptions params = CreateEmailOptions.builder()
                .from("Traveler Brasil <contato@travelerbrasil.com>")
                .to(usuario.getEmail())
                .html(template)
                .subject("Redefinição de senha - Traveler Brasil")
                .build();

        try {
            resend.emails().send(params);


        } catch (ResendException e) {
            e.printStackTrace();
            throw new Exception("Erro ao enviar o e-mail de recuperação.");
        }
    }
}

