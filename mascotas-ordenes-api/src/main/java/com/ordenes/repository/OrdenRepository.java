package com.ordenes.repository;

import com.ordenes.model.Orden;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface OrdenRepository extends JpaRepository<Orden, Integer> {
    List<Orden> findByEstado(String estado);
    List<Orden> findByCliente(String cliente);
}
