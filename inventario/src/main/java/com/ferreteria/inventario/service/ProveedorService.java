package com.ferreteria.inventario.service;

// import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.ferreteria.inventario.dto.ProveedorRequest;
import com.ferreteria.inventario.dto.ProveedorResponse;
import com.ferreteria.inventario.entity.Proveedor;
import com.ferreteria.inventario.exception.RecursoNoEncontradoException;
import com.ferreteria.inventario.repository.ProveedorRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor 
public class ProveedorService {
    private final ProveedorRepository proveedorRepository;

    private ProveedorResponse toResponse(Proveedor p){
        return new ProveedorResponse(
            p.getId(),
            p.getNombre(),
            p.getTelefono(),
            p.isActivo()
        );
    }

    public List<ProveedorResponse> listar() {
        return proveedorRepository.findByActivo(true)
            .stream()
            .map(this::toResponse)
            .toList();
    }
    // public List<ProveedorResponse> listar() {
    //     List<Proveedor> proveedores = proveedorRepository.findByActivo(true);
    //     List<ProveedorResponse> respuesta = new ArrayList<>();
    //     for (Proveedor p : proveedores) {
    //         respuesta.add(toResponse(p));
    //     }
    //     return respuesta;
    // }

    private Proveedor buscarEntidad(Long id){
        return proveedorRepository
            .findByIdAndActivo(id, true)
            .orElseThrow(() -> new RecursoNoEncontradoException("No se encontró el proveedor con id "+id));
    }

    public ProveedorResponse obtenerPorId(Long id) {
        return toResponse(buscarEntidad(id));
    }

    private void aplicarDatos(Proveedor p, ProveedorRequest proveedorRequest){
        p.setNombre(proveedorRequest.nombre());
        p.setTelefono(proveedorRequest.telefono());
    }

    public ProveedorResponse crear(ProveedorRequest proveedorRequest){
        Proveedor nuevoProveedor = new Proveedor();
        aplicarDatos(nuevoProveedor, proveedorRequest);
        return toResponse(proveedorRepository.save(nuevoProveedor));
    }

    public ProveedorResponse actualizar(Long id, ProveedorRequest proveedorRequest){
        Proveedor proveedorExistente = buscarEntidad(id);
        aplicarDatos(proveedorExistente, proveedorRequest);
        return toResponse(proveedorRepository.save(proveedorExistente));
    }

    public void eliminar(Long id){
        Proveedor proveedorAEliminar = buscarEntidad(id);
        proveedorAEliminar.setActivo(false);
        proveedorRepository.save(proveedorAEliminar);
    }
    
}
