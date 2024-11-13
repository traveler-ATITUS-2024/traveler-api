package com.traveler.api.service;

import com.traveler.api.controller.dto.CriarCategoriaDto;
import com.traveler.api.entity.Categoria;
import com.traveler.api.repository.CategoriaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CategoriaService {

    @Autowired
    private CategoriaRepository categoriaRepository;

    public Categoria criarCategoria(CriarCategoriaDto criarCategoriaDto) {

        var entity = new Categoria(
                criarCategoriaDto.nome());

        return categoriaRepository.save(entity);
    }

    public void deletarCategoria(Long categoriaId) throws Exception {

        Optional<Categoria> categoriaExistente = categoriaRepository.findById(categoriaId);

        if(categoriaExistente.isPresent()) {
            categoriaRepository.deleteById(categoriaId);
        } else {
            throw new Exception("Categoria não encontrada!");
        }
    }
}