package com.traveler.api.repository;

import com.traveler.api.entity.Viagem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ViagemRepository extends JpaRepository<Viagem, Integer> {
}
