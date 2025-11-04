package com.clinica_odontologica.V1.Service;

import com.clinica_odontologica.V1.Model.Entity.AntecedentesBucodentales;
import java.util.List;
import java.util.Optional;

public interface AntecedentesBucodentalesService {
    
    List<AntecedentesBucodentales> obtenerTodos();
    
    Optional<AntecedentesBucodentales> obtenerPorId(Long id);
    
    AntecedentesBucodentales guardar(AntecedentesBucodentales antecedentesBucodentales);
    
    void eliminar(Long id);
}