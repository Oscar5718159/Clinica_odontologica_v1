package com.clinica_odontologica.V1.Service;

import com.clinica_odontologica.V1.Model.Dto.ProgresoMateriaDTO;
import com.clinica_odontologica.V1.Model.Entity.Estudiante;
import java.util.List;
import java.util.Optional;

public interface EstudianteService {
    
    List<Estudiante> obtenerTodos();
    
    Optional<Estudiante> obtenerPorId(Long id);
    
    Estudiante guardar(Estudiante estudiante);

    List<Estudiante> buscarPorCodigoEstudiante(Integer codigoEstudiante);

    // ========== NUEVOS MÉTODOS ==========
    Optional<Estudiante> buscarPorCodigoExacto(Integer codigoEstudiante);
    
    Boolean existePorCodigoEstudiante(Integer codigoEstudiante);
    
    List<Estudiante> buscarPorGestion(String gestion);
    
    List<Estudiante> buscarPorBloqueado(Boolean bloqueado);
    
    Optional<Estudiante> buscarPorNombreUsuario(String username);

    List<ProgresoMateriaDTO> obtenerProgresoPorEstudiante(Long idEstudiante);

    
}