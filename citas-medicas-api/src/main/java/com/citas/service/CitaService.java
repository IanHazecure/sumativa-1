package com.citas.service;

import com.citas.model.Cita;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CitaService {
    private List<Cita> citas = new ArrayList<>();
    private int nextId = 9;

    public CitaService() {
        citas.add(new Cita(1, "Juan Pérez", "Dr. García", "2026-04-15", "09:00", "confirmada"));
        citas.add(new Cita(2, "María López", "Dra. Rodríguez", "2026-04-16", "10:30", "confirmada"));
        citas.add(new Cita(3, "Carlos Martínez", "Dr. López", "2026-04-17", "14:00", "cancelada"));
        citas.add(new Cita(4, "Patricia Díaz", "Dr. García", "2026-04-18", "11:00", "confirmada"));
        citas.add(new Cita(5, "Roberto Silva", "Dra. Rodríguez", "2026-04-19", "15:30", "confirmada"));
        citas.add(new Cita(6, "Lucía Gómez", "Dr. López", "2026-04-20", "16:00", "confirmada"));
        citas.add(new Cita(7, "Fernando Ruiz", "Dr. García", "2026-04-21", "13:30", "cancelada"));
        citas.add(new Cita(8, "Sandra Cortés", "Dra. Rodríguez", "2026-04-22", "09:30", "confirmada"));
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

    public boolean eliminarCita(int id) {
        Cita cita = obtenerCitaPorId(id);
        if (cita != null) {
            citas.remove(cita);
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
