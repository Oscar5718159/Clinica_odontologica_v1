package com.clinica_odontologica.V1.Model.Entity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
@Setter
@Getter
@Entity
@Table(name="detalle_consentimiento")


public class DetalleConsentimiento {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JoinColumn(name = "id_detalle_consentimiento")
    private Long idDetalleConsentimiento;


    @ManyToOne
    @JoinColumn(name="consentimiento_id")
    private Consentimiento consentimiento;


    @ManyToOne
    @JoinColumn(name="tratamiento_id")
    private Tratamiento tratamiento;   
    
    @Column(name="cantidad_tratamiento")
    private Integer cantidadTratamiento;

    @Column(name="estado_detalle")
    private Boolean estadoDetalle;

}
