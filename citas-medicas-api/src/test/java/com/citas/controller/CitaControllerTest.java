package com.citas.controller;

import com.citas.model.Cita;
import com.citas.service.CitaService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(CitaController.class)
@DisplayName("controlador de citas")
public class CitaControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private CitaService citaService;
    private Cita cita1;
    private Cita cita2;

//////////////////////////




    @BeforeEach
    public void setUp() {
        cita1 = new Cita(1, "Juan Pérez", "Dr. García", "2024-04-27", "10:00", "confirmada");
        cita2 = new Cita(2, "María López", "Dra. Rodríguez", "2024-04-28", "14:00", "confirmada");
    }

    @Test
    @DisplayName("listar todas las citas con HATEOAS")
    public void testObtenerTodasLasCitas() throws Exception {
        List<Cita> citas = Arrays.asList(cita1, cita2);
        when(citaService.obtenerTodasLasCitas()).thenReturn(citas);

        mockMvc.perform(get("/api/citas"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$._embedded.citaList").isArray())
                .andExpect(jsonPath("$._links.self").exists());

        verify(citaService, times(1)).obtenerTodasLasCitas();


        
    }

    @Test
    @DisplayName("obtener cita por id con HATEOAS")
    public void testObtenerCitaPorId() throws Exception {
        when(citaService.obtenerCitaPorId(1)).thenReturn(cita1);

        mockMvc.perform(get("/api/citas/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.paciente").value("Juan Pérez"))
                .andExpect(jsonPath("$._links.self").exists())
                .andExpect(jsonPath("$._links['todas-citas']").exists());

        verify(citaService, times(1)).obtenerCitaPorId(1);

    }
    

    @Test
    @DisplayName("crear nueva cita con HATEOAS")
    public void testProgramarCita() throws Exception {
        when(citaService.programarCita("Carlos López", "Dr. Pérez", "2024-04-29", "16:00"))
                .thenReturn(new Cita(3, "Carlos López", "Dr. Pérez", "2024-04-29", "16:00", "confirmada"));

        mockMvc.perform(post("/api/citas")
                .contentType("application/json")
                .content("{\"paciente\":\"Carlos López\",\"medico\":\"Dr. Pérez\",\"fecha\":\"2024-04-29\",\"hora\":\"16:00\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.paciente").value("Carlos López"))
                .andExpect(jsonPath("$._links").exists());

        verify(citaService, times(1)).programarCita(anyString(), anyString(), anyString(), anyString());
    }



    @Test
    @DisplayName("cancelar cita")
    public void testCancelarCita() throws Exception {
        cita1.setEstado("cancelada");
        when(citaService.cancelarCita(1)).thenReturn(true);
        when(citaService.obtenerCitaPorId(1)).thenReturn(cita1);

        mockMvc.perform(put("/api/citas/cancelar?id=1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$._links").exists());

        verify(citaService, times(1)).cancelarCita(1);
    }


    @Test

    @DisplayName("eliminar cita por id")
    public void testEliminarCita() throws Exception {
        when(citaService.eliminarCita(1)).thenReturn(true);

        mockMvc.perform(delete("/api/citas/1"))
                .andExpect(status().isOk());

        verify(citaService, times(1)).eliminarCita(1);

    }






}
