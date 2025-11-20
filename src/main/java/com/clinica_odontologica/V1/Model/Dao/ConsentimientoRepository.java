package com.clinica_odontologica.V1.Model.Dao;

import com.clinica_odontologica.V1.Model.Entity.Consentimiento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;
import java.util.Optional;

public interface ConsentimientoRepository extends JpaRepository<Consentimiento, Long> {

    Optional<Consentimiento> findByConsultaIdConsulta(Long idConsulta);
    
    // Buscar consentimientos por paciente (a través de consulta)
    @Query("SELECT c FROM Consentimiento c WHERE c.consulta.paciente.idPaciente = :idPaciente")
    List<Consentimiento> findByPacienteId(@Param("idPaciente") Long idPaciente);
    
    // Buscar consentimientos por docente
    List<Consentimiento> findByDocenteIdDocente(Long idDocente);
    
    // Buscar consentimientos activos
    List<Consentimiento> findByEstadoTrue();
    
    // Verificar si existe consentimiento para una consulta
    boolean existsByConsultaIdConsulta(Long idConsulta);
}