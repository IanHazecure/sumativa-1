package com.citas.service;

import com.citas.model.Cita;
import com.citas.repository.CitaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class CitaService {
    @Autowired
    private CitaRepository citaRepository;

    public List<Cita> obtenerTodasLasCitas() {
        return citaRepository.findAll();
    }

    public Cita obtenerCitaPorId(int id) {
        return citaRepository.findById(id).orElse(null);
    }

    public Cita programarCita(String paciente, String medico, String fecha, String hora) {
        Cita nuevaCita = new Cita();
        nuevaCita.setPaciente(paciente);
        nuevaCita.setMedico(medico);
        nuevaCita.setFecha(fecha);
        nuevaCita.setHora(hora);
        nuevaCita.setEstado("confirmada");
        return citaRepository.save(nuevaCita);
    }

    public boolean cancelarCita(int id) {
        Cita cita = obtenerCitaPorId(id);
        if (cita != null) {
            cita.setEstado("cancelada");
            citaRepository.save(cita);
            return true;
        }
        return false;
    }

    public boolean eliminarCita(int id) {
        if (citaRepository.existsById(id)) {
            citaRepository.deleteById(id);
            return true;
        }
        return false;
    }

    public List<Cita> obtenerHorariosDisponibles(String fecha) {
        List<Cita> citasPorFecha = citaRepository.findByFecha(fecha);
        return citasPorFecha.stream()
                .filter(c -> c.getEstado().equals("confirmada"))
                .toList();
    }
}
