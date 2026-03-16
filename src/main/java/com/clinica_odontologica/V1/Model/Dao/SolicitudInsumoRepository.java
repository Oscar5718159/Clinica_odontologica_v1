package com.clinica_odontologica.V1.Model.Dao;

import com.clinica_odontologica.V1.Model.Entity.SolicitudInsumo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SolicitudInsumoRepository extends JpaRepository<SolicitudInsumo, Long> {
    // Si necesitas métodos adicionales, los agregas aquí
}