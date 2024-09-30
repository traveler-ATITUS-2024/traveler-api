package com.traveler.api.controller;

import com.traveler.api.controller.dto.CriarCategoriaDto;
import com.traveler.api.entity.Categoria;
import com.traveler.api.service.CategoriaService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/categoria")
public class CategoriaController {

    @Autowired
    private CategoriaService categoriaService;

    @PostMapping
    public ResponseEntity<Categoria> criarCategoria(@RequestBody @Valid CriarCategoriaDto criarCategoriaDto) {

        try {
            var categoria = categoriaService.criarCategoria(criarCategoriaDto);

            return new ResponseEntity<>(categoria, HttpStatus.CREATED);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }

    }

    @DeleteMapping("/{categoriaId}")
    public ResponseEntity<Void> deletarCategoria(@PathVariable("categoriaId") Long categoriaId) {
        categoriaService.deletarCategoria(categoriaId);
        return ResponseEntity.noContent().build();
    }

}