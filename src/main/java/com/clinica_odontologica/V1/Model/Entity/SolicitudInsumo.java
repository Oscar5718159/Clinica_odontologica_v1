package com.clinica_odontologica.V1.Model.Entity;
import jakarta.persistence.*;
import lombok.*;
@Getter
@Setter


@Entity@Table(name = "solicitud_insumo")
public class SolicitudInsumo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_solicitud_insumo")
    private Long idSolicitudInsumo;

    @OneToOne
    @JoinColumn(name = "consentimiento", nullable = false)
    private Consentimiento consentimiento;

    @Column(name = "nombre_insumo", nullable = false, length = 100)
    private String nombreInsumo;

    @Column(name = "cantidad", nullable = false)
    private Integer cantidad;
    

}
