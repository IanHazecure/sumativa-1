package com.citas.controller;

import com.citas.model.Cita;
import com.citas.service.CitaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
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

    @GetMapping("/id")
    public Cita obtenerCitaPorId(@RequestParam int id) {
        return citaService.obtenerCitaPorId(id);
    }

    @GetMapping("/programar")
    public Cita programarCita(@RequestParam String paciente, @RequestParam String medico, 
                              @RequestParam String fecha, @RequestParam String hora) {
        return citaService.programarCita(paciente, medico, fecha, hora);
    }

    @GetMapping("/cancelar")
    public boolean cancelarCita(@RequestParam int id) {
        return citaService.cancelarCita(id);
    }

    @GetMapping("/disponibles")
    public List<Cita> obtenerHorariosDisponibles(@RequestParam String fecha) {
        return citaService.obtenerHorariosDisponibles(fecha);
    }
}
