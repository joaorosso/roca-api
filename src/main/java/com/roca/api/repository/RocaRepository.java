package com.roca.api.repository;

import com.roca.api.model.Roca;
import com.roca.api.repository.roca.RocaRepositoryQuery;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.UUID;

@Repository
public interface RocaRepository extends JpaRepository<Roca, UUID>, RocaRepositoryQuery {
    @Transactional
    void deleteByIdIn(List<UUID> ids);
}
