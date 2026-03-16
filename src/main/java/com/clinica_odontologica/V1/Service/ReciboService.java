package com.clinica_odontologica.V1.Service;

import com.clinica_odontologica.V1.Model.Entity.Recibo;
import java.util.List;
public interface ReciboService {

    List<Recibo> obtenerRecibosPendientesPorCodigoEstudiante(Integer codigo);
}
