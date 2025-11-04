package com.clinica_odontologica.V1.Model.Entity;

import jakarta.persistence.*;

@Entity
@Table(name = "patologiapersonal")
public class PatologiaPersonal {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_patologiapersonal")
    private Long idPatologiaPersonal;

    @Column(name = "nombre_patologia", length = 255)
    private String nombrePatologia;

    @Column(name = "alergias")
    private Boolean alergias;

    @Column(name = "embarazo")
    private Boolean embarazo;

    @Column(name = "semana_embarazo")
    private Integer semanaEmbarazo;

    // Constructores
    public PatologiaPersonal() {
    }

    public PatologiaPersonal(String nombrePatologia, Boolean alergias, Boolean embarazo, Integer semanaEmbarazo) {
        this.nombrePatologia = nombrePatologia;
        this.alergias = alergias;
        this.embarazo = embarazo;
        this.semanaEmbarazo = semanaEmbarazo;
    }

    // Getters y Setters
    public Long getIdPatologiaPersonal() {
        return idPatologiaPersonal;
    }

    public void setIdPatologiaPersonal(Long idPatologiaPersonal) {
        this.idPatologiaPersonal = idPatologiaPersonal;
    }

    public String getNombrePatologia() {
        return nombrePatologia;
    }

    public void setNombrePatologia(String nombrePatologia) {
        this.nombrePatologia = nombrePatologia;
    }

    public Boolean getAlergias() {
        return alergias;
    }

    public void setAlergias(Boolean alergias) {
        this.alergias = alergias;
    }

    public Boolean getEmbarazo() {
        return embarazo;
    }

    public void setEmbarazo(Boolean embarazo) {
        this.embarazo = embarazo;
    }

    public Integer getSemanaEmbarazo() {
        return semanaEmbarazo;
    }

    public void setSemanaEmbarazo(Integer semanaEmbarazo) {
        this.semanaEmbarazo = semanaEmbarazo;
    }
}