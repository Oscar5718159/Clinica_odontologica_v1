package com.clinica_odontologica.V1.Model.Dto;
// Crear esta clase en tu proyecto
public class DocenteDTO {
    private Long idDocente;
    private String nombreDocente;
    private String especialidad;
    private Boolean estado;
    
    // Constructor
    public DocenteDTO(Long idDocente, String nombreDocente, String especialidad, Boolean estado) {
        this.idDocente = idDocente;
        this.nombreDocente = nombreDocente;
        this.especialidad = especialidad;
        this.estado = estado;
    }
    
    // Getters
    public Long getIdDocente() { return idDocente; }
    public String getNombreDocente() { return nombreDocente; }
    public String getEspecialidad() { return especialidad; }
    public Boolean getEstado() { return estado; }
}