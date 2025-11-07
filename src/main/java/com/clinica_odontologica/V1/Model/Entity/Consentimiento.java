package com.clinica_odontologica.V1.Model.Entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "consentimiento")
public class Consentimiento {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_consentimiento")
    private Long idConsentimiento;

    @ManyToOne
    @JoinColumn(name = "id_consulta", nullable = false)
    private Consulta consulta;

    @ManyToOne
    @JoinColumn(name = "id_docente", nullable = false)
    private Docente docente;

    @Column(name = "explicacion", columnDefinition = "TEXT")
    private String explicacion;

    @Column(name = "fecha")
    private LocalDateTime fecha;

    @Column(name = "decision", length = 20)
    private String decision;

    @Column(name = "estado")
    private Boolean estado = true;

    // Constructores
    public Consentimiento() {
        this.fecha = LocalDateTime.now();
    }

    public Consentimiento(Consulta consulta, Docente docente, String explicacion, String decision) {
        this.consulta = consulta;
        this.docente = docente;
        this.explicacion = explicacion;
        this.decision = decision;
        this.fecha = LocalDateTime.now();
        this.estado = true;
    }

    // Getters y Setters
    public Long getIdConsentimiento() {
        return idConsentimiento;
    }

    public void setIdConsentimiento(Long idConsentimiento) {
        this.idConsentimiento = idConsentimiento;
    }

    public Consulta getConsulta() {
        return consulta;
    }

    public void setConsulta(Consulta consulta) {
        this.consulta = consulta;
    }

    public Docente getDocente() {
        return docente;
    }

    public void setDocente(Docente docente) {
        this.docente = docente;
    }

    public String getExplicacion() {
        return explicacion;
    }

    public void setExplicacion(String explicacion) {
        this.explicacion = explicacion;
    }

    public LocalDateTime getFecha() {
        return fecha;
    }

    public void setFecha(LocalDateTime fecha) {
        this.fecha = fecha;
    }

    public String getDecision() {
        return decision;
    }

    public void setDecision(String decision) {
        this.decision = decision;
    }

    public Boolean getEstado() {
        return estado;
    }

    public void setEstado(Boolean estado) {
        this.estado = estado;
    }
}