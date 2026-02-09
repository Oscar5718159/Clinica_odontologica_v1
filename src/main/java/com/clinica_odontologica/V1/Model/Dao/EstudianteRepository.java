package com.clinica_odontologica.V1.Model.Dao;

import com.clinica_odontologica.V1.Model.Entity.Estudiante;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.List;


public interface EstudianteRepository extends JpaRepository<Estudiante, Long> {
    List<Estudiante> findAllByCodigoEstudiante(Integer codigoEstudiante);


}