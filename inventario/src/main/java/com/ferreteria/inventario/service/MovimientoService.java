package com.ferreteria.inventario.service;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ferreteria.inventario.dto.MovimientoRequest;
import com.ferreteria.inventario.dto.MovimientoResponse;
import com.ferreteria.inventario.entity.Movimiento;
import com.ferreteria.inventario.entity.Producto;
import com.ferreteria.inventario.exception.RecursoNoEncontradoException;
import com.ferreteria.inventario.repository.MovimientoRepository;
import com.ferreteria.inventario.repository.ProductoRepository;

import lombok.RequiredArgsConstructor;

@Service                    // Spring crea y administra esta clase como un bean
@RequiredArgsConstructor    // Lombok genera el constructor con los campos final (inyección por constructor)
public class MovimientoService {

    // Necesitamos los dos repositories: uno para guardar el movimiento
    // y otro para leer y actualizar la existencia del producto
    private final MovimientoRepository movimientoRepository;
    private final ProductoRepository productoRepository;

    // @Transactional: todo lo que pasa dentro de este método es una sola unidad.
    // O se guarda TODO (existencia nueva + movimiento) o, si algo falla con una
    // excepción unchecked, se deshace TODO (rollback). Nunca queda a medias.
    @Transactional
    public MovimientoResponse registrar(MovimientoRequest request) {

        // 1. Buscar el producto activo. Si no existe o está dado de baja,
        //    lanzamos la excepción y el manejador global responde 404.
        Producto producto = productoRepository
                .findByIdAndActivo(request.productoId(), true)
                .orElseThrow(() -> new RecursoNoEncontradoException(
                        "No se encontró el producto con id " + request.productoId()));

        // 2. Calcular la nueva existencia según el tipo de movimiento.
        //    Con BigDecimal no se usa + ni -, se usan los métodos add y subtract.
        //    Este "switch" moderno (Java 14+) devuelve un valor directamente.
        BigDecimal nuevaExistencia = switch (request.tipo()) {
            case ENTRADA -> producto.getExistencia().add(request.cantidad());      // Entrada: suma
            case VENTA -> producto.getExistencia().subtract(request.cantidad());   // Venta: resta
        };

        // 3. Actualizar la existencia del producto en memoria.
        //    Como el producto se leyó dentro de esta transacción, Hibernate detecta
        //    el cambio (dirty checking) y hará el UPDATE al terminar el método,
        //    aunque no llamemos a productoRepository.save().
        producto.setExistencia(nuevaExistencia);

        // 4. Crear el movimiento y relacionarlo con el producto completo.
        //    En la base solo se guardará el id del producto (llave foránea).
        //    La fecha la pone sola @CreationTimestamp.
        Movimiento movimiento = new Movimiento();
        movimiento.setTipo(request.tipo());
        movimiento.setCantidad(request.cantidad());
        movimiento.setProducto(producto);

        // 5. Guardar el movimiento (INSERT). Nos devuelve la entidad con id y fecha.
        Movimiento guardado = movimientoRepository.save(movimiento);

        // 6. Regla del negocio: se permite vender aunque quede negativo,
        //    pero se avisa. Para comparar BigDecimal se usa compareTo:
        //    devuelve -1 si es menor, 0 si es igual y 1 si es mayor.
        String advertencia = null;
        if (nuevaExistencia.compareTo(BigDecimal.ZERO) < 0) {
            advertencia = "La existencia del producto " + producto.getCodigo()
                    + " quedó en negativo: " + nuevaExistencia;
        }

        // 7. Convertir a DTO y regresar. Aquí incluimos la existencia resultante
        //    y la advertencia (que será null si todo está bien).
        return toResponse(guardado, nuevaExistencia, advertencia);
    }

    // Historial de movimientos de un producto, del más reciente al más antiguo.
    // readOnly = true: le avisa a Hibernate que solo leeremos, lo que le permite
    // optimizar (por ejemplo, no revisar cambios en las entidades).
    @Transactional(readOnly = true)
    public List<MovimientoResponse> historial(Long productoId) {
        return movimientoRepository.findByProductoIdOrderByFechaDesc(productoId)
                .stream()
                // En el historial no calculamos existencia ni advertencia por movimiento,
                // así que van en null.
                .map(m -> toResponse(m, null, null))
                .toList();
    }

    // Conversor entidad -> DTO.
    // m.getProducto().getId() no dispara una consulta extra aunque la relación sea LAZY,
    // porque Hibernate ya conoce el id (es la llave foránea guardada en el movimiento).
    private MovimientoResponse toResponse(Movimiento m, BigDecimal existenciaResultante, String advertencia) {
        return new MovimientoResponse(
                m.getId(),
                m.getProducto().getId(),
                m.getTipo(),
                m.getCantidad(),
                m.getFecha(),
                existenciaResultante,
                advertencia
        );
    }
}