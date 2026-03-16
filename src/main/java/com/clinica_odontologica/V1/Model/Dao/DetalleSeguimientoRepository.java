package com.clinica_odontologica.V1.Model.Dao;

import com.clinica_odontologica.V1.Model.Entity.DetalleSeguimiento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DetalleSeguimientoRepository extends JpaRepository<DetalleSeguimiento, Long> {
    // Normalmente no necesitas métodos adicionales aquí
}