package com.clinica_odontologica.V1.Service;

import com.clinica_odontologica.V1.Model.Entity.Periodo;
import java.util.List;

public interface PeriodoService {
    List<Periodo> listarTodos();
    Periodo obtenerPorId(Long id);
    Periodo guardar(Periodo periodo);

    List<Periodo> listarActivosPorFecha();
}