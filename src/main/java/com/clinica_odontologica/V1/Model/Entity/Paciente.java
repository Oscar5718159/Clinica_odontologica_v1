package com.clinica_odontologica.V1.Model.Entity;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "paciente")
@PrimaryKeyJoinColumn(name = "id_persona")
public class Paciente extends Persona {

    @Column(name = "lugar_nacimiento", nullable = false, length = 100)
    private String lugarNacimiento;

    @Column(name = "fecha_nacimiento", nullable = false)
    private LocalDate fechaNacimiento;

    @Column(nullable = false, length = 50)
    private String ocupacion;

    @Column(nullable = false, length = 200)
    private String direccion;

    @Column(nullable = false)
    private Integer telefono; // Cambiado a String para formatos internacionales

    @Column(name = "estado_civil", nullable = false, length = 1)
    private String estadoCivil; // Cambiado a String: S, C, V, D

    @Column(name = "naciones_originarias", length = 50)
    private String nacionesOriginarias;

    @Column(length = 50)
    private String idioma;

    @Column(name = "fecha_registro")
    private LocalDateTime fechaRegistro;

    @PrePersist
    protected void onCreate() {
        fechaRegistro = LocalDateTime.now();
    }

    // Constructor
    public Paciente() {
        super();
    }

    // Getters y setters
    public String getLugarNacimiento() {
        return lugarNacimiento;
    }

    public void setLugarNacimiento(String lugarNacimiento) {
        this.lugarNacimiento = lugarNacimiento;
    }

    public LocalDate getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(LocalDate fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public String getOcupacion() {
        return ocupacion;
    }

    public void setOcupacion(String ocupacion) {
        this.ocupacion = ocupacion;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public Integer getTelefono() {
        return telefono;
    }

    public void setTelefono(Integer telefono) {
        this.telefono = telefono;
    }

    public String getEstadoCivil() {
        return estadoCivil;
    }

    public void setEstadoCivil(String estadoCivil) {
        this.estadoCivil = estadoCivil;
    }

    public String getNacionesOriginarias() {
        return nacionesOriginarias;
    }

    public void setNacionesOriginarias(String nacionesOriginarias) {
        this.nacionesOriginarias = nacionesOriginarias;
    }

    public String getIdioma() {
        return idioma;
    }

    public void setIdioma(String idioma) {
        this.idioma = idioma;
    }

    public LocalDateTime getFechaRegistro() {
        return fechaRegistro;
    }
}