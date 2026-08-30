package com.foh.reto.controller;

import com.foh.reto.dto.VentaDetalleDTO;
import com.foh.reto.dto.VentaRequestDTO;
import com.foh.reto.dto.VentaResumenDTO;
import com.foh.reto.service.VentaService;
import jakarta.validation.Valid;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/ventas")
public class VentaController {

    private final VentaService ventaService;

    public VentaController(VentaService ventaService) {
        this.ventaService = ventaService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public VentaDetalleDTO registrar(@Valid @RequestBody VentaRequestDTO dto) {
        return ventaService.registrar(dto);
    }

    @GetMapping
    public List<VentaResumenDTO> buscarPorFecha(@RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fecha) {
        return ventaService.buscarPorFecha(fecha);
    }

    @GetMapping("/{id}")
    public VentaDetalleDTO buscarPorId(@PathVariable Long id) {
        return ventaService.buscarDetallePorId(id);
    }
}
