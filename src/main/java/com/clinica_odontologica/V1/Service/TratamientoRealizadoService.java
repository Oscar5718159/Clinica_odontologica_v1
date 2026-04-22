package com.clinica_odontologica.V1.Service;

import com.clinica_odontologica.V1.Model.Entity.DetalleConsentimiento;
import com.clinica_odontologica.V1.Model.Entity.InscripcionMateria;
import com.clinica_odontologica.V1.Model.Entity.TratamientoRealizado;
import com.clinica_odontologica.V1.Model.Dao.TratamientoRealizadoRepository;
import java.util.List;
import java.util.Optional;

public interface TratamientoRealizadoService {
    List<TratamientoRealizado> listarTodos();
    Optional<TratamientoRealizado> obtenerPorId(Long id);
    TratamientoRealizado guardar(TratamientoRealizado tratamientoRealizado);
    void eliminar(Long id);
    List<TratamientoRealizado> obtenerPorInscripcion(InscripcionMateria inscripcion);
    List<TratamientoRealizado> obtenerValidadosPorInscripcion(InscripcionMateria inscripcion);
    boolean cumpleCuposMateria(InscripcionMateria inscripcion);

    void validarTratamiento(Long id, Long docenteId, Integer cantidadReal);

    Optional<TratamientoRealizado> findByDetalleConsentimiento(DetalleConsentimiento detalle);
}