package com.ordenes.controller;

import com.ordenes.model.Orden;
import com.ordenes.service.OrdenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@RestController
@RequestMapping("/api/ordenes")
public class OrdenController {

    @Autowired
    private OrdenService ordenService;

    @GetMapping
    public CollectionModel<EntityModel<Orden>> obtenerTodasLasOrdenes() {
        List<EntityModel<Orden>> ordenes = ordenService.obtenerTodasLasOrdenes().stream()
                .map(orden -> EntityModel.of(orden,
                        linkTo(methodOn(OrdenController.class).obtenerOrdenPorId(orden.getId())).withSelfRel(),
                        linkTo(methodOn(OrdenController.class).obtenerTodasLasOrdenes()).withRel("todas-ordenes")))
                .collect(Collectors.toList());
        return CollectionModel.of(ordenes,
                linkTo(methodOn(OrdenController.class).obtenerTodasLasOrdenes()).withSelfRel());
    }

    @GetMapping("/{id}")
    public EntityModel<Orden> obtenerOrdenPorId(@PathVariable int id) {
        Orden orden = ordenService.obtenerOrdenPorId(id);
        return EntityModel.of(orden,
                linkTo(methodOn(OrdenController.class).obtenerOrdenPorId(id)).withSelfRel(),
                linkTo(methodOn(OrdenController.class).obtenerTodasLasOrdenes()).withRel("todas-ordenes"),
                linkTo(methodOn(OrdenController.class).actualizarEstado(id, "")).withRel("actualizar-estado"));
    }

    @PostMapping
    public EntityModel<Orden> crearOrden(@RequestBody Orden orden) {
        Orden nuevaOrden = ordenService.crearOrden(orden.getCliente(), orden.getProducto(), 
                                       orden.getCantidad(), orden.getPrecio(), 
                                       orden.getEstado(), orden.getFecha());
        return EntityModel.of(nuevaOrden,
                linkTo(methodOn(OrdenController.class).obtenerOrdenPorId(nuevaOrden.getId())).withSelfRel(),
                linkTo(methodOn(OrdenController.class).obtenerTodasLasOrdenes()).withRel("todas-ordenes"));
    }

    @PutMapping("/{id}/estado")
    public EntityModel<Orden> actualizarEstado(@PathVariable int id, @RequestParam String nuevoEstado) {
        ordenService.actualizarEstado(id, nuevoEstado);
        Orden orden = ordenService.obtenerOrdenPorId(id);
        return EntityModel.of(orden,
                linkTo(methodOn(OrdenController.class).obtenerOrdenPorId(id)).withSelfRel(),
                linkTo(methodOn(OrdenController.class).obtenerTodasLasOrdenes()).withRel("todas-ordenes"));
    }

    @DeleteMapping("/{id}")
    public boolean eliminarOrden(@PathVariable int id) {
        return ordenService.eliminarOrden(id);
    }

    @GetMapping("/estado/{estado}")
    public CollectionModel<EntityModel<Orden>> obtenerOrdenesPorEstado(@PathVariable String estado) {
        List<EntityModel<Orden>> ordenes = ordenService.obtenerOrdenesPorEstado(estado).stream()
                .map(orden -> EntityModel.of(orden,
                        linkTo(methodOn(OrdenController.class).obtenerOrdenPorId(orden.getId())).withSelfRel()))
                .collect(Collectors.toList());
        return CollectionModel.of(ordenes,
                linkTo(methodOn(OrdenController.class).obtenerOrdenesPorEstado(estado)).withSelfRel());
    }
}
