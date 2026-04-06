package com.ordenes.service;

import com.ordenes.model.Orden;
import com.ordenes.repository.OrdenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class OrdenService {
    @Autowired
    private OrdenRepository ordenRepository;

    public List<Orden> obtenerTodasLasOrdenes() {
        return ordenRepository.findAll();
    }

    public Orden obtenerOrdenPorId(int id) {
        return ordenRepository.findById(id).orElse(null);
    }

    public Orden crearOrden(String cliente, String producto, int cantidad, double precio, String estado, String fecha) {
        if (cliente == null || cliente.isEmpty()) {
            throw new IllegalArgumentException("cliente no puede estar vacio");
        }
        if (producto == null || producto.isEmpty()) {
            throw new IllegalArgumentException("producto no puede estar vacio");
        }
        if (cantidad <= 0) {
            throw new IllegalArgumentException("cantidad debe ser mayor a 0");
        }
        if (precio <= 0) {
            throw new IllegalArgumentException("precio debe ser mayor a 0");
        }

        Orden nuevaOrden = new Orden();
        nuevaOrden.setCliente(cliente);
        nuevaOrden.setProducto(producto);
        nuevaOrden.setCantidad(cantidad);
        nuevaOrden.setPrecio(precio);
        nuevaOrden.setEstado(estado);
        nuevaOrden.setFecha(fecha);
        return ordenRepository.save(nuevaOrden);
    }

    public boolean actualizarEstado(int id, String nuevoEstado) {
        Orden orden = obtenerOrdenPorId(id);
        if (orden != null) {
            orden.setEstado(nuevoEstado);
            ordenRepository.save(orden);
            return true;
        }
        return false;
    }

    public boolean eliminarOrden(int id) {
        if (ordenRepository.existsById(id)) {
            ordenRepository.deleteById(id);
            return true;
        }
        return false;
    }

    public List<Orden> obtenerOrdenesPorEstado(String estado) {
        return ordenRepository.findByEstado(estado);
    }
}
