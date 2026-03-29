package com.ordenes.service;

import com.ordenes.model.Orden;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrdenService {
    private List<Orden> ordenes = new ArrayList<>();
    private int nextId = 9;

    public OrdenService() {

        ordenes.add(new Orden(1, "Juan Pérez", "Collar para perro", 2, 15.99, "confirmada", "2026-03-25"));
        ordenes.add(new Orden(2, "María López", "Comida para gatos", 5, 24.50, "confirmada", "2026-03-26"));
        ordenes.add(new Orden(3, "Carlos Martínez", "Juguete para perro", 3, 12.99, "cancelada", "2026-03-27"));
     
    }
}
