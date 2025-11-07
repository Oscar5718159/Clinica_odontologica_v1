package com.clinica_odontologica.V1.Model.Dao;

import com.clinica_odontologica.V1.Model.Entity.Clinica;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ClinicaRepository extends JpaRepository<Clinica, Long> {
    
    List<Clinica> findByNombreClinicaContainingIgnoreCase(String nombre);
    
    List<Clinica> findByTurno(String turno);
}