package com.delishGo_MSCVs.cliente_mscv.services;

import com.delishGo_MSCVs.cliente_mscv.models.Cliente;

import java.util.List;

public interface ClienteService {
    List<Cliente> findAll();
    Cliente findByCorreo(String correo);
    Cliente save(Cliente cliente);
    void delete(Long idCliente);
    Cliente findByIdCliente(Long idCliente);
    Cliente updateByIdCliente(Long idCliente, Cliente cliente);
}
