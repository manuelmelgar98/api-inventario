package com.ferreteria.inventario.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ferreteria.inventario.entity.Movimiento;

public interface MovimientoRepository extends JpaRepository<Movimiento, Long> {
    List<Movimiento> findByProductoIdOrderByFechaDesc(Long productoId);
}