package com.clinica_odontologica.V1.Model.Dao;

import com.clinica_odontologica.V1.Model.Entity.PatologiaPersonal;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PatologiaPersonalRepository extends JpaRepository<PatologiaPersonal, Long> {
}