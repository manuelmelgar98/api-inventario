package com.ferreteria.inventario.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name="movimientos")
@Getter
@Setter
@NoArgsConstructor 
public class Movimiento {

    @Id 
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column (name = "tipo", nullable = false, length = 10)
    @Enumerated(EnumType.STRING)
    private TipoMovimiento tipo;

    @Column (name = "cantidad", nullable = false, precision = 12, scale = 3)
    private BigDecimal cantidad;

    @Column (name = "fecha", nullable = false, updatable = false)
    @CreationTimestamp 
    private LocalDateTime fecha;

    @ManyToOne (fetch = FetchType.LAZY)
    @JoinColumn (name = "producto_id", nullable = false)
    private Producto producto;
    
}
