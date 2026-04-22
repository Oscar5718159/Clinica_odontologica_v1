package com.clinica_odontologica.V1.Model.Dto;
import lombok.*;


@Getter
@Setter
public class DetalleConsentimientoPendienteDTO {
    private Long idDetalle;
    private String pacienteNombre;
    private String tratamientoNombre;
    private Integer cantidadSolicitada;
    private Long idTratamientoRealizado;
    private Integer cantidadActual;
    private Boolean validado;
    // getters y setters
}