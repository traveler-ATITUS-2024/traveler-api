package com.traveler.api.controller;

import com.traveler.api.controller.dto.DespesaInputDto;
import com.traveler.api.entity.Despesa;
import com.traveler.api.entity.Usuario;
import com.traveler.api.repository.DespesaRepository;
import com.traveler.api.repository.UsuarioRepository;
import com.traveler.api.service.DespesaService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/despesas")
public class DespesaController {

    @Autowired
    private DespesaService despesaService;

    @Autowired
    private DespesaRepository despesaRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @PostMapping
    public ResponseEntity<?> criarDespesa(@RequestBody DespesaInputDto despesaInputDto) {
        try {
            Despesa despesa = despesaService.criarDespesa(despesaInputDto);

            return new ResponseEntity<>(despesa, HttpStatus.CREATED);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<List<Despesa>> buscarDespesas() {

        List<Despesa> despesas = despesaService.buscarDespesas();

        return ResponseEntity.ok(despesas);
    }

    @GetMapping("/{despesaId}")
    public ResponseEntity<Despesa> buscarDespesaPorId(@PathVariable("despesaId") String despesaId) {
        try {

            Optional<Despesa> despesa = despesaService.buscarDespesaPorId(despesaId);

            if (despesa.isPresent()) {
                return ResponseEntity.ok(despesa.get());
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
}
