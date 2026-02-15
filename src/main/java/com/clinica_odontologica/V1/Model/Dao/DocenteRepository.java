package com.clinica_odontologica.V1.Model.Dao;

import com.clinica_odontologica.V1.Model.Entity.Docente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;

public interface DocenteRepository extends JpaRepository<Docente, Long> {
    
    
    List<Docente> findAll();  // Método explícito para obtener TODOS
    
    List<Docente> findByEstadoTrue();
    
    boolean existsByCodigoDocente(Integer codigoDocente);
    
    List<Docente> findByClinicaIdClinica(Long idClinica);
    
    
    
    // ✅ Método para debug - contar total de docentes
    @Query("SELECT COUNT(d) FROM Docente d")
    long countTotalDocentes();
    
    @Query("SELECT COUNT(d) FROM Docente d WHERE d.estado = true")
    long countDocentesActivos();
}