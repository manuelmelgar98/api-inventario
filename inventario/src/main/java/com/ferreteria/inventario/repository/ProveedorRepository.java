package com.ferreteria.inventario.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ferreteria.inventario.entity.Proveedor;
import java.util.List;
import java.util.Optional;


public interface ProveedorRepository extends JpaRepository<Proveedor, Long> {

    List<Proveedor> findByActivo(boolean activo);
    Optional<Proveedor> findById(Long id);
    Optional<Proveedor> findByIdAndActivo(Long id, boolean activo);
}
