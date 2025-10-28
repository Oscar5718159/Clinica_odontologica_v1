package com.clinica_odontologica.V1.Model.Dao;

import com.clinica_odontologica.V1.Model.Entity.Paciente;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface PacienteRepository extends JpaRepository<Paciente, Long> {
    
    // Métodos específicos para Paciente
    List<Paciente> findByOcupacion(String ocupacion);
    
    List<Paciente> findByLugarNacimientoContainingIgnoreCase(String lugarNacimiento);
    
    List<Paciente> findByEstadoCivil(String estadoCivil);
    
    // ✅ MÉTODOS CORREGIDOS - Buscar a través de la relación Persona
    List<Paciente> findByPersonaNombreContainingIgnoreCase(String nombre);
    
    List<Paciente> findByPersonaApellidoContainingIgnoreCase(String apellido);
    
    // ✅ MÉTODO PARA BUSCAR POR HISTORIAL CLÍNICO
    Optional<Paciente> findByHistorialClinico(String historialClinico);
}