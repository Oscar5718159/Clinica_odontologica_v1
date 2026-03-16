package com.clinica_odontologica.V1.Model.Dao;

import com.clinica_odontologica.V1.Model.Entity.RayosX;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RayosXRepository extends JpaRepository<RayosX, Long> {
    // Si necesitas métodos de búsqueda personalizados, agrégalos aquí
}