package com.traveler.api.controller;

import com.traveler.api.controller.dto.ViagemInputDto;
import com.traveler.api.entity.Despesa;
import com.traveler.api.entity.Usuario;
import com.traveler.api.entity.Viagem;
import com.traveler.api.repository.UsuarioRepository;
import com.traveler.api.repository.ViagemRepository;
import com.traveler.api.service.DespesaService;
import com.traveler.api.service.ViagemService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/viagem")
public class  ViagemController {

    @Autowired
    private ViagemService viagemService;

    @Autowired
    private UsuarioRepository usuarioRepository;
    @Autowired
    private ViagemRepository viagemRepository;
    @Autowired
    private DespesaService despesaService;


    @PostMapping
    public ResponseEntity<?> criarViagem(@RequestBody @Valid ViagemInputDto viagemInputDto, @AuthenticationPrincipal Usuario usuario) {
//            Usuario usuario = (Usuario) usuarioRepository.findByNome(userDetails.getUsername());

            Viagem viagem = viagemService.criarViagem(viagemInputDto, usuario);

            return new ResponseEntity<>(viagem, HttpStatus.CREATED);
    }


    @GetMapping("/{viagemId}")
    public ResponseEntity<Viagem> buscarViagemPorId(@PathVariable("viagemId") String viagemId) {
        try {
            Optional<Viagem> viagem = viagemService.buscarViagemPorId(viagemId);

            if (viagem.isPresent()) {
                return ResponseEntity.ok(viagem.get());
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }


    @GetMapping
    public ResponseEntity<List<Viagem>> buscarViagens() {
        List<Viagem> viagens = viagemService.buscarViagens();

        viagens.forEach(viagem -> {
            List<Despesa> despesas = despesaService.buscarDespesasPorViagem(viagem.getId());
            double valorTotalDespesas = despesas.stream()
                    .mapToDouble(despesa -> despesa.getValor().doubleValue())
                    .sum();
            viagem.setValorTotalDespesas(valorTotalDespesas);
        });

        return ResponseEntity.ok(viagens);
    }


    @GetMapping("/usuario/{usuarioId}")
    public ResponseEntity<List<Viagem>> buscarViagensPorUsuario(@PathVariable("usuarioId") Long usuarioId) {
        List<Viagem> viagens = viagemService.buscarViagensPorUsuario(usuarioId);

        viagens.forEach(viagem -> {
            List<Despesa> despesas = despesaService.buscarDespesasPorViagem(viagem.getId());
            double valorTotalDespesas = despesas.stream()
                    .mapToDouble(despesa -> despesa.getValor().doubleValue())
                    .sum();
            viagem.setValorTotalDespesas(valorTotalDespesas);
        });

        return ResponseEntity.ok(viagens);
    }


    @PutMapping("/{viagemId}")
    public ResponseEntity<?> alterarViagem(@PathVariable("viagemId") String viagemId, @RequestBody @Valid ViagemInputDto viagemInputDto, @AuthenticationPrincipal Usuario usuario) {
        try {
            Viagem viagem = viagemService.alterarViagem(viagemId, viagemInputDto, usuario);

            List<Despesa> despesas = despesaService.buscarDespesasPorViagem(viagem.getId());
            double valorTotalDespesas = despesas.stream()
                    .mapToDouble(despesa -> despesa.getValor().doubleValue())
                    .sum();
            viagem.setValorTotalDespesas(valorTotalDespesas);

            return ResponseEntity.ok(viagem);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }


    @DeleteMapping("/{viagemId}")
    public ResponseEntity<?>deletarViagem(@PathVariable("viagemId") String viagemId) {
        try {

        viagemService.deletarViagem(viagemId);

        return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }


}
