package com.clinica_odontologica.V1.Model.Dto;

import java.time.LocalDateTime;
import java.util.List;

public class ConsentimientoDTO {

    // IDs para relaciones
    private Long idConsulta;
    private Long idDocente;
    private Long idTratamiento;   
    private Long idEstudiante;

    // Datos copia del tratamiento (para mantener histórico)
    private String nombreTratamiento;
    private String descripcionTratamiento;
    private Double precioTratamiento;

    // Datos del consentimiento
    private String explicacion;
    private String decision;       // "aceptar" o "rechazar"
    private LocalDateTime fecha;

    // Datos de insumos (solo si se acepta y se seleccionan)
    private List<SolicitudInsumoDTO> insumos;

    // Constructores
    public ConsentimientoDTO() {}

    // Getters y Setters
    public Long getIdConsulta() {
        return idConsulta;
    }

    public void setIdConsulta(Long idConsulta) {
        this.idConsulta = idConsulta;
    }

    public Long getIdDocente() {
        return idDocente;
    }

    public void setIdDocente(Long idDocente) {
        this.idDocente = idDocente;
    }

    public Long getIdTratamiento() {
        return idTratamiento;
    }

    public void setIdTratamiento(Long idTratamiento) {
        this.idTratamiento = idTratamiento;
    }


    public String getNombreTratamiento() {
        return nombreTratamiento;
    }

    public Long getIdEstudiante() {
        return idEstudiante;
    }

    public void setIdEstudiante(Long idEstudiante) {
        this.idEstudiante = idEstudiante;
    }
    public void setNombreTratamiento(String nombreTratamiento) {
        this.nombreTratamiento = nombreTratamiento;
    }

    public String getDescripcionTratamiento() {
        return descripcionTratamiento;
    }

    public void setDescripcionTratamiento(String descripcionTratamiento) {
        this.descripcionTratamiento = descripcionTratamiento;
    }

    public Double getPrecioTratamiento() {
        return precioTratamiento;
    }

    public void setPrecioTratamiento(Double precioTratamiento) {
        this.precioTratamiento = precioTratamiento;
    }

    public String getExplicacion() {
        return explicacion;
    }

    public void setExplicacion(String explicacion) {
        this.explicacion = explicacion;
    }

    public String getDecision() {
        return decision;
    }

    public void setDecision(String decision) {
        this.decision = decision;
    }

    public LocalDateTime getFecha() {
        return fecha;
    }

    public void setFecha(LocalDateTime fecha) {
        this.fecha = fecha;
    }

    public List<SolicitudInsumoDTO> getInsumos() {
        return insumos;
    }

    public void setInsumos(List<SolicitudInsumoDTO> insumos) {
        this.insumos = insumos;
    }
}