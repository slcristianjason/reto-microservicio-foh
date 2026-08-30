package com.foh.reto.service;

import com.foh.reto.dto.VentaDetalleDTO;
import com.foh.reto.dto.VentaRequestDTO;
import com.foh.reto.dto.VentaResumenDTO;
import com.foh.reto.entity.Cliente;
import com.foh.reto.entity.DetalleVenta;
import com.foh.reto.entity.Producto;
import com.foh.reto.entity.Venta;
import com.foh.reto.repository.ClienteRepository;
import com.foh.reto.repository.ProductoRepository;
import com.foh.reto.repository.VentaRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Service
public class VentaService {

    private final VentaRepository ventaRepository;
    private final ClienteRepository clienteRepository;
    private final ProductoRepository productoRepository;

    public VentaService(VentaRepository ventaRepository, ClienteRepository clienteRepository,
                         ProductoRepository productoRepository) {
        this.ventaRepository = ventaRepository;
        this.clienteRepository = clienteRepository;
        this.productoRepository = productoRepository;
    }

    @Transactional
    public VentaDetalleDTO registrar(VentaRequestDTO dto) {
        Cliente cliente = clienteRepository.findById(dto.getClienteId())
                .orElseThrow(() -> new EntityNotFoundException("Cliente no encontrado: " + dto.getClienteId()));

        Venta venta = new Venta();
        venta.setCliente(cliente);
        venta.setFecha(dto.getFecha());

        for (VentaRequestDTO.DetalleRequestDTO d : dto.getDetalles()) {
            Producto producto = productoRepository.findById(d.getProductoId())
                    .orElseThrow(() -> new EntityNotFoundException("Producto no encontrado: " + d.getProductoId()));
            DetalleVenta detalle = new DetalleVenta();
            detalle.setVenta(venta);
            detalle.setProducto(producto);
            detalle.setCantidad(d.getCantidad());
            venta.getDetalles().add(detalle);
        }

        Venta guardada = ventaRepository.save(venta);
        return buscarDetallePorId(guardada.getId());
    }

    @Transactional(readOnly = true)
    public List<VentaResumenDTO> buscarPorFecha(LocalDate fecha) {
        List<Venta> ventas = fecha != null
                ? ventaRepository.findByFecha(fecha)
                : ventaRepository.findTop10ByOrderByIdDesc();
        return ventas.stream()
                .map(v -> new VentaResumenDTO(v.getId(), nombreCompleto(v.getCliente()), v.getFecha(), calcularTotal(v)))
                .toList();
    }

    @Transactional(readOnly = true)
    public VentaDetalleDTO buscarDetallePorId(Long id) {
        Venta venta = ventaRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Venta no encontrada: " + id));

        List<VentaDetalleDTO.ProductoDetalleDTO> productos = venta.getDetalles().stream()
                .map(d -> new VentaDetalleDTO.ProductoDetalleDTO(
                        d.getProducto().getNombre(),
                        d.getProducto().getPrecio(),
                        d.getCantidad(),
                        d.getProducto().getPrecio().multiply(BigDecimal.valueOf(d.getCantidad()))))
                .toList();

        Cliente cliente = venta.getCliente();

        return new VentaDetalleDTO(
                venta.getId(),
                venta.getFecha(),
                cliente.getNombres(),
                cliente.getApellidos(),
                cliente.getDni(),
                cliente.getTelefono(),
                cliente.getEmail(),
                productos,
                calcularTotal(venta));
    }

    private BigDecimal calcularTotal(Venta venta) {
        return venta.getDetalles().stream()
                .map(d -> d.getProducto().getPrecio().multiply(BigDecimal.valueOf(d.getCantidad())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    private String nombreCompleto(Cliente cliente) {
        return cliente.getNombres() + " " + cliente.getApellidos();
    }
}
