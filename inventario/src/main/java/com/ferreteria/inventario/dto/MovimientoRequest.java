package com.ferreteria.inventario.dto;

import java.math.BigDecimal;

import com.ferreteria.inventario.entity.TipoMovimiento;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record MovimientoRequest(
        @NotNull(message = "El producto es obligatorio") Long productoId,   // Solo el id, no el producto completo
        @NotNull(message = "El tipo de movimiento es obligatorio") TipoMovimiento tipo,
        @NotNull(message = "La cantidad es obligatoria")
        @Positive(message = "La cantidad debe ser mayor a 0") BigDecimal cantidad  // El signo lo decide el tipo, no la cantidad
) {}