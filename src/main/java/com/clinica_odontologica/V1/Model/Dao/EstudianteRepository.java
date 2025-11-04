package com.clinica_odontologica.V1.Model.Dao;

import com.clinica_odontologica.V1.Model.Entity.Estudiante;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.Optional;

public interface EstudianteRepository extends JpaRepository<Estudiante, Long> {
    
    // Buscar por código de estudiante
    Optional<Estudiante> findByCodigoEstudiante(String codigoEstudiante);
    
    // Buscar por nombre y código (para login)
    @Query("SELECT e FROM Estudiante e WHERE e.nombre = :nombre AND e.codigoEstudiante = :codigo")
    Optional<Estudiante> findByNombreAndCodigo(@Param("nombre") String nombre, 
                                              @Param("codigo") String codigo);
    
    // Buscar por nombre (para verificar usuario)
    Optional<Estudiante> findByNombre(String nombre);
}