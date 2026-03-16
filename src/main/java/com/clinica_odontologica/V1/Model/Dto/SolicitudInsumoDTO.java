package com.clinica_odontologica.V1.Model.Dto;

public class SolicitudInsumoDTO {
    private String nombreInsumo;
    private Integer cantidad;

    public SolicitudInsumoDTO() {}

    public SolicitudInsumoDTO(String nombreInsumo, Integer cantidad) {
        this.nombreInsumo = nombreInsumo;
        this.cantidad = cantidad;
    }

    // Getters y Setters
    public String getNombreInsumo() {
        return nombreInsumo;
    }

    public void setNombreInsumo(String nombreInsumo) {
        this.nombreInsumo = nombreInsumo;
    }

    public Integer getCantidad() {
        return cantidad;
    }

    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }
}