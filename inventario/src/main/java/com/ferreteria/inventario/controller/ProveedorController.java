package com.ferreteria.inventario.controller;

import com.ferreteria.inventario.dto.ProveedorRequest;
import com.ferreteria.inventario.dto.ProveedorResponse;
import com.ferreteria.inventario.service.ProveedorService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;



@RestController
@RequestMapping("/api/proveedores")
@RequiredArgsConstructor 
public class ProveedorController {
    private final ProveedorService proveedorService;

    @GetMapping
    public List<ProveedorResponse> listar(){
        return proveedorService.listar();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ProveedorResponse crear(@RequestBody @Valid  ProveedorRequest proveedorRequest ) {
        return proveedorService.crear(proveedorRequest);
    }

    @GetMapping("/{id}")
    public ProveedorResponse obtenerPorId(@PathVariable Long id) {
        return proveedorService.obtenerPorId(id);
    }

    @PutMapping("/{id}")
    public ProveedorResponse actualizar(@PathVariable Long id, @RequestBody @Valid ProveedorRequest proveedorRequest) {
        return proveedorService.actualizar(id, proveedorRequest);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void eliminar(@PathVariable Long id){
        proveedorService.eliminar(id);
    }
    
    
}
