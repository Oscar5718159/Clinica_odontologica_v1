package com.clinica_odontologica.V1.Model.Entity;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "docente")
public class Docente {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_docente")
    private Long idDocente;

    @Column(name = "nombre_docente", length = 100, nullable = false)
    private String nombreDocente;

    @Column(name = "especialidad", length = 100)
    private String especialidad;

    @Column(name = "estado")
    private Boolean estado = true;

    @ManyToOne
    @JoinColumn(name = "id_clinica")
    private Clinica clinica;

    @OneToMany(mappedBy = "docente")
    private List<Consentimiento> consentimientos;

    // Constructores
    public Docente() {}

    public Docente(String nombreDocente, String especialidad, Clinica clinica) {
        this.nombreDocente = nombreDocente;
        this.especialidad = especialidad;
        this.clinica = clinica;
        this.estado = true;
    }

    // Getters y Setters
    public Long getIdDocente() {
        return idDocente;
    }

    public void setIdDocente(Long idDocente) {
        this.idDocente = idDocente;
    }

    public String getNombreDocente() {
        return nombreDocente;
    }

    public void setNombreDocente(String nombreDocente) {
        this.nombreDocente = nombreDocente;
    }

    public String getEspecialidad() {
        return especialidad;
    }

    public void setEspecialidad(String especialidad) {
        this.especialidad = especialidad;
    }

    public Boolean getEstado() {
        return estado;
    }

    public void setEstado(Boolean estado) {
        this.estado = estado;
    }

    public Clinica getClinica() {
        return clinica;
    }

    public void setClinica(Clinica clinica) {
        this.clinica = clinica;
    }

    public List<Consentimiento> getConsentimientos() {
        return consentimientos;
    }

    public void setConsentimientos(List<Consentimiento> consentimientos) {
        this.consentimientos = consentimientos;
    }
}