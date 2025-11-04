package com.clinica_odontologica.V1.Service;

import com.clinica_odontologica.V1.Model.Entity.Consulta;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface ConsultaService {
    
    List<Consulta> obtenerTodos();
    
    Optional<Consulta> obtenerPorId(Long id);
    
    Consulta guardar(Consulta consulta);
    
    void eliminar(Long id);
    
    List<Consulta> obtenerPorFecha(LocalDate fecha);
    
    List<Consulta> obtenerPorPaciente(Long idPaciente);
    
    List<Consulta> obtenerPorEstudiante(Long idEstudiante);
    
    List<Consulta> obtenerPorRangoFechas(LocalDate fechaInicio, LocalDate fechaFin);
}