package com.clinica_odontologica.V1.Service;

import com.clinica_odontologica.V1.Model.Entity.Paciente;
import java.util.List;
import java.util.Optional;

public interface PacienteService {
    
    List<Paciente> obtenerTodos();
    
    Optional<Paciente> obtenerPorId(Long id);
    
    Paciente guardar(Paciente paciente);
    
    Paciente actualizar(Long id, Paciente paciente);
    
    void eliminar(Long id);
    
    // Métodos específicos de Paciente
    List<Paciente> buscarPorOcupacion(String ocupacion);

    List<Paciente> buscarPorLugarNacimiento(String lugarNacimiento);
    
    List<Paciente> buscarPorEstadoCivil(String estadoCivil);
    
    // Métodos de búsqueda por datos de Persona (relación)
    List<Paciente> buscarPorNombre(String nombre);
    
    List<Paciente> buscarPorApellido(String apellido);
    
    // Método para buscar por historial clínico
    Optional<Paciente> buscarPorHistorialClinico(String historialClinico);
    
    // ✅ NUEVO MÉTODO PARA BÚSQUEDA POR NOMBRE COMPLETO
    List<Paciente> buscarPorNombreCompleto(String nombreCompleto);
}