package com.ferreteria.inventario.dto;

import java.math.BigDecimal;

import com.ferreteria.inventario.entity.Categoria;
import com.ferreteria.inventario.entity.Unidad;

public record ProductoResponse (
    Long id,
    String codigo, 
    String nombre,
    Categoria categoria,
    Unidad unidad,
    BigDecimal precioCompra,
    BigDecimal precioVenta,
    BigDecimal existencia,
    BigDecimal stockMinimo,
    boolean activo
) {}
