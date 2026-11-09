package com.ferreteria.inventario.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record ProveedorRequest(
    @NotBlank(message = "El nombre es obligatorio") @Size(max = 100, message = "El tamaño máximo del nombre es de 100 caracteres") String nombre, 
    @Size(max = 20, message = "El tamaño máximo del teléfono es de 20 caracteres") String telefono
    ) {}
