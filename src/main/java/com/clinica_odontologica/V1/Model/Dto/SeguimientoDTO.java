package com.clinica_odontologica.V1.Model.Dto;

import lombok.Data;
import java.util.List;

@Data
public class SeguimientoDTO {
    private Long idConsentimiento;
    private String presionArterial;
    private String frecuenciaCardiaca;
    private String frecuenciaRespiratoria;
    private String temperatura;
    private String saturacionOxigeno;
    private String peso;
    private String subjetivo;
    private String objetivo;
    private String analisis;
    private String planAccion;
    private List<Long> idsRayosX;  // IDs de rayos X seleccionados
}