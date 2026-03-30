package com.ordenes.controller;

import com.ordenes.model.Orden;
import com.ordenes.service.OrdenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/ordenes")
public class OrdenController {

    @Autowired
    private OrdenService ordenService;

    @GetMapping
    public List<Orden> obtenerTodasLasOrdenes() {
        return ordenService.obtenerTodasLasOrdenes();
    }

   
    
}
