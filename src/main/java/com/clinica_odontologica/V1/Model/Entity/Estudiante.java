package com.clinica_odontologica.V1.Model.Entity;

import jakarta.persistence.*;

@Entity
@Table(name = "estudiante")
public class Estudiante {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_estudiante")
    private Long idEstudiante;

    @Column(nullable = false, length = 100)
    private String nombre;

    @Column(nullable = false, length = 100)
    private String apellidos;

    @Column(nullable = false)
    private Integer semestre;

    @Column(name = "codigo_estudiante", nullable = false, unique = true, length = 20)
    private String codigoEstudiante;

    // Constructores
    public Estudiante() {
    }

    public Estudiante(String nombre, String apellidos, Integer semestre, String codigoEstudiante) {
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.semestre = semestre;
        this.codigoEstudiante = codigoEstudiante;
    }

    // Getters y Setters
    public Long getIdEstudiante() {
        return idEstudiante;
    }

    public void setIdEstudiante(Long idEstudiante) {
        this.idEstudiante = idEstudiante;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public Integer getSemestre() {
        return semestre;
    }

    public void setSemestre(Integer semestre) {
        this.semestre = semestre;
    }

    public String getCodigoEstudiante() {
        return codigoEstudiante;
    }

    public void setCodigoEstudiante(String codigoEstudiante) {
        this.codigoEstudiante = codigoEstudiante;
    }
}