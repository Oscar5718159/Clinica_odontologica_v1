package com.clinica_odontologica.V1.Model.Dao;

import com.clinica_odontologica.V1.Model.Entity.Recibo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ReciboRepository extends JpaRepository<Recibo, Long> {

    @Query("SELECT r FROM Recibo r WHERE r.seguimiento.consentimiento.consulta.paciente.idPaciente = :idPaciente AND r.estadoPago = :estado")
List<Recibo> findByPacienteAndEstado(@Param("idPaciente") Long idPaciente, @Param("estado") String estado);
    //  código de estudiante 
    @Query("SELECT r FROM Recibo r WHERE r.seguimiento.consentimiento.estudiante.codigoEstudiante = :codigo AND r.estadoPago = :estado")
    List<Recibo> findByCodigoEstudianteAndEstado(@Param("codigo") Integer codigo, @Param("estado") String estado);
}