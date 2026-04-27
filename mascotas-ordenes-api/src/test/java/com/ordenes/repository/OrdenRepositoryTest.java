package com.ordenes.repository;

import com.ordenes.model.Orden;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@DisplayName("repositorio de ordenes")
public class OrdenRepositoryTest {

    @Autowired
    private OrdenRepository ordenRepository;

    private Orden orden1;
    private Orden orden2;

    @BeforeEach
    public void setUp() {
        ordenRepository.deleteAll();

        orden1 = new Orden(0, "Carlos López", "Correa perro", 2, 12.99, "pendiente", "2024-04-27");
        orden2 = new Orden(0, "Ana Ruiz", "Arena gatos", 4, 8.50, "entregada", "2024-04-27");

        ordenRepository.save(orden1);
        ordenRepository.save(orden2);
    }

    @Test
    @DisplayName("ordenes por estado")
    public void testFindByEstado() {
        List<Orden> resultado = ordenRepository.findByEstado("pendiente");

        assertNotNull(resultado);
        assertTrue(resultado.size() >= 1);
        assertTrue(resultado.stream().allMatch(o -> o.getEstado().equals("pendiente")));
    }

    @Test
    @DisplayName("guardar orden")
    public void testSaveOrden() {
        Orden nuevaOrden = new Orden(0, "Pedro Martínez", "Collar gato", 1, 5.99, "pendiente", "2024-04-28");

        Orden ordenGuardada = ordenRepository.save(nuevaOrden);

        assertNotNull(ordenGuardada);
        assertNotNull(ordenGuardada.getId());
        assertTrue(ordenGuardada.getId() > 0);
        assertEquals("Pedro Martínez", ordenGuardada.getCliente());
    }

    @Test
    @DisplayName("eliminar orden por id")
    public void testDeleteById() {
        int id = orden1.getId();

        ordenRepository.deleteById(id);
        Optional<Orden> resultado = ordenRepository.findById(id);

        assertTrue(resultado.isEmpty());
    }
}
