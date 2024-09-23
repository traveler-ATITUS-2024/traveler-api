package com.traveler.api.repository;

import com.traveler.api.entity.Viagem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ViagemRepository extends JpaRepository<Viagem, Long> {

    List<Viagem> findByUsuarioId(Long usuarioId);
}
