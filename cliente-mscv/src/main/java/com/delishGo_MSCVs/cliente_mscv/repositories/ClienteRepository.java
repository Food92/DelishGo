package com.delishGo_MSCVs.cliente_mscv.repositories;

import com.delishGo_MSCVs.cliente_mscv.models.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ClienteRepository extends JpaRepository<Cliente, Long> {
    Optional<Cliente> findByRun(String run);
}
