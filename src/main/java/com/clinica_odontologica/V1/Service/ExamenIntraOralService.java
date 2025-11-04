package com.clinica_odontologica.V1.Service;

import com.clinica_odontologica.V1.Model.Entity.ExamenIntraOral;
import java.util.List;
import java.util.Optional;

public interface ExamenIntraOralService {
    
    List<ExamenIntraOral> obtenerTodos();
    
    Optional<ExamenIntraOral> obtenerPorId(Long id);
    
    ExamenIntraOral guardar(ExamenIntraOral examenIntraOral);
    
    void eliminar(Long id);
}