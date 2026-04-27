package com.citas.controller;

import com.citas.model.Cita;
import com.citas.service.CitaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@RestController
@RequestMapping("/api/citas")
public class CitaController {

    @Autowired
    private CitaService citaService;

    @GetMapping
    public CollectionModel<EntityModel<Cita>> obtenerTodasLasCitas() {
        List<EntityModel<Cita>> citas = citaService.obtenerTodasLasCitas().stream()
                .map(cita -> EntityModel.of(cita,
                        linkTo(methodOn(CitaController.class).obtenerCitaPorId(cita.getId())).withSelfRel(),
                        linkTo(methodOn(CitaController.class).obtenerTodasLasCitas()).withRel("todas-citas")))
                .collect(Collectors.toList());
        return CollectionModel.of(citas,
                linkTo(methodOn(CitaController.class).obtenerTodasLasCitas()).withSelfRel());
    }

    @GetMapping("/{id}")
    public EntityModel<Cita> obtenerCitaPorId(@PathVariable int id) {
        Cita cita = citaService.obtenerCitaPorId(id);
        return EntityModel.of(cita,
                linkTo(methodOn(CitaController.class).obtenerCitaPorId(id)).withSelfRel(),
                linkTo(methodOn(CitaController.class).obtenerTodasLasCitas()).withRel("todas-citas"),
                linkTo(methodOn(CitaController.class).cancelarCita(id)).withRel("cancelar"));
    }

    @PostMapping
    public EntityModel<Cita> programarCita(@RequestBody Cita cita) {
        Cita nuevaCita = citaService.programarCita(cita.getPaciente(), cita.getMedico(), 
                                          cita.getFecha(), cita.getHora());
        return EntityModel.of(nuevaCita,
                linkTo(methodOn(CitaController.class).obtenerCitaPorId(nuevaCita.getId())).withSelfRel(),
                linkTo(methodOn(CitaController.class).obtenerTodasLasCitas()).withRel("todas-citas"));
    }

    @PutMapping("/cancelar")
    public EntityModel<Cita> cancelarCita(@RequestParam int id) {
        citaService.cancelarCita(id);
        Cita cita = citaService.obtenerCitaPorId(id);
        return EntityModel.of(cita,
                linkTo(methodOn(CitaController.class).obtenerCitaPorId(id)).withSelfRel(),
                linkTo(methodOn(CitaController.class).obtenerTodasLasCitas()).withRel("todas-citas"));
    }

    @DeleteMapping("/{id}")
    public boolean eliminarCita(@PathVariable int id) {
        return citaService.eliminarCita(id);
    }

    @GetMapping("/disponibles")
    public CollectionModel<EntityModel<Cita>> obtenerHorariosDisponibles(@RequestParam String fecha) {
        List<EntityModel<Cita>> citas = citaService.obtenerHorariosDisponibles(fecha).stream()
                .map(cita -> EntityModel.of(cita,
                        linkTo(methodOn(CitaController.class).obtenerCitaPorId(cita.getId())).withSelfRel()))
                .collect(Collectors.toList());
        return CollectionModel.of(citas,
                linkTo(methodOn(CitaController.class).obtenerHorariosDisponibles(fecha)).withSelfRel());
    }
}
