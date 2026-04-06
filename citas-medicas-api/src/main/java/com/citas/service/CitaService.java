package com.citas.service;

import com.citas.model.Cita;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;

@Service
public class CitaService {
    private ArrayList<Cita> citas = new ArrayList<>();
    private int nextId = 1;

    public List<Cita> obtenerTodasLasCitas() {
        return new ArrayList<>(citas);
    }

    public Cita obtenerCitaPorId(int id) {
        return citas.stream().filter(c -> c.getId() == id).findFirst().orElse(null);
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
        return citas.removeIf(c -> c.getId() == id);
    }

    public List<Cita> obtenerHorariosDisponibles(String fecha) {
        return citas.stream()
                .filter(c -> c.getFecha().equals(fecha) && c.getEstado().equals("confirmada"))
                .toList();
    }
}
