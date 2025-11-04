package com.clinica_odontologica.V1.Service;

import com.clinica_odontologica.V1.Model.Entity.PatologiaPersonal;
import java.util.List;
import java.util.Optional;

public interface PatologiaPersonalService {
    
    List<PatologiaPersonal> obtenerTodos();
    
    Optional<PatologiaPersonal> obtenerPorId(Long id);
    
    PatologiaPersonal guardar(PatologiaPersonal patologiaPersonal);
    
    PatologiaPersonal actualizar(Long id, PatologiaPersonal patologiaPersonal);
    
    void eliminar(Long id);
    

}