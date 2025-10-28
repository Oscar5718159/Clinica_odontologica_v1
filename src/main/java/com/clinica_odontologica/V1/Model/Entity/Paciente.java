package com.clinica_odontologica.V1.Model.Entity;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "paciente")
public class Paciente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_paciente")
    private Long idPaciente;

    @Column(name = "historial_clinico", unique = true, nullable = false, length = 50)
    private String historialClinico;

    @ManyToOne
    @JoinColumn(name = "id_persona", nullable = false)  
    private Persona persona;

    @Column(name = "lugar_nacimiento", nullable = false, length = 100)
    private String lugarNacimiento;

    @Column(name = "fecha_nacimiento", nullable = false)
    private LocalDate fechaNacimiento;

    @Column(nullable = false, length = 50)
    private String ocupacion;

    @Column(nullable = false, length = 200)
    private String direccion;

    @Column(nullable = false)
    private String telefono; // Cambiado a String para formatos internacionales

    @Column(name = "estado_civil", nullable = false, length = 1)
    private String estadoCivil; // S, C, V, D

    @Column(name = "naciones_originarias", length = 50)
    private String nacionesOriginarias;

    @Column(length = 50)
    private String idioma;

    @Column(nullable = false)
    private Integer ci;

    // Constructor
    public Paciente() {
    }

    // Getters y setters
    public Long getIdPaciente() {
        return idPaciente;
    }

    public void setIdPaciente(Long idPaciente) {
        this.idPaciente = idPaciente;
    }

    public String getHistorialClinico() {
        return historialClinico;
    }

    public void setHistorialClinico(String historialClinico) {
        this.historialClinico = historialClinico;
    }

    public Persona getPersona() {
        return persona;
    }

    public void setPersona(Persona persona) {
        this.persona = persona;
    }

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

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
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
    public Integer getCi() {
        return ci;
    }

    public void setCi(Integer ci) {
        this.ci = ci;
    }
}