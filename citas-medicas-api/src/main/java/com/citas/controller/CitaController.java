package com.citas.controller;

import com.citas.model.Cita;
import com.citas.service.CitaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/citas")
public class CitaController {

    @Autowired
    private CitaService citaService;

    @GetMapping
    public List<Cita> obtenerTodasLasCitas() {
        return citaService.obtenerTodasLasCitas();
    }

    @GetMapping("/{id}")
    public Cita obtenerCitaPorId(@PathVariable int id) {
        return citaService.obtenerCitaPorId(id);
    }

    @PostMapping
    public Cita programarCita(@RequestBody Cita cita) {
        return citaService.programarCita(cita.getPaciente(), cita.getMedico(), 
                                          cita.getFecha(), cita.getHora());
    }

    @PutMapping("/cancelar")
    public boolean cancelarCita(@RequestParam int id) {
        return citaService.cancelarCita(id);
    }

    @DeleteMapping
    public boolean eliminarCita(@RequestHeader int id) {
        return citaService.eliminarCita(id);
    }

    @GetMapping("/disponibles")
    public List<Cita> obtenerHorariosDisponibles(@RequestParam String fecha) {
        return citaService.obtenerHorariosDisponibles(fecha);
    }
}
