package com.citas.repository;

import com.citas.model.Cita;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@DisplayName("repositorio de citas")
public class CitaRepositoryTest {
    @Autowired
    private CitaRepository citaRepository;
    private Cita cita1;
    private Cita cita2;
    @BeforeEach


    /////////////////////////
    public void setUp() {
        citaRepository.deleteAll();

        cita1 = new Cita(0, "Pedro García", "Dr. López", "2024-04-27", "09:00", "confirmada");
        cita2 = new Cita(0, "Ana Martínez", "Dra. Silva", "2024-04-27", "11:00", "confirmada");

        citaRepository.save(cita1);
        citaRepository.save(cita2);
    }
    @Test
    @DisplayName("citas por fecha")
    public void testFindByFecha() {
        List<Cita> resultado = citaRepository.findByFecha("2024-04-27");

        assertNotNull(resultado);
        assertTrue(resultado.size() >= 2);
        assertTrue(resultado.stream().allMatch(c -> c.getFecha().equals("2024-04-27")));
    }

/////////////////////
    @Test
    @DisplayName("citas por estado")
    public void testFindByEstado() {
        List<Cita> resultado = citaRepository.findByEstado("confirmada");

        assertNotNull(resultado);
        assertTrue(resultado.size() >= 2);
        assertTrue(resultado.stream().allMatch(c -> c.getEstado().equals("confirmada")));
    }


    @Test
    @DisplayName("guardar cita")
    public void testSaveCita() {
        Cita nuevaCita = new Cita(0, "Carlos López", "Dr. Pérez", "2024-04-28", "15:00", "confirmada");

        Cita citaGuardada = citaRepository.save(nuevaCita);

        assertNotNull(citaGuardada);
        assertNotNull(citaGuardada.getId());
        assertTrue(citaGuardada.getId() > 0);
        assertEquals("Carlos López", citaGuardada.getPaciente());
    }

    @Test
    @DisplayName("eliminar cita por id")
    public void testDeleteById() {
        int id = cita1.getId();

        citaRepository.deleteById(id);
        Optional<Cita> resultado = citaRepository.findById(id);

        assertTrue(resultado.isEmpty());
    }
}
