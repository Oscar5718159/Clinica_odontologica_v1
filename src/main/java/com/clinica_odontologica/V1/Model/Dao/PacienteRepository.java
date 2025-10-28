package com.clinica_odontologica.V1.Model.Dao;

import com.clinica_odontologica.V1.Model.Entity.Paciente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;
import java.util.Optional;

public interface PacienteRepository extends JpaRepository<Paciente, Long> {
    
    // Métodos específicos para Paciente
    List<Paciente> findByOcupacion(String ocupacion);
    
    List<Paciente> findByLugarNacimientoContainingIgnoreCase(String lugarNacimiento);
    
    List<Paciente> findByEstadoCivil(String estadoCivil);
    
    // ✅ MÉTODO PARA BUSCAR POR HISTORIAL CLÍNICO
    Optional<Paciente> findByHistorialClinico(String historialClinico);
    // ✅ Buscar por nombre (dentro del objeto Persona)
    List<Paciente> findByPersonaNombreContainingIgnoreCase(String nombre);

    // ✅ Buscar por apellido (dentro del objeto Persona)
    List<Paciente> findByPersonaApellidoContainingIgnoreCase(String apellido);

    // ✅ BUSCAR POR NOMBRE COMPLETO EXACTO (case-insensitive)
    @Query("SELECT p FROM Paciente p WHERE " +
           "LOWER(p.persona.nombre) = LOWER(:nombre) AND " +
           "LOWER(p.persona.apellido) = LOWER(:apellido)")
    List<Paciente> buscarPorNombreCompletoExacto(@Param("nombre") String nombre, 
                                               @Param("apellido") String apellido);

    // ✅ NUEVO MÉTODO: Buscar por nombre completo (concatena nombre y apellido)
    @Query("SELECT p FROM Paciente p WHERE " +
           "LOWER(CONCAT(p.persona.nombre, ' ', p.persona.apellido)) " +
           "LIKE LOWER(CONCAT('%', :nombreCompleto, '%'))")
    List<Paciente> buscarPorNombreCompleto(@Param("nombreCompleto") String nombreCompleto);
}