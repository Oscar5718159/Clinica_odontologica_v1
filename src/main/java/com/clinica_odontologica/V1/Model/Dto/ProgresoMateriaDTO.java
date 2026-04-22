// ProgresoMateriaDTO.java
package com.clinica_odontologica.V1.Model.Dto;

import java.util.List;
import lombok.*;

@Getter
@Setter
public class ProgresoMateriaDTO {
    private Long idMateria;
    private String nombreMateria;
    private List<ProgresoTratamientoDTO> tratamientos;

 
}