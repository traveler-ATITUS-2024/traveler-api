package com.traveler.api.controller;

import com.traveler.api.controller.dto.CriarCategoriaDto;
import com.traveler.api.entity.Categoria;
import com.traveler.api.service.CategoriaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/categoria")
@Tag(name = "Categoria")
public class CategoriaController {

    @Autowired
    private CategoriaService categoriaService;

    @Operation(summary = "Criar uma categoria", description = "Criar uma nova categoria")
    @PostMapping
    public ResponseEntity<Categoria> criarCategoria(@RequestBody @Valid CriarCategoriaDto criarCategoriaDto) {

        try {
            var categoria = categoriaService.criarCategoria(criarCategoriaDto);

            return new ResponseEntity<>(categoria, HttpStatus.CREATED);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }

    }

    @Operation(summary = "Deletar uma categoria", description = "Deletar uma categoria do banco de dados")
    @DeleteMapping("/{categoriaId}")
    public ResponseEntity<?> deletarCategoria(@PathVariable("categoriaId") Long categoriaId) {
        try {

        categoriaService.deletarCategoria(categoriaId);
        return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

}