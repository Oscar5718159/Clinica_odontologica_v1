package com.clinica_odontologica.V1.Service;

import com.clinica_odontologica.V1.Model.Entity.TratamientoMedico;
import java.util.List;
import java.util.Optional;

public interface TratamientoMedicoService {
    
    List<TratamientoMedico> obtenerTodos();
    
    Optional<TratamientoMedico> obtenerPorId(Long id);
    
    TratamientoMedico guardar(TratamientoMedico tratamientoMedico);
    
    void eliminar(Long id);
}