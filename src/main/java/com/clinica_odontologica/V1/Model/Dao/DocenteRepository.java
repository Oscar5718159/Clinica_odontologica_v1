package com.clinica_odontologica.V1.Model.Dao;

import com.clinica_odontologica.V1.Model.Entity.Docente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;

public interface DocenteRepository extends JpaRepository<Docente, Long> {
    
    // ✅ AGREGAR ESTOS MÉTODOS
    List<Docente> findAll();  // Método explícito para obtener TODOS
    
    List<Docente> findByEstadoTrue();
    
    List<Docente> findByEspecialidadContainingIgnoreCase(String especialidad);
    
    List<Docente> findByNombreDocenteContainingIgnoreCase(String nombreDocente);
    
    List<Docente> findByClinicaIdClinica(Long idClinica);
    
    @Query("SELECT d FROM Docente d WHERE d.estado = true AND " +
           "(d.nombreDocente LIKE %:criterio% OR d.especialidad LIKE %:criterio%)")
    List<Docente> buscarActivosPorCriterio(@Param("criterio") String criterio);
    
    // ✅ Método para debug - contar total de docentes
    @Query("SELECT COUNT(d) FROM Docente d")
    long countTotalDocentes();
    
    @Query("SELECT COUNT(d) FROM Docente d WHERE d.estado = true")
    long countDocentesActivos();
}