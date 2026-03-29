package com.ordenes.service;

import com.ordenes.model.Orden;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrdenService {
    private List<Orden> ordenes = new ArrayList<>();
    private int nextId = 9;

    public OrdenService() {

        ordenes.add(new Orden(1, "Juan Pérez", "Collar para perro", 2, 15.99, "confirmada", "2026-03-25"));
        ordenes.add(new Orden(2, "María López", "Comida para gatos", 5, 24.50, "confirmada", "2026-03-26"));
        ordenes.add(new Orden(3, "Carlos Martínez", "Juguete para perro", 3, 12.99, "cancelada", "2026-03-27"));
        ordenes.add(new Orden(4, "Patricia Díaz", "Arena para gatos", 1, 35.00, "confirmada", "2026-03-28"));
        ordenes.add(new Orden(5, "Roberto Silva", "Correa extensible", 2, 18.75, "confirmada", 
        "2026-03-29"));
        ordenes.add(new Orden(6, "Lucía Gómez", "Cama para mascota", 1, 45.99, "confirmada", "2026-03-30"));
        ordenes.add(new Orden(7, "Fernando Ruiz", "Vitaminas para perro", 4, 22.00, "cancelada", "2026-03-31"));
        
        ordenes.add(new Orden(8, "Sandra Cortés", "Champú para mascotas", 3, 16.50, "confirmada", "2026-04-01"));
    }


    public List<Orden> obtenerTodasLasOrdenes() {
        return ordenes;
    }


    public Orden obtenerOrdenPorId(int id) {
        return ordenes.stream()
                .filter(orden -> orden.getId() == id)
                .findFirst()
                .orElse(null);
    }

    public Orden crearOrden(String cliente, 
        String producto, int cantidad, double precio, String estado, String fecha) {


        if (cliente == null || cliente.isEmpty()) {
            throw new IllegalArgumentException("cliente no puede estar vacio");
 }
        
        if (producto == null || producto.isEmpty()) {
            throw new IllegalArgumentException("producto no puede estar vacio");
        }
        if (cantidad <= 0) {
            throw new IllegalArgumentException("antidad debe ser mayor a 0");
     }

        if (precio <= 0) {
            throw new IllegalArgumentException("precio debe ser mayor a 0");
    }

        Orden nuevaOrden = new Orden(nextId++, cliente, producto, cantidad, precio, estado, fecha);
        ordenes.add(nuevaOrden);
        return nuevaOrden;

      }

    public boolean actualizarEstado(int id, String nuevoEstado) {
        Orden orden = obtenerOrdenPorId(id);
        if (orden != null) {
            orden.setEstado(nuevoEstado);
            return true;
        }return false;
    }

    public boolean eliminarOrden(int id) {
        return ordenes.removeIf(orden -> orden.getId() == id);
    }

    public List<Orden> obtenerOrdenesPorEstado(String estado) {
        return ordenes.stream()
                .filter(orden -> orden.getEstado().equalsIgnoreCase(estado))
                .collect(Collectors.toList()); }
}
