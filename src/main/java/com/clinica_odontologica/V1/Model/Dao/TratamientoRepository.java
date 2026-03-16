package com.clinica_odontologica.V1.Model.Dao;
import com.clinica_odontologica.V1.Model.Entity.Tratamiento;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
public interface TratamientoRepository extends JpaRepository<Tratamiento, Long> {


    List<Tratamiento> findByTipoTratamientoIdTipoTratamiento(Long idTipoTratamiento);
    
    

}
