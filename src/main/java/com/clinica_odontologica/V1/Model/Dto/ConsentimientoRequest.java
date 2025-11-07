package com.clinica_odontologica.V1.Model.Dto;

public class ConsentimientoRequest {
    private Long idConsulta;
    private Long idDocente;
    private String explicacion;
    private String decision;

    // Constructores
    public ConsentimientoRequest() {}

    public ConsentimientoRequest(Long idConsulta, Long idDocente, String explicacion, String decision) {
        this.idConsulta = idConsulta;
        this.idDocente = idDocente;
        this.explicacion = explicacion;
        this.decision = decision;
    }

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
}