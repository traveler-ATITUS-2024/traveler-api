package com.traveler.api.repository;

import com.traveler.api.controller.dto.CategoriaDespesaDto;
import com.traveler.api.entity.Despesa;
import com.traveler.api.entity.Viagem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface ViagemRepository extends JpaRepository<Viagem, Long> {

    List<Viagem> findByUsuarioId(Long usuarioId);

    @Query("SELECT new com.traveler.api.controller.dto.CategoriaDespesaDto(d.categoriaId, c.nome, SUM(d.valor)) " +
            "FROM Despesa d " +
            "JOIN Categoria c ON d.categoriaId = c.id " +
            "WHERE d.viagemId = :viagemId " +
            "GROUP BY d.categoriaId, c.nome")
    List<CategoriaDespesaDto> buscarTotalDespesasPorCategorias(@Param("viagemId") Long viagemId);
}