package com.clinica_odontologica.V1.Service;

import com.clinica_odontologica.V1.Model.Entity.Consentimiento;
import java.util.List;
import java.util.Optional;

public interface ConsentimientoService {
    
    List<Consentimiento> obtenerTodos();
    
    Optional<Consentimiento> obtenerPorId(Long id);
    
    Optional<Consentimiento> obtenerPorConsulta(Long idConsulta);
    
    Consentimiento guardar(Consentimiento consentimiento);
    
    void eliminar(Long id);
    
    boolean existePorConsulta(Long idConsulta);
    
    List<Consentimiento> obtenerPorPaciente(Long idPaciente);
}