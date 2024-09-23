package com.traveler.api.repository;

import com.traveler.api.entity.Usuario;
import com.traveler.api.entity.Viagem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Optional;

public interface ViagemRepository extends JpaRepository<Viagem, Integer> {
}
