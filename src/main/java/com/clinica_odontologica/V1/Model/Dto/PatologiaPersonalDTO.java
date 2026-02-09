package com.clinica_odontologica.V1.Model.Dto;

public class PatologiaPersonalDTO {
    
    private String nombrePatologia;
    private Boolean alergias;
    private Boolean embarazo;
    private Integer semanaEmbarazo;
    
    // Getters y Setters
    public String getNombrePatologia() { return nombrePatologia; }
    public void setNombrePatologia(String nombrePatologia) { this.nombrePatologia = nombrePatologia; }
    
    public Boolean getAlergias() { return alergias; }
    public void setAlergias(Boolean alergias) { this.alergias = alergias; }
    
    public Boolean getEmbarazo() { return embarazo; }
    public void setEmbarazo(Boolean embarazo) { this.embarazo = embarazo; }
    
    public Integer getSemanaEmbarazo() { return semanaEmbarazo; }
    public void setSemanaEmbarazo(Integer semanaEmbarazo) { this.semanaEmbarazo = semanaEmbarazo; }
}
