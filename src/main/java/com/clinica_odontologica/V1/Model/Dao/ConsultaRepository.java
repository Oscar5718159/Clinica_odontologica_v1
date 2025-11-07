package com.clinica_odontologica.V1.Model.Dao;

import com.clinica_odontologica.V1.Model.Entity.Consulta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.time.LocalDate;
import java.util.List;

public interface ConsultaRepository extends JpaRepository<Consulta, Long> {
    
    List<Consulta> findByFecha(LocalDate fecha);
    
    List<Consulta> findByPacienteIdPaciente(Long idPaciente);
    
    List<Consulta> findByEstudianteIdEstudiante(Long idEstudiante);
    
    @Query("SELECT c FROM Consulta c WHERE c.fecha BETWEEN :fechaInicio AND :fechaFin")
    List<Consulta> findByFechaBetween(@Param("fechaInicio") LocalDate fechaInicio, 
                                    @Param("fechaFin") LocalDate fechaFin);

    // En ConsultaRepository.java - AGREGAR:
    @Query("SELECT c FROM Consulta c WHERE " +
        "c.paciente.nombres LIKE %:criterio% OR " +
        "c.paciente.apellidoPaterno LIKE %:criterio% OR " +
        "c.paciente.apellidoMaterno LIKE %:criterio% OR " +
        "CAST(c.idConsulta AS string) LIKE %:criterio%")
    List<Consulta> buscarPorCriterio(@Param("criterio") String criterio);
}