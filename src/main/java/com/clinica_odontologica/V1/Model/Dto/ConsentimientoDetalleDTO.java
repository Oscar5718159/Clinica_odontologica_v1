package com.clinica_odontologica.V1.Model.Dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ConsentimientoDetalleDTO {
    private Long idConsentimiento;
    private String decision;
    private String explicacion;
    private Boolean estado;
    
    // Datos del paciente
    private Long idPaciente;
    private String nombrePaciente;
    private Integer edadPaciente;
    
    // Datos del tratamiento
    private Long idTratamiento;
    private String nombreTratamiento;
    private String descripcionTratamiento;
    private Double precioTratamiento;
    

}