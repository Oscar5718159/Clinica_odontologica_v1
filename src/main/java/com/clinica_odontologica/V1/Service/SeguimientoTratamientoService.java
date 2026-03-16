package com.clinica_odontologica.V1.Service;
import com.clinica_odontologica.V1.Model.Dto.SeguimientoDTO;
import com.clinica_odontologica.V1.Model.Entity.Recibo;
import java.util.List;

public interface SeguimientoTratamientoService {

    
    Recibo crearSeguimiento(SeguimientoDTO dto);
    List<Recibo> obtenerRecibosPendientesPorPaciente(Long idPaciente);
    Recibo actualizarEstadoPago(Long idRecibo, String nuevoEstado);
    List<Recibo> obtenerRecibosPendientesPorCodigoEstudiante(Integer codigo);
}