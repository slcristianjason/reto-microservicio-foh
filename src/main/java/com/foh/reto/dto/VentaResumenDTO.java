package com.foh.reto.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

public class VentaResumenDTO {

    private Long id;
    private String cliente;
    private LocalDate fecha;
    private BigDecimal total;

    public VentaResumenDTO(Long id, String cliente, LocalDate fecha, BigDecimal total) {
        this.id = id;
        this.cliente = cliente;
        this.fecha = fecha;
        this.total = total;
    }

    public Long getId() {
        return id;
    }

    public String getCliente() {
        return cliente;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public BigDecimal getTotal() {
        return total;
    }
}
