package com.clinica_odontologica.V1.Service;

import com.clinica_odontologica.V1.Model.Entity.Persona;
import java.util.List;
import java.util.Optional;

public interface PersonaService {
    
    List<Persona> obtenerTodos();
    
    Optional<Persona> obtenerPorId(Long id);
    
    Persona guardar(Persona persona);
    
    Persona actualizar(Long id, Persona persona);
    
    void eliminar(Long id);
    
    List<Persona> buscarPorNombre(String nombre);
    
    List<Persona> buscarPorApellido(String apellido);
    
    List<Persona> buscarPorEdad(Integer edad);
    
    List<Persona> buscarPorSexo(Character sexo);
    
    List<Persona> buscarPorNombreYApellido(String nombre, String apellido);
}