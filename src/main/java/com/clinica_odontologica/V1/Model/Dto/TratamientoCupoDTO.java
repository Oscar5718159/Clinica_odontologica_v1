package com.clinica_odontologica.V1.Model.Dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class TratamientoCupoDTO {
    private Long idTratamiento;
    private String nombreTratamiento;
    private Double precioTratamiento;
    private Integer cuposDisponibles;
}