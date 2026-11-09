package com.ferreteria.inventario.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ferreteria.inventario.dto.ProductoRequest;
import com.ferreteria.inventario.dto.ProductoResponse;
import com.ferreteria.inventario.service.ProductoService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;



@RestController 
@RequestMapping("/api/productos")
@RequiredArgsConstructor 
public class ProductoController {
    private final ProductoService productoService;

    @GetMapping
    public List<ProductoResponse> listar(){
        return productoService.listar();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ProductoResponse crear(@RequestBody @Valid ProductoRequest productoRequest){
        return productoService.crear(productoRequest);
    }

    @GetMapping("/{id}")
    public ProductoResponse obtenerPorId(@PathVariable Long id){
        return productoService.obtenerPorId(id);
    }
    
    @PutMapping("/{id}")
    public ProductoResponse actualizar(@PathVariable Long id, @RequestBody @Valid ProductoRequest productoRequest){
        return productoService.actualizar(id, productoRequest);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void eliminar(@PathVariable Long id){
        productoService.eliminar(id);
    }
    
}
