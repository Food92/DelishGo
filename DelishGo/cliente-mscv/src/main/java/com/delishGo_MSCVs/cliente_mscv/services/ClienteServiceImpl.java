package com.delishGo_MSCVs.cliente_mscv.services;

import com.delishGo_MSCVs.cliente_mscv.exception.ClienteException;
import com.delishGo_MSCVs.cliente_mscv.models.Cliente;
import com.delishGo_MSCVs.cliente_mscv.models.dtos.ClienteDTO;
import com.delishGo_MSCVs.cliente_mscv.repositories.ClienteRepository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ClienteServiceImpl implements ClienteService {



        @Autowired
        private ClienteRepository clienteRepository;

        @Override
        @Transactional(readOnly = true)
        public List<ClienteDTO> findAll() {
        return clienteRepository.findAll()
                .stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

        @Override
        @Transactional
        public ClienteDTO save(Cliente cliente) {
        // Validar duplicados por RUN y correo
        if (clienteRepository.findByRun(cliente.getRun()).isPresent()) {
            throw new ClienteException("Ya existe un cliente con RUN: " + cliente.getRun());
        }
        if (clienteRepository.findByEmailCliente(cliente.getEmailCliente()).isPresent()) {
            throw new ClienteException("Ya existe un cliente con correo: " + cliente.getEmailCliente());
        }

        Cliente saved = clienteRepository.save(cliente);
        return mapToDTO(saved);
    }


    @Override
    @Transactional(readOnly = true)
    public ClienteDTO findByEmailCliente(String emailCliente) {
        Cliente cliente = clienteRepository.findByEmailCliente(emailCliente)
                .orElseThrow(() -> new ClienteException("Cliente con correo: " + emailCliente + " no encontrado"));
        return mapToDTO(cliente);
    }


        @Override
        @Transactional
        public void delete(Long idCliente) {
        if (!clienteRepository.existsById(idCliente)) {
            throw new ClienteException("Cliente con ID: " + idCliente + " no encontrado");
        }
        clienteRepository.deleteById(idCliente);
    }

        @Override
        @Transactional(readOnly = true)
        public ClienteDTO findByIdCliente(Long idCliente) {
        Cliente cliente = clienteRepository.findById(idCliente)
                .orElseThrow(() -> new ClienteException("Cliente con ID: " + idCliente + " no encontrado"));
        return mapToDTO(cliente);
    }

        @Override
        @Transactional
        public ClienteDTO updateByIdCliente(Long idCliente, Cliente cliente) {
        Cliente updated = clienteRepository.findById(idCliente).map(element -> {
            element.setNombreCliente(cliente.getNombreCliente());
            element.setApellidoCliente(cliente.getApellidoCliente());
            element.setEmailCliente(cliente.getEmailCliente());
            element.setTelefono(cliente.getTelefono());
            element.setDireccionCliente(cliente.getDireccionCliente());
            element.setActivo(cliente.getActivo());
            return clienteRepository.save(element);
        }).orElseThrow(() -> new ClienteException("Cliente con ID: " + idCliente + " no encontrado"));

        return mapToDTO(updated);
    }

        @Override
        @Transactional(readOnly = true)
        public List<ClienteDTO> findClientesActivos() {
        return clienteRepository.findByActivoTrue()
                .stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

        // 🔧 Método privado para mapear entidad → DTO
        private ClienteDTO mapToDTO(Cliente cliente) {
        ClienteDTO dto = new ClienteDTO();
        dto.setIdCliente(cliente.getIdCliente());
        dto.setRun(cliente.getRun());
        dto.setNombreCliente(cliente.getNombreCliente());
        dto.setApellidoCliente(cliente.getApellidoCliente());
        dto.setDireccionCliente(cliente.getDireccionCliente());
        dto.setTelefono(cliente.getTelefono());
        dto.setEmailCliente(cliente.getEmailCliente());
        return dto;
    }
}
