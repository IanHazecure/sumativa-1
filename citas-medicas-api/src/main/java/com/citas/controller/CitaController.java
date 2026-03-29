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
    public Cita programarCita(@RequestParam String paciente, @RequestParam String medico, 
                              @RequestParam String fecha, @RequestParam String hora) {
        return citaService.programarCita(paciente, medico, fecha, hora);
    }

    @PutMapping("/{id}/cancelar")
    public boolean cancelarCita(@PathVariable int id) {
        return citaService.cancelarCita(id);
    }

    @DeleteMapping("/{id}")
    public boolean eliminarCita(@PathVariable int id) {
        return citaService.eliminarCita(id);
    }

    @GetMapping("/disponibles")
    public List<Cita> obtenerHorariosDisponibles(@RequestParam String fecha) {
        return citaService.obtenerHorariosDisponibles(fecha);
    }
}
