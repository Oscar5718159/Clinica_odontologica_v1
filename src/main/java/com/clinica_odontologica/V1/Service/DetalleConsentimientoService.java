package com.clinica_odontologica.V1.Service;

import com.clinica_odontologica.V1.Model.Entity.DetalleConsentimiento;
import com.clinica_odontologica.V1.Model.Entity.Estudiante;
import java.util.List;
import java.util.Optional;

public interface DetalleConsentimientoService {
    List<DetalleConsentimiento> listarTodos();
    Optional<DetalleConsentimiento> obtenerPorId(Long id);
    DetalleConsentimiento guardar(DetalleConsentimiento detalle);
    void eliminar(Long id);
    List<DetalleConsentimiento> obtenerPorConsentimiento(Long consentimientoId);
    List<DetalleConsentimiento> obtenerPorEstudianteAsignadoYEstado(Estudiante estudiante, Boolean estado);
    void actualizarEstado(Long id, Boolean nuevoEstado);
    List<DetalleConsentimiento> listarPendientes();
}