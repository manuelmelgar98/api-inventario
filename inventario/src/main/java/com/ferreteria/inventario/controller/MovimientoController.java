package com.ferreteria.inventario.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.ferreteria.inventario.dto.MovimientoRequest;
import com.ferreteria.inventario.dto.MovimientoResponse;
import com.ferreteria.inventario.service.MovimientoService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/movimientos")
@RequiredArgsConstructor
public class MovimientoController {

    private final MovimientoService movimientoService;

    // POST /api/movimientos -> registra una entrada o venta y actualiza la existencia
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public MovimientoResponse registrar(@RequestBody @Valid MovimientoRequest request) {
        return movimientoService.registrar(request);
    }

    // GET /api/movimientos?productoId=1 -> historial de un producto
    // @RequestParam lee un parámetro de consulta (lo que va después del "?"),
    // a diferencia de @PathVariable, que lee una parte de la ruta (/movimientos/1)
    @GetMapping
    public List<MovimientoResponse> historial(@RequestParam Long productoId) {
        return movimientoService.historial(productoId);
    }
}