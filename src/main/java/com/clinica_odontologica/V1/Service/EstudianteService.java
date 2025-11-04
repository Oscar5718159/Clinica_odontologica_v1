package com.clinica_odontologica.V1.Service;

import com.clinica_odontologica.V1.Model.Entity.Estudiante;
import java.util.List;
import java.util.Optional;

public interface EstudianteService {
    
    List<Estudiante> obtenerTodos();
    
    Optional<Estudiante> obtenerPorId(Long id);
    
    Estudiante guardar(Estudiante estudiante);
    
    boolean validarCredenciales(String nombre, String codigoEstudiante);
    
    Optional<Estudiante> obtenerPorCodigo(String codigoEstudiante);
}