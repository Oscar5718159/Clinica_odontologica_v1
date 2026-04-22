package com.clinica_odontologica.V1.Service;

import com.clinica_odontologica.V1.Model.Entity.Estudiante;
import com.clinica_odontologica.V1.Model.Entity.InscripcionMateria;
import com.clinica_odontologica.V1.Model.Entity.Materia;
import com.clinica_odontologica.V1.Model.Entity.Periodo;
import java.util.List;
import java.util.Optional;

public interface InscripcionMateriaService {
    List<InscripcionMateria> listarTodos();
    Optional<InscripcionMateria> obtenerPorId(Long id);
    InscripcionMateria guardar(InscripcionMateria inscripcion);
    void eliminar(Long id);
    void inscribirEstudianteEnPeriodo(Estudiante estudiante, Periodo periodo);
    List<InscripcionMateria> obtenerInscripcionesActivasEstudiante(Long estudianteId);
    List<InscripcionMateria> obtenerPorEstudianteYPeriodo(Estudiante estudiante, Periodo periodo);
    Optional<InscripcionMateria> obtenerPorEstudianteMateriaPeriodo(Estudiante estudiante, Materia materia, Periodo periodo);
    void cerrarPeriodo(Periodo periodo);

    Optional<InscripcionMateria> obtenerInscripcionActivaPorEstudianteYMateria(Long estudianteId, Long materiaId);
    
}