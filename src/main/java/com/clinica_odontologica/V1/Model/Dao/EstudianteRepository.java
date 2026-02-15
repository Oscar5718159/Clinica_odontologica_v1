package com.clinica_odontologica.V1.Model.Dao;

import com.clinica_odontologica.V1.Model.Entity.Estudiante;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EstudianteRepository extends JpaRepository<Estudiante, Long> {
    
    List<Estudiante> findAllByCodigoEstudiante(Integer codigoEstudiante);
    
    Optional<Estudiante> findByCodigoEstudiante(Integer codigoEstudiante);
    
    Boolean existsByCodigoEstudiante(Integer codigoEstudiante);
    
    List<Estudiante> findByGestion(String gestion);
    
    List<Estudiante> findByBloqueado(Boolean bloqueado);
    
    @Query("SELECT e FROM Estudiante e WHERE e.Usuario.nombreUsuario = :username")
    Optional<Estudiante> buscarPorNombreUsuario(@Param("username") String username);
    
    @Query("SELECT e FROM Estudiante e WHERE e.Usuario.persona.nombre LIKE %:nombre%")
    List<Estudiante> buscarPorNombrePersona(@Param("nombre") String nombre);
}