package com.clinica_odontologica.V1.Service;

import com.clinica_odontologica.V1.Model.Entity.Clinica;
import com.clinica_odontologica.V1.Model.Entity.Materia;
import java.util.List;
import java.util.Optional;

public interface MateriaService {
    List<Materia> listarTodos();
    Optional<Materia> obtenerPorId(Long id);
    Materia guardar(Materia materia);
    void eliminar(Long id);
    List<Materia> obtenerPorClinica(Clinica clinica);
}