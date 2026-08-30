package com.foh.reto.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public class VentaDetalleDTO {

    private Long id;
    private LocalDate fecha;
    private String nombres;
    private String apellidos;
    private String dni;
    private String telefono;
    private String email;
    private List<ProductoDetalleDTO> productos;
    private BigDecimal total;

    public VentaDetalleDTO(Long id, LocalDate fecha, String nombres, String apellidos, String dni,
                            String telefono, String email, List<ProductoDetalleDTO> productos, BigDecimal total) {
        this.id = id;
        this.fecha = fecha;
        this.nombres = nombres;
        this.apellidos = apellidos;
        this.dni = dni;
        this.telefono = telefono;
        this.email = email;
        this.productos = productos;
        this.total = total;
    }

    public Long getId() {
        return id;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public String getNombres() {
        return nombres;
    }

    public String getApellidos() {
        return apellidos;
    }

    public String getDni() {
        return dni;
    }

    public String getTelefono() {
        return telefono;
    }

    public String getEmail() {
        return email;
    }

    public List<ProductoDetalleDTO> getProductos() {
        return productos;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public static class ProductoDetalleDTO {

        private String producto;
        private BigDecimal precioUnitario;
        private Integer cantidad;
        private BigDecimal subtotal;

        public ProductoDetalleDTO(String producto, BigDecimal precioUnitario, Integer cantidad, BigDecimal subtotal) {
            this.producto = producto;
            this.precioUnitario = precioUnitario;
            this.cantidad = cantidad;
            this.subtotal = subtotal;
        }

        public String getProducto() {
            return producto;
        }

        public BigDecimal getPrecioUnitario() {
            return precioUnitario;
        }

        public Integer getCantidad() {
            return cantidad;
        }

        public BigDecimal getSubtotal() {
            return subtotal;
        }
    }
}
