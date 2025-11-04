package com.clinica_odontologica.V1.Service;

import com.clinica_odontologica.V1.Model.Entity.ExamenExtraOral;
import java.util.List;
import java.util.Optional;

public interface ExamenExtraOralService {
    
    List<ExamenExtraOral> obtenerTodos();
    
    Optional<ExamenExtraOral> obtenerPorId(Long id);
    
    ExamenExtraOral guardar(ExamenExtraOral examenExtraOral);
    
    void eliminar(Long id);
}