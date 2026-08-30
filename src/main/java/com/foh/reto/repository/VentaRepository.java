package com.foh.reto.repository;

import com.foh.reto.entity.Venta;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface VentaRepository extends JpaRepository<Venta, Long> {

    List<Venta> findByFecha(LocalDate fecha);

    List<Venta> findTop10ByOrderByIdDesc();
}
