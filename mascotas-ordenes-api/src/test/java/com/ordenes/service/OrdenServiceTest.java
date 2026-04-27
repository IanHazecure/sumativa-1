package com.ordenes.service;

import com.ordenes.model.Orden;
import com.ordenes.repository.OrdenRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
@DisplayName("servicio de ordenes")
public class OrdenServiceTest {

    @Autowired
    private OrdenService ordenService;

    @MockBean
    private OrdenRepository ordenRepository;

    private Orden ordenPrueba;

    @BeforeEach
    public void setUp() {
        ordenPrueba = new Orden(1, "Juan Flores", "Alimento gatos", 5, 25.99, "pendiente", "2024-04-27");
    }

    @Test
    @DisplayName("obtener orden por id")
    public void testObtenerOrdenPorId() {
        when(ordenRepository.findById(1)).thenReturn(Optional.of(ordenPrueba));

        Orden resultado = ordenService.obtenerOrdenPorId(1);

        assertNotNull(resultado);
        assertEquals(1, resultado.getId());
        assertEquals("Juan Flores", resultado.getCliente());
        assertEquals("Alimento gatos", resultado.getProducto());
        verify(ordenRepository, times(1)).findById(1);
    }

    @Test
    @DisplayName("actualizar estado de orden")
    public void testActualizarEstado() {
        when(ordenRepository.findById(1)).thenReturn(Optional.of(ordenPrueba));
        when(ordenRepository.save(any(Orden.class))).thenReturn(ordenPrueba);

        boolean resultado = ordenService.actualizarEstado(1, "entregada");

        assertTrue(resultado);
        assertEquals("entregada", ordenPrueba.getEstado());
        verify(ordenRepository, times(1)).save(any(Orden.class));
    }

    @Test
    @DisplayName("crear orden")
    public void testCrearOrden() {
        Orden nuevaOrden = new Orden();
        nuevaOrden.setCliente("María García");
        nuevaOrden.setProducto("Juguete perro");
        nuevaOrden.setCantidad(3);
        nuevaOrden.setPrecio(15.50);
        nuevaOrden.setEstado("pendiente");
        nuevaOrden.setFecha("2024-04-28");

        when(ordenRepository.save(any(Orden.class))).thenReturn(nuevaOrden);

        Orden resultado = ordenService.crearOrden("María García", "Juguete perro", 3, 15.50, "pendiente", "2024-04-28");

        assertNotNull(resultado);
        assertEquals("María García", resultado.getCliente());
        assertEquals(3, resultado.getCantidad());
        verify(ordenRepository, times(1)).save(any(Orden.class));
    }
}
