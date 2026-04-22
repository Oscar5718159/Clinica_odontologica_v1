package com.clinica_odontologica.V1.Model.Dto;
import lombok.*;
import java.util.List;
@Getter
@Setter

public class ProgresoEstudianteDTO {
    private Long idEstudiante;
    private String nombreCompleto; 
    private Integer codigoEstudiante;
    private List<ProgresoMateriaDTO> materias;
}