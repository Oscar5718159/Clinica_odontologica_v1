package com.clinica_odontologica.V1.Model.Dao;

import com.clinica_odontologica.V1.Model.Entity.DetalleConsentimiento;
import com.clinica_odontologica.V1.Model.Entity.InscripcionMateria;
import com.clinica_odontologica.V1.Model.Entity.TratamientoRealizado;
import com.clinica_odontologica.V1.Model.Entity.Tratamiento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;
import java.util.Optional;

public interface TratamientoRealizadoRepository extends JpaRepository<TratamientoRealizado, Long> {
    List<TratamientoRealizado> findByInscripcionMateriaAndValidacionTrue(InscripcionMateria inscripcion);
    List<TratamientoRealizado> findByInscripcionMateria(InscripcionMateria inscripcion);

    Optional<TratamientoRealizado> findByDetalleConsentimiento(DetalleConsentimiento detalle);


    @Query("SELECT COUNT(tr) FROM TratamientoRealizado tr WHERE tr.inscripcionMateria = :inscripcion AND tr.tratamiento = :tratamiento AND tr.validacion = true")
    long countValidadosByInscripcionAndTratamiento(@Param("inscripcion") InscripcionMateria inscripcion, @Param("tratamiento") Tratamiento tratamiento);
// En TratamientoRealizadoRepository
    @Query("SELECT COALESCE(SUM(tr.cantidadTratamiento), 0) FROM TratamientoRealizado tr WHERE tr.inscripcionMateria = :inscripcion AND tr.tratamiento = :tratamiento AND tr.validacion = true")
    Integer sumCantidadValidadosByInscripcionAndTratamiento(@Param("inscripcion") InscripcionMateria inscripcion, @Param("tratamiento") Tratamiento tratamiento);   

}
