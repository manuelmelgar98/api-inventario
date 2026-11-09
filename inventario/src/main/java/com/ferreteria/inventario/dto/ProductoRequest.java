package com.ferreteria.inventario.dto;

import java.math.BigDecimal;

import com.ferreteria.inventario.entity.Categoria;
import com.ferreteria.inventario.entity.Unidad;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;

public record ProductoRequest (
    @NotBlank(message = "El código es obligatorio") @Size(max = 20, message = "El tamaño máximo del código es de 20 caracteres") String codigo, 
    @NotBlank(message = "El nombre es obligatorio") @Size(max = 100, message = "El tamaño máximo del nombre es de 100 caracteres") String nombre,
    @NotNull(message = "La categoría es obligatoria") Categoria categoria,
    @NotNull(message = "La unidad es obligatoria") Unidad unidad,
    @NotNull(message = "El precio de compra es obligatorio") @Positive(message = "Precio de compra debe ser mayor a 0") BigDecimal precioCompra,
    @NotNull(message = "El precio de venta es obligatorio") @Positive(message = "Precio de venta debe ser mayor a 0") BigDecimal precioVenta,
    @NotNull(message = "El stock mínimo es obligatorio") @PositiveOrZero(message = "El stock debe ser mayor o igual a 0") BigDecimal stockMinimo
) {}
