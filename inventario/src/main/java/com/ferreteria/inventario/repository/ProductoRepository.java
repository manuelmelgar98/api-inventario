package com.ferreteria.inventario.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ferreteria.inventario.entity.Producto;
import java.util.List;
import java.util.Optional;


public interface ProductoRepository extends JpaRepository<Producto, Long> {
    List<Producto> findByActivo(boolean activo);
    Optional<Producto> findByIdAndActivo(Long id, boolean activo);
    boolean existsByCodigo(String codigo);
}
