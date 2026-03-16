package com.clinica_odontologica.V1.Model.Dao;

import com.clinica_odontologica.V1.Model.Entity.Recibo;
import com.clinica_odontologica.V1.Model.Entity.SeguimientoTratamiento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface SeguimientoTratamientoRepository extends JpaRepository<SeguimientoTratamiento, Long> {
    List<SeguimientoTratamiento> findByConsentimientoIdConsentimiento(Long idConsentimiento);


}