package com.citas.service;

import com.citas.model.Cita;
import com.citas.repository.CitaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
@DisplayName("serv cita")


//////////////////////////////

public class CitaServiceTest {
    @Autowired
    private CitaService citaService;
    @MockBean
    private CitaRepository citaRepository;
    private Cita citaPrueba;


    @BeforeEach
    public void setUp() {
        citaPrueba = new Cita(1, "Juan Pérez", "Dr. García", "2024-04-27", "10:00", "confirmada");
    }

    @Test
    @DisplayName("obtener cita por id")
    public void testObtenerCitaPorIdExitoso() {
        when(citaRepository.findById(1)).thenReturn(Optional.of(citaPrueba));

        Cita resultado = citaService.obtenerCitaPorId(1);

        assertNotNull(resultado);
        assertEquals(1, resultado.getId());
        assertEquals("Juan Pérez", resultado.getPaciente());
        assertEquals("Dr. García", resultado.getMedico());
        verify(citaRepository, times(1)).findById(1);
    }
//////////////////////////////////


    @Test
    @DisplayName("cancelar cita")
    public void testCancelarCitaExitoso() {
        when(citaRepository.findById(1)).thenReturn(Optional.of(citaPrueba));
        when(citaRepository.save(any(Cita.class))).thenReturn(citaPrueba);

        boolean resultado = citaService.cancelarCita(1);

        assertTrue(resultado);
        assertEquals("cancelada", citaPrueba.getEstado());
        verify(citaRepository, times(1)).save(any(Cita.class));
    }

    @Test

    @DisplayName("guardar cita nueva")
    public void testProgramarCitaNueva() {
        Cita nuevaCita = new Cita();
        nuevaCita.setPaciente("María López");
        nuevaCita.setMedico("Dra. Rodríguez");
        nuevaCita.setFecha("2024-04-28");
        nuevaCita.setHora("14:00");
        nuevaCita.setEstado("confirmada");

        when(citaRepository.save(any(Cita.class))).thenReturn(nuevaCita);

        Cita resultado = citaService.programarCita("María López", "Dra. Rodríguez", "2024-04-28", "14:00");

        assertNotNull(resultado);
        assertEquals("María López", resultado.getPaciente());
        assertEquals("confirmada", resultado.getEstado());
        verify(citaRepository, times(1)).save(any(Cita.class));
    }
}
