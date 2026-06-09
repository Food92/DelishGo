package com.delishGo_MSCVs.cliente_mscv.services;

import com.delishGo_MSCVs.cliente_mscv.models.Cliente;
import com.delishGo_MSCVs.cliente_mscv.models.dtos.ClienteDTO;

import java.util.List;

public interface ClienteService {
    List<ClienteDTO> findAll();
    ClienteDTO save(Cliente cliente);
    void delete(Long idCliente);
    ClienteDTO findByIdCliente(Long idCliente);
    ClienteDTO updateByIdCliente(Long idCliente, Cliente cliente);

    List<ClienteDTO> findClientesActivos();
    ClienteDTO findByEmailCliente(String emailCliente);
}
