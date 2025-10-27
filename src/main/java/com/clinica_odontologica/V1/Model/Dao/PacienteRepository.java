package com.clinica_odontologica.V1.Model.Dao;

import com.clinica_odontologica.V1.Model.Entity.Paciente;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface PacienteRepository extends JpaRepository<Paciente, Long> {
    
    // Métodos específicos para Paciente
    List<Paciente> findByOcupacion(String ocupacion);
    
    List<Paciente> findByLugarNacimientoContainingIgnoreCase(String lugarNacimiento);
    
    List<Paciente> findByEstadoCivil(Character estadoCivil);
    
    List<Paciente> findByNacionesOriginariasContainingIgnoreCase(String nacionesOriginarias);
    
    
    // Métodos que heredan de Persona también funcionan
    List<Paciente> findByNombreContainingIgnoreCase(String nombre);
    
    List<Paciente> findByApellidoContainingIgnoreCase(String apellido);
    
    List<Paciente> findByEdad(Integer edad);
}