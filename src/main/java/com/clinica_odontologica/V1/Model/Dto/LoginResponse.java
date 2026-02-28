package com.clinica_odontologica.V1.Model.Dto;

public class LoginResponse {
    private boolean success;
    private String message;
    private String nombre;
    private String apellidos;
    private String semestre;
    private Long idEstudiante; // NUEVO CAMPO

    // Constructores
    public LoginResponse() {
    }

    public LoginResponse(boolean success, String message) {
        this.success = success;
        this.message = message;
    }

    public LoginResponse(boolean success, String message, String nombre, 
                        String apellidos, String semestre, Long idEstudiante) {
        this.success = success;
        this.message = message;
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.semestre = semestre;
        this.idEstudiante = idEstudiante; // NUEVO
    }

    // Getters y Setters
    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
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

    public String getSemestre() {
        return semestre;
    }

    public void setSemestre(String semestre) {
        this.semestre = semestre;
    }

    public Long getIdEstudiante() { // NUEVO GETTER
        return idEstudiante;
    }

    public void setIdEstudiante(Long idEstudiante) { // NUEVO SETTER
        this.idEstudiante = idEstudiante;
    }
}