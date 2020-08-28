package com.roca.api.repository;

import com.roca.api.model.Roca;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface RocaRepository extends JpaRepository<Roca, UUID> {
}
