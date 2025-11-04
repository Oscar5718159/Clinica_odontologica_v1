package com.clinica_odontologica.V1.Model.Dto;

public class LoginRequest {
    private String usuario;
    private String contraseña;

    // Constructores
    public LoginRequest() {
    }

    public LoginRequest(String usuario, String contraseña) {
        this.usuario = usuario;
        this.contraseña = contraseña;
    }

    // Getters y Setters
    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getContraseña() {
        return contraseña;
    }

    public void setContraseña(String contraseña) {
        this.contraseña = contraseña;
    }
}