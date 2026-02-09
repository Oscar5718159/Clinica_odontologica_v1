package com.clinica_odontologica.V1.Service;
import com.clinica_odontologica.V1.Model.Entity.PrestamoActual;
import java.util.List;
import java.util.Optional;


public interface PrestamoActualService {
    
    List<PrestamoActual> obtenerTodos();
    Optional<PrestamoActual> obtenerPorId(Long id);
    PrestamoActual guardar(PrestamoActual prestamoActual);
    PrestamoActual actualizar(Long id, PrestamoActual prestamoActualActualizado);
    void eliminar(Long id);
    List<PrestamoActual> buscarPorIdEstudiante(Long idEstudiante);
    List<PrestamoActual> buscarPorIdArchivo(Long idArchivo);
}
