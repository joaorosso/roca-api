package com.roca.api.repository;

import com.roca.api.model.Lucro;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface LucroRepository extends JpaRepository<Lucro, UUID> {
}
