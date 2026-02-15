package com.clinica_odontologica.V1.Service;

import com.clinica_odontologica.V1.Model.Entity.Clinica;
import java.util.List;
import java.util.Optional;

public interface ClinicaService {
    
    List<Clinica> obtenerTodas();
    
    Optional<Clinica> obtenerPorId(Long id);
    
    Clinica guardar(Clinica clinica);
    
    void eliminar(Long id);
    
    List<Clinica> buscarPorNombre(String nombre);
    
    List<Clinica> buscarPorTurno(String turno);

}