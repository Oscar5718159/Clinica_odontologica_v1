package com.clinica_odontologica.V1.Model.Dao;
import com.clinica_odontologica.V1.Model.Entity.TipoTratamiento;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TipoTratamientoRepository extends JpaRepository<TipoTratamiento, Long> {

    List<TipoTratamiento> findByClinicaIdClinica(Long idClinica);
    
}
