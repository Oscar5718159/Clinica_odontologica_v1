package com.clinica_odontologica.V1.Service;

import com.clinica_odontologica.V1.Model.Dto.TratamientoCupoDTO;
import com.clinica_odontologica.V1.Model.Entity.Cupos;
import com.clinica_odontologica.V1.Model.Entity.Materia;
import com.clinica_odontologica.V1.Model.Entity.Tratamiento;
import java.util.List;
import java.util.Optional;

public interface CupoService {
    List<Cupos> listarTodos();
    Optional<Cupos> obtenerPorId(Long id);
    Cupos guardar(Cupos cupo);
    void eliminar(Long id);
    List<Cupos> obtenerPorMateria(Materia materia);
    Optional<Cupos> obtenerPorMateriaYTratamiento(Materia materia, Tratamiento tratamiento);
    List<TratamientoCupoDTO> obtenerTratamientosConCuposPorMateria(Long idMateria);
}