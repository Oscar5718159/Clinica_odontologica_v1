package com.clinica_odontologica.V1.Service.impl;

import com.clinica_odontologica.V1.Model.Dao.*;
import com.clinica_odontologica.V1.Model.Dto.SeguimientoDTO;
import com.clinica_odontologica.V1.Model.Entity.*;
import com.clinica_odontologica.V1.Service.SeguimientoTratamientoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class SeguimientoTratamientoServiceImpl implements SeguimientoTratamientoService {

    @Autowired
    private SeguimientoTratamientoRepository seguimientoRepo;
    @Autowired
    private ConsentimientoRepository consentimientoRepo;
    @Autowired
    private RayosXRepository rayosXRepo;
    @Autowired
    private ReciboRepository reciboRepo;

    @Override
    @Transactional
    public Recibo crearSeguimiento(SeguimientoDTO dto) {
        // 1. Obtener consentimiento
        Consentimiento consentimiento = consentimientoRepo.findById(dto.getIdConsentimiento())
                .orElseThrow(() -> new RuntimeException("Consentimiento no encontrado"));

        // 2. Crear seguimiento
        SeguimientoTratamiento seguimiento = new SeguimientoTratamiento();
        seguimiento.setConsentimiento(consentimiento);
        seguimiento.setFechaHora(LocalDateTime.now());
        seguimiento.setPresionArterial(dto.getPresionArterial());
        seguimiento.setFrecuenciaCardiaca(dto.getFrecuenciaCardiaca());
        seguimiento.setFrecuenciaRespiratoria(dto.getFrecuenciaRespiratoria());
        seguimiento.setTemperatura(dto.getTemperatura());
        seguimiento.setSaturacionOxigeno(dto.getSaturacionOxigeno());
        seguimiento.setPeso(dto.getPeso());
        seguimiento.setSubjetivo(dto.getSubjetivo());
        seguimiento.setObjetivo(dto.getObjetivo());
        seguimiento.setAnalisis(dto.getAnalisis());
        seguimiento.setPlanAccion(dto.getPlanAccion());

        // 3. Procesar rayos X seleccionados
        List<DetalleSeguimiento> detalles = new ArrayList<>();
        Double sumaRayosX = 0.0;
        if (dto.getIdsRayosX() != null) {
            for (Long idRayo : dto.getIdsRayosX()) {
                RayosX rayo = rayosXRepo.findById(idRayo)
                        .orElseThrow(() -> new RuntimeException("Rayo X no encontrado: " + idRayo));
                DetalleSeguimiento detalle = new DetalleSeguimiento();
                detalle.setSeguimientoTratamiento(seguimiento);
                detalle.setRayosX(rayo);
                detalles.add(detalle);
                sumaRayosX += rayo.getPrecioRayo();
            }
        }
        seguimiento.setDetalles(detalles);  // Asegúrate de tener este campo en la entidad

        // 4. Guardar seguimiento (en cascada guarda detalles)
        seguimiento = seguimientoRepo.save(seguimiento);

        // 5. Calcular total
        Double costoBase = consentimiento.getTratamiento().getPrecioTratamiento(); // Asegúrate que Tratamiento tenga este campo
        Double total = costoBase + sumaRayosX;

        // 6. Crear recibo
        Recibo recibo = new Recibo();
        recibo.setSeguimiento(seguimiento);
        recibo.setFechaEmision(LocalDateTime.now());
        recibo.setMontoTotal(total);
        recibo.setEstadoPago("PENDIENTE");  // String, como definiste

        return reciboRepo.save(recibo);
    }

    @Override
    public List<Recibo> obtenerRecibosPendientesPorPaciente(Long idPaciente) {
        return reciboRepo.findByPacienteAndEstado(idPaciente, "PENDIENTE");
    }


    @Override
    public List<Recibo> obtenerRecibosPendientesPorCodigoEstudiante(Integer codigo) {
        return reciboRepo.findByCodigoEstudianteAndEstado(codigo, "PENDIENTE");
    }
    @Override
    @Transactional
    public Recibo actualizarEstadoPago(Long idRecibo, String nuevoEstado) {
        Recibo recibo = reciboRepo.findById(idRecibo)
                .orElseThrow(() -> new RuntimeException("Recibo no encontrado"));
        recibo.setEstadoPago(nuevoEstado);
        return reciboRepo.save(recibo);
    }
}