package com.clinica_odontologica.V1.Service;

import com.clinica_odontologica.V1.Model.Entity.Docente;
import java.util.List;
import java.util.Optional;

public interface DocenteService {
    
    List<Docente> obtenerTodos();
    
    List<Docente> obtenerTodosActivos();
    
    Optional<Docente> obtenerPorId(Long id);
    
    Docente guardar(Docente docente);
    
    void eliminar(Long id);
    
    boolean existePorCodigoDocente(Integer codigoDocente);
    

    // List<Docente> buscarPorEspecialidad(String especialidad);
    
    // List<Docente> buscarPorNombre(String nombreDocente);
    
    
    // List<Docente> buscarActivosPorCriterio(String criterio);
}