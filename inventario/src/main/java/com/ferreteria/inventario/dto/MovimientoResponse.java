package com.ferreteria.inventario.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.ferreteria.inventario.entity.TipoMovimiento;

public record MovimientoResponse(
        Long id,
        Long productoId,
        TipoMovimiento tipo,
        BigDecimal cantidad,
        LocalDateTime fecha,
        BigDecimal existenciaResultante,   // Cómo quedó la existencia del producto después del movimiento
        String advertencia                 // Mensaje si quedó negativa; null si todo está bien
) {}