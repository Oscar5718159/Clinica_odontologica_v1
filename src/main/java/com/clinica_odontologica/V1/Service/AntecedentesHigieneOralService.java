package com.clinica_odontologica.V1.Service;

import com.clinica_odontologica.V1.Model.Entity.AntecedentesHigieneOral;
import java.util.List;
import java.util.Optional;

public interface AntecedentesHigieneOralService {
    
    List<AntecedentesHigieneOral> obtenerTodos();
    
    Optional<AntecedentesHigieneOral> obtenerPorId(Long id);
    
    AntecedentesHigieneOral guardar(AntecedentesHigieneOral antecedentesHigieneOral);
    
    void eliminar(Long id);
}