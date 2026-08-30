package com.foh.reto.controller;

import com.foh.reto.dto.ClienteDTO;
import com.foh.reto.entity.Cliente;
import com.foh.reto.service.ClienteService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/clientes")
public class ClienteController {

    private final ClienteService clienteService;

    public ClienteController(ClienteService clienteService) {
        this.clienteService = clienteService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Cliente registrar(@Valid @RequestBody ClienteDTO dto) {
        return clienteService.registrar(dto);
    }

    @GetMapping
    public List<Cliente> listar() {
        return clienteService.listar();
    }
}
