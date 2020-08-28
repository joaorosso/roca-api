package com.roca.api.repository;

import com.roca.api.model.Despesa;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface DespesaRepository extends JpaRepository<Despesa, UUID> {
}
