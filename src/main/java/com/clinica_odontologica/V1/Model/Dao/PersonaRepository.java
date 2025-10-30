package com.clinica_odontologica.V1.Model.Dao;

import com.clinica_odontologica.V1.Model.Entity.Persona;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface PersonaRepository extends JpaRepository<Persona, Long> {
    
    List<Persona> findByNombreContainingIgnoreCase(String nombre);
    List<Persona> findByApellidoPaternoContainingIgnoreCase(String apellidoPaterno);
    List<Persona> findByApellidoMaternoContainingIgnoreCase(String apellidoMaterno);
    List<Persona> findByEdad(Integer edad);
    List<Persona> findBySexo(Character sexo);
    List<Persona> findByNombreAndApellidoPaterno(String nombre, String apellidoPaterno);
}