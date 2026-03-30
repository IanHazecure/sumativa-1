package com.ordenes.controller;

import com.ordenes.model.Orden;
import com.ordenes.service.OrdenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/ordenes")
public class OrdenController {

    @Autowired
    private OrdenService ordenService;

    @GetMapping
    public List<Orden> obtenerTodasLasOrdenes() {
        return ordenService.obtenerTodasLasOrdenes();
    }

    @GetMapping("/{id}")
    public Orden obtenerOrdenPorId(@PathVariable int id) {
        return ordenService.obtenerOrdenPorId(id);
    }

    @PostMapping
    public Orden crearOrden(@RequestBody Orden orden) {
        return ordenService.crearOrden(orden.getCliente(), orden.getProducto(), 
                                       orden.getCantidad(), orden.getPrecio(), 
                                       orden.getEstado(), orden.getFecha());

    }

////////
    @PutMapping("/{id}/estado")
    
    public boolean actualizarEstado(@PathVariable int id, @RequestParam String nuevoEstado) {
        return ordenService.actualizarEstado(id, nuevoEstado);

    }
/////////////

    @DeleteMapping

    public boolean eliminarOrden(@RequestHeader int id) {
        
        return ordenService.eliminarOrden(id);
    }
/////////////////
    @GetMapping("/estado/{estado}")

    public List<Orden> obtenerOrdenesPorEstado(@PathVariable String estado) {
        return ordenService.obtenerOrdenesPorEstado(estado);
    }
}
