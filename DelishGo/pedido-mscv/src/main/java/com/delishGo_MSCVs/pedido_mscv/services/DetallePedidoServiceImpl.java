package com.delishGo_MSCVs.pedido_mscv.services;

import com.delishGo_MSCVs.pedido_mscv.models.DetallePedido;
import com.delishGo_MSCVs.pedido_mscv.models.dtos.DetallePedidoDTO;
import com.delishGo_MSCVs.pedido_mscv.repositories.DetallePedidoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class DetallePedidoServiceImpl implements DetallePedidoService {

    @Autowired
    private DetallePedidoRepository repository;

    @Override
    public List<DetallePedidoDTO> obtenerDetallesPorPedido(Long idPedido) {
        return repository.findByIdPedido(idPedido)
                .stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public DetallePedidoDTO guardarDetalle(DetallePedidoDTO detalleDTO) {
        DetallePedido detalle = mapToEntity(detalleDTO);
        detalle.calcularSubtotal(); // ✅ calcula subtotal antes de guardar
        DetallePedido saved = repository.save(detalle);
        return mapToDTO(saved);
    }

    @Override
    public void eliminarDetalle(Long idDetalle) {
        repository.deleteById(idDetalle);
    }

    // 🔄 Helpers para mapear
    private DetallePedidoDTO mapToDTO(DetallePedido detalle) {
        DetallePedidoDTO dto = new DetallePedidoDTO();
        dto.setIdDetalle(detalle.getIdDetalle());   // ✅ ahora se incluye
        dto.setIdProducto(detalle.getIdProducto());
        dto.setCantidad(detalle.getCantidad());
        dto.setPrecioUnitario(detalle.getPrecioUnitario());
        dto.setSubtotal(detalle.getSubtotal());
        dto.setObservacion(detalle.getObservacion());
        return dto;
    }

    private DetallePedido mapToEntity(DetallePedidoDTO dto) {
        DetallePedido detalle = new DetallePedido();
        detalle.setIdProducto(dto.getIdProducto());
        detalle.setCantidad(dto.getCantidad());
        detalle.setPrecioUnitario(dto.getPrecioUnitario());
        detalle.setObservacion(dto.getObservacion());
        detalle.calcularSubtotal();
        return detalle;
    }
}