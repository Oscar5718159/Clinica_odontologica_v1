package com.clinica_odontologica.V1.Model.Dto;
import lombok.Data;
import java.time.LocalDateTime;

@Data
public class ReciboDTO {
    

    private Long idRecibo;
    private Long idSeguimiento;
    private Long idConsentimiento;
    private LocalDateTime fechaEmision;
    private Double montoTotal;
    private String estadoPago;
}