package com.traveler.api.repository;

import com.traveler.api.entity.Despesa;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DespesaRepository extends JpaRepository<Despesa, Long> {
    List<Despesa> findByViagemId(Long viagemId);
}

