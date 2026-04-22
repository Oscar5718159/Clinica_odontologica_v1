package com.clinica_odontologica.V1.Model.Dao;

import com.clinica_odontologica.V1.Model.Entity.Cupos;
import com.clinica_odontologica.V1.Model.Entity.Materia;
import com.clinica_odontologica.V1.Model.Entity.Tratamiento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import com.clinica_odontologica.V1.Model.Dto.TratamientoCupoDTO;

import java.util.List;
import java.util.Optional;

public interface CupoRepository extends JpaRepository<Cupos, Long> {
    List<Cupos> findByMateria(Materia materia);
    Optional<Cupos> findByMateriaAndTratamiento(Materia materia, Tratamiento tratamiento);

    // Método que devuelve una lista de objetos con la información que necesitas
    @Query("SELECT new com.clinica_odontologica.V1.Model.Dto.TratamientoCupoDTO(" +
           "t.idTratamiento, t.nombreTratamiento, t.precioTratamiento, c.cuposDisponibles) " +
           "FROM Cupos c JOIN c.tratamiento t WHERE c.materia.id_materia = :idMateria")
    List<TratamientoCupoDTO> findTratamientosWithCuposByMateriaId(@Param("idMateria") Long idMateria);
}