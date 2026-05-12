package com.delishGo_MSCVs.cliente_mscv.services;

import com.delishGo_MSCVs.cliente_mscv.exception.ClienteException;
import com.delishGo_MSCVs.cliente_mscv.models.Cliente;
import com.delishGo_MSCVs.cliente_mscv.repositories.ClienteRepository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class ClienteServiceImpl implements ClienteService {

    @Autowired
    private ClienteRepository clienteRepository;


    @Override
    @Transactional(readOnly=true)
    public List<Cliente> findAll() {
        return this.clienteRepository.findAll();
    }

    @Transactional
    @Override
    public Cliente save(Cliente cliente) {
        // Si el ID viene nulo, es nuevo registro
        if (cliente.getIdCliente() == null) {
            return clienteRepository.save(cliente);
        }

        // Si el ID viene con valor, validamos que no exista duplicado
        if (clienteRepository.findById(cliente.getIdCliente()).isPresent()) {
            throw new ClienteException("Cliente existente");
        }

        return clienteRepository.save(cliente);
    }


    @Transactional
    @Override
    public void delete(Long idCliente) {
        this.clienteRepository.deleteById(idCliente);

    }

    @Transactional(readOnly=true)
    @Override
    public Cliente findByIdCliente(Long idCliente) {
        return this.clienteRepository.findById(idCliente).orElseThrow(
                ()-> new ClienteException("Cliente con ID: "+idCliente+" no encontrado"));
    }

    @Transactional
    @Override
    public Cliente updateByIdCliente(Long idCliente, Cliente cliente) {
        return this.clienteRepository.findById(idCliente).map(element->{
            element.setNombreCliente(cliente.getNombreCliente());
            element.setApellidoCliente(cliente.getApellidoCliente());
            element.setEmailCliente(cliente.getEmailCliente());
            element.setTelefono(cliente.getTelefono());
            element.setDireccionCliente(cliente.getDireccionCliente());
            return this.clienteRepository.save(element);
        }).orElseThrow(
                ()-> new ClienteException("Cliente no encontrado"));
    }
}
