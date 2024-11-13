package com.traveler.api.controller;

import com.traveler.api.controller.dto.DespesaInputDto;
import com.traveler.api.entity.Despesa;
import com.traveler.api.repository.DespesaRepository;
import com.traveler.api.repository.UsuarioRepository;
import com.traveler.api.service.DespesaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/despesas")
@Tag(name = "Despesa")
public class DespesaController {

    @Autowired
    private DespesaService despesaService;

    @Autowired
    private DespesaRepository despesaRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Operation(summary = "Criar uma despesa", description = "Criar uma nova despesa")
    @PostMapping
    public ResponseEntity<?> criarDespesa(@RequestBody DespesaInputDto despesaInputDto) {
        try {
            Despesa despesa = despesaService.criarDespesa(despesaInputDto);

            return new ResponseEntity<>(despesa, HttpStatus.CREATED);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @Operation(summary = "Buscar todas as despesas", description = "Buscar todas as despesas criadas no banco de dados")
    @GetMapping
    public ResponseEntity<List<Despesa>> buscarDespesas() {

        List<Despesa> despesas = despesaService.buscarDespesas();

        return ResponseEntity.ok(despesas);
    }

    @Operation(summary = "Buscar uma despesa específica", description = "Buscar uma despesa específica")
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

    @Operation(summary = "Buscar as despesas de uma viagem específica", description = "Buscar todas as despesas vinculadas a uma viagem")
    @GetMapping("/viagem/{viagemId}")
    public ResponseEntity<List<Despesa>> buscarDespesaPorViagem(@PathVariable("viagemId") Long viagemId) {
        List<Despesa> despesas = despesaService.buscarDespesasPorViagem(viagemId);

        return ResponseEntity.ok(despesas);
    }

    @Operation(summary = "Alterar uma despesa", description = "Alterar os dados de uma despesa")
    @PutMapping("/{despesaId}")
    public ResponseEntity<?> alterarDespesa(@PathVariable("despesaId") Long despesaId, @RequestBody @Valid DespesaInputDto despesaInputDto) {
        try {
            Despesa despesa = despesaService.alterarDespesa(despesaId, despesaInputDto);

            return ResponseEntity.ok(despesa);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @Operation(summary = "Deletar uma despesa", description = "Deletar uma viagem do banco de dados")
    @DeleteMapping("/{despesaId}")
    public ResponseEntity<?> deletarDespesa(@PathVariable("despesaId") Long despesaId) {
        try {

            despesaService.deletarDespesa(despesaId);

            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }













}
