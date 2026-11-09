package com.ferreteria.inventario.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.ferreteria.inventario.dto.ProductoRequest;
import com.ferreteria.inventario.dto.ProductoResponse;
import com.ferreteria.inventario.entity.Producto;
import com.ferreteria.inventario.exception.CodigoDuplicadoException;
import com.ferreteria.inventario.exception.RecursoNoEncontradoException;
import com.ferreteria.inventario.repository.ProductoRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor 
public class ProductoService {
    private final ProductoRepository productoRepository;

    private ProductoResponse toResponse(Producto p){
        return new ProductoResponse(
            p.getId(),
            p.getCodigo(),
            p.getNombre(),
            p.getCategoria(),
            p.getUnidad(),
            p.getPrecioCompra(),
            p.getPrecioVenta(),
            p.getExistencia(),
            p.getStockMinimo(),
            p.isActivo()
        );
    }

    public List<ProductoResponse> listar() {
        return productoRepository.findByActivo(true)
            .stream()
            .map(this::toResponse)
            .toList();
    }

    private Producto buscarEntidad(Long id){
        return productoRepository
            .findByIdAndActivo(id, true)
            .orElseThrow(() -> new RecursoNoEncontradoException("No se encontró el Producto con id "+id));
    }

    public ProductoResponse obtenerPorId(Long id) {
        return toResponse(buscarEntidad(id));
    }

    private void aplicarDatos(Producto p, ProductoRequest productoRequest){
        p.setCodigo(productoRequest.codigo());
        p.setNombre(productoRequest.nombre());
        p.setCategoria(productoRequest.categoria());
        p.setPrecioCompra(productoRequest.precioCompra());
        p.setUnidad(productoRequest.unidad());
        p.setPrecioVenta(productoRequest.precioVenta());
        p.setStockMinimo(productoRequest.stockMinimo());
    }

    public ProductoResponse crear(ProductoRequest productoRequest){
        if (productoRepository.existsByCodigo(productoRequest.codigo())) {
            throw new CodigoDuplicadoException("Ya existe el producto con el código "+productoRequest.codigo());
        }
        Producto nuevoProducto = new Producto();
        aplicarDatos(nuevoProducto, productoRequest);
        return toResponse(productoRepository.save(nuevoProducto));
    }

    public ProductoResponse actualizar(Long id, ProductoRequest productoRequest){
        Producto productoExistente = buscarEntidad(id);
        String codigoNuevo = productoRequest.codigo();
        if (!codigoNuevo.equals(productoExistente.getCodigo()) && productoRepository.existsByCodigo(codigoNuevo)) {
            throw new CodigoDuplicadoException("Ya existe el producto con el código "+codigoNuevo);
        }
        aplicarDatos(productoExistente, productoRequest);
        return toResponse(productoRepository.save(productoExistente));
    }

    public void eliminar(Long id){
        Producto productoAEliminar = buscarEntidad(id);
        productoAEliminar.setActivo(false);
        productoRepository.save(productoAEliminar);
    }
    
}
