package com.clinica_odontologica.V1.Model.Dao;

import com.clinica_odontologica.V1.Model.Entity.Consentimiento;
import com.clinica_odontologica.V1.Model.Entity.DetalleConsentimiento;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface DetalleConsentimientoRepository extends JpaRepository<DetalleConsentimiento, Long> {
    List<DetalleConsentimiento> findByConsentimiento(Consentimiento consentimiento);
    List<DetalleConsentimiento> findByEstadoDetalle(Boolean estadoDetalle);
    List<DetalleConsentimiento> findByEstadoDetalleTrue();
}