package com.delishGo_MSCVs.cliente_mscv.repositories;

import com.delishGo_MSCVs.cliente_mscv.models.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ClienteRepository extends JpaRepository<Cliente, Long> {
    Optional<Cliente> findByRun(String run);

    // Buscar clientes activos
    List<Cliente> findByActivoTrue();

    Optional<Cliente> findByEmailCliente(String emailCliente);
}
