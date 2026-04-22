package com.clinica_odontologica.V1.Controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.clinica_odontologica.V1.Model.Dto.DetalleConsentimientoPendienteDTO;
import com.clinica_odontologica.V1.Model.Entity.DetalleConsentimiento;
import com.clinica_odontologica.V1.Model.Entity.TratamientoRealizado;
import com.clinica_odontologica.V1.Service.DetalleConsentimientoService;
import com.clinica_odontologica.V1.Service.TratamientoRealizadoService;

@RestController
@RequestMapping("/api/detalle-consentimiento")
public class DetalleConsentimientoController {

    @Autowired
    private DetalleConsentimientoService detalleConsentimientoService;

    @Autowired
    private TratamientoRealizadoService tratamientoRealizadoService;

    @GetMapping("/pendientes")
    public ResponseEntity<List<DetalleConsentimientoPendienteDTO>> getPendientes() {
        List<DetalleConsentimiento> detalles = detalleConsentimientoService.listarPendientes();
        List<DetalleConsentimientoPendienteDTO> result = detalles.stream()
            .map(d -> {
                DetalleConsentimientoPendienteDTO dto = new DetalleConsentimientoPendienteDTO();
                dto.setIdDetalle(d.getIdDetalleConsentimiento());
                
                // Obtener nombre del paciente (ajusta según tu estructura)
                String nombrePaciente = d.getConsentimiento()
                    .getConsulta()
                    .getPaciente()
                    .getPersona()
                    .getNombre() + " " + 
                    d.getConsentimiento()
                    .getConsulta()
                    .getPaciente()
                    .getPersona()
                    .getApellidoPaterno();
                dto.setPacienteNombre(nombrePaciente);
                
                dto.setTratamientoNombre(d.getTratamiento().getNombreTratamiento());
                dto.setCantidadSolicitada(d.getCantidadTratamiento() != null ? d.getCantidadTratamiento() : 1);
                
                // Obtener TratamientoRealizado asociado
                TratamientoRealizado tr = tratamientoRealizadoService.findByDetalleConsentimiento(d)
                    .orElseThrow(() -> new RuntimeException("No se encontró tratamiento realizado"));
                
                dto.setIdTratamientoRealizado(tr.getIdTratamientoRealizado());
                dto.setCantidadActual(tr.getCantidadTratamiento() != null ? tr.getCantidadTratamiento() : 1);
                dto.setValidado(tr.getValidacion());
                return dto;
            })
            .collect(Collectors.toList());
        return ResponseEntity.ok(result);
    }
}