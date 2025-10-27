package com.clinica_odontologica.V1.Model.Dao;

import com.clinica_odontologica.V1.Model.Entity.Persona;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;

public interface PersonaRepository extends JpaRepository<Persona, Long> {
    
    // Métodos para Persona (funcionan para Persona y todas sus subclases)
    List<Persona> findByNombreContainingIgnoreCase(String nombre);
    
    List<Persona> findByApellidoContainingIgnoreCase(String apellido);
    
    List<Persona> findByEdad(Integer edad);
    
    List<Persona> findBySexo(Character sexo);
    
    // Buscar por nombre y apellido
    List<Persona> findByNombreAndApellido(String nombre, String apellido);
    
    // Buscar personas mayores de cierta edad
    @Query("SELECT p FROM Persona p WHERE p.edad > :edad")
    List<Persona> findPersonasMayoresQue(@Param("edad") Integer edad);
}