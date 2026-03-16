package com.clinica_odontologica.V1.Controller;

import com.clinica_odontologica.V1.Model.Dto.ReciboDTO;
import com.clinica_odontologica.V1.Model.Dto.SeguimientoDTO;
import com.clinica_odontologica.V1.Model.Entity.Recibo;
import com.clinica_odontologica.V1.Service.SeguimientoTratamientoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/seguimientos")
public class SeguimientoTratamientoController {

    @Autowired
    private SeguimientoTratamientoService seguimientoService;
    

    @PostMapping("/crear")
    public ResponseEntity<ReciboDTO> crearSeguimiento(@RequestBody SeguimientoDTO dto) {
        Recibo recibo = seguimientoService.crearSeguimiento(dto);
        return ResponseEntity.ok(convertirAResponseDTO(recibo));
    }

    @GetMapping("/recibos/pendientes/{idPaciente}")
    public ResponseEntity<List<ReciboDTO>> recibosPendientes(@PathVariable Long idPaciente) {
        List<Recibo> recibos = seguimientoService.obtenerRecibosPendientesPorPaciente(idPaciente);
        List<ReciboDTO> response = recibos.stream()
                .map(this::convertirAResponseDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(response);
    }

    @PutMapping("/recibos/{idRecibo}/pagar")
    public ResponseEntity<ReciboDTO> pagarRecibo(@PathVariable Long idRecibo) {
        Recibo recibo = seguimientoService.actualizarEstadoPago(idRecibo, "PAGADO");
        return ResponseEntity.ok(convertirAResponseDTO(recibo));
    }


    @GetMapping("/recibos/pendientes/estudiante/codigo/{codigo}")
    public ResponseEntity<List<ReciboDTO>> recibosPendientesPorCodigoEstudiante(@PathVariable Integer codigo) {
        List<Recibo> recibos = seguimientoService.obtenerRecibosPendientesPorCodigoEstudiante(codigo);
        List<ReciboDTO> response = recibos.stream().map(this::convertirAResponseDTO).collect(Collectors.toList());
        return ResponseEntity.ok(response);
    }

    private ReciboDTO convertirAResponseDTO(Recibo recibo) {
        ReciboDTO dto = new ReciboDTO();
        dto.setIdRecibo(recibo.getIdRecibo());
        dto.setIdSeguimiento(recibo.getSeguimiento().getIdSeguimientoTratamiento());
        dto.setIdConsentimiento(recibo.getSeguimiento().getConsentimiento().getIdConsentimiento());
        dto.setFechaEmision(recibo.getFechaEmision());
        dto.setMontoTotal(recibo.getMontoTotal());
        dto.setEstadoPago(recibo.getEstadoPago());
        return dto;
    }
}