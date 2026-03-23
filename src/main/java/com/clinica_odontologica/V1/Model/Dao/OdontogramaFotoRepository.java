package com.clinica_odontologica.V1.Model.Dao;
import com.clinica_odontologica.V1.Model.Entity.OdontogramaFoto;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
public interface OdontogramaFotoRepository extends JpaRepository<OdontogramaFoto, Long> {
    List<OdontogramaFoto> findByConsultaIdConsulta(Long IdConsulta);
}