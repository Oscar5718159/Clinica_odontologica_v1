package com.clinica_odontologica.V1.Service;

import com.clinica_odontologica.V1.Model.Entity.Informante;
import java.util.List;
import java.util.Optional;

public interface InformanteService {
    
    List<Informante> obtenerTodos();
    
    Optional<Informante> obtenerPorId(Long id);
    
    Informante guardar(Informante informante);
    
    void eliminar(Long id);

}