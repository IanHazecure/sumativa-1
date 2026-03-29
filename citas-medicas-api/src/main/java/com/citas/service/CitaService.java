package com.citas.service;

import com.citas.model.Cita;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CitaService {
    private List<Cita> citas = new ArrayList<>();
    private int nextId = 4;

    public CitaService() {
        citas.add(new Cita(1, "Juan Pérez", "Dr. García", "2026-04-15", "09:00", "confirmada"));
        citas.add(new Cita(2, "María López", "Dra. Rodríguez", "2026-04-16", "10:30", "confirmada"));
        citas.add(new Cita(3, "Carlos Martínez", "Dr. López", "2026-04-17", "14:00", "cancelada"));
    }

    public List<Cita> obtenerTodasLasCitas() {
        return citas;
    }

    public Cita obtenerCitaPorId(int id) {
        return citas.stream()
                .filter(c -> c.getId() == id)
                .findFirst()
                .orElse(null);
    }

    public Cita programarCita(String paciente, String medico, String fecha, String hora) {
        Cita nuevaCita = new Cita(nextId++, paciente, medico, fecha, hora, "confirmada");
        citas.add(nuevaCita);
        return nuevaCita;
    }

    public boolean cancelarCita(int id) {
        Cita cita = obtenerCitaPorId(id);
        if (cita != null) {
            cita.setEstado("cancelada");
            return true;
        }
        return false;
    }

    public List<Cita> obtenerHorariosDisponibles(String fecha) {
        return citas.stream()
                .filter(c -> c.getFecha().equals(fecha) && c.getEstado().equals("confirmada"))
                .collect(Collectors.toList());
    }
}
