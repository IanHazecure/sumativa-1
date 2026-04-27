package com.ordenes.controller;

import com.ordenes.model.Orden;
import com.ordenes.service.OrdenService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(OrdenController.class)
@DisplayName("controlador de ordenes")
public class OrdenControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private OrdenService ordenService;

    private Orden orden1;
    private Orden orden2;

    @BeforeEach
    public void setUp() {
        orden1 = new Orden(1, "Juan García", "Alimento gatos", 5, 25.99, "pendiente", "2024-04-27");
        orden2 = new Orden(2, "María López", "Juguete perro", 3, 15.50, "entregada", "2024-04-28");
    }

    @Test
    @DisplayName("listar todas las ordenes con HATEOAS")
    public void testObtenerTodasLasOrdenes() throws Exception {
        List<Orden> ordenes = Arrays.asList(orden1, orden2);
        when(ordenService.obtenerTodasLasOrdenes()).thenReturn(ordenes);

        mockMvc.perform(get("/api/ordenes"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$._embedded.ordenList").isArray())
                .andExpect(jsonPath("$._links.self").exists());

        verify(ordenService, times(1)).obtenerTodasLasOrdenes();
    }

    @Test
    @DisplayName("obtener orden por id con HATEOAS")
    public void testObtenerOrdenPorId() throws Exception {
        when(ordenService.obtenerOrdenPorId(1)).thenReturn(orden1);

        mockMvc.perform(get("/api/ordenes/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.cliente").value("Juan García"))
                .andExpect(jsonPath("$._links.self").exists())
                .andExpect(jsonPath("$._links['todas-ordenes']").exists());

        verify(ordenService, times(1)).obtenerOrdenPorId(1);
    }

    @Test
    @DisplayName("crear nueva orden con HATEOAS")
    public void testCrearOrden() throws Exception {
        Orden nuevaOrden = new Orden(3, "Carlos Ruiz", "Correa gato", 2, 9.99, "pendiente", "2024-04-29");
        when(ordenService.crearOrden("Carlos Ruiz", "Correa gato", 2, 9.99, "pendiente", "2024-04-29"))
                .thenReturn(nuevaOrden);

        mockMvc.perform(post("/api/ordenes")
                .contentType("application/json")
                .content("{\"cliente\":\"Carlos Ruiz\",\"producto\":\"Correa gato\",\"cantidad\":2,\"precio\":9.99,\"estado\":\"pendiente\",\"fecha\":\"2024-04-29\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.cliente").value("Carlos Ruiz"))
                .andExpect(jsonPath("$._links").exists());

        verify(ordenService, times(1)).crearOrden(anyString(), anyString(), anyInt(), anyDouble(), anyString(), anyString());
    }

    @Test
    @DisplayName("actualizar estado de orden")
    public void testActualizarEstado() throws Exception {
        orden1.setEstado("entregada");
        when(ordenService.actualizarEstado(1, "entregada")).thenReturn(true);
        when(ordenService.obtenerOrdenPorId(1)).thenReturn(orden1);

        mockMvc.perform(put("/api/ordenes/1/estado?nuevoEstado=entregada"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$._links").exists());

        verify(ordenService, times(1)).actualizarEstado(1, "entregada");
    }

    @Test
    @DisplayName("eliminar orden por id")
    public void testEliminarOrden() throws Exception {
        when(ordenService.eliminarOrden(1)).thenReturn(true);

        mockMvc.perform(delete("/api/ordenes/1"))
                .andExpect(status().isOk());

        verify(ordenService, times(1)).eliminarOrden(1);
    }
}
