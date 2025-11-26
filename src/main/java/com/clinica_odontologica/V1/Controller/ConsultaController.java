package com.clinica_odontologica.V1.Controller;

import com.clinica_odontologica.V1.Model.Entity.Consulta;
import com.clinica_odontologica.V1.Model.Dto.ConsultaCompletaDTO;
import com.clinica_odontologica.V1.Service.ConsultaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/consultas")
@CrossOrigin(origins = "*") // Para permitir requests desde tu frontend
public class ConsultaController {

    @Autowired
    private ConsultaService consultaService;

    @GetMapping
    public List<Consulta> obtenerTodas() {
        return consultaService.obtenerTodos();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Consulta> obtenerPorId(@PathVariable Long id) {
        Optional<Consulta> consulta = consultaService.obtenerPorId(id);
        return consulta.map(ResponseEntity::ok)
                      .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/completa")
    public ResponseEntity<?> crearConsultaCompleta(@RequestBody ConsultaCompletaDTO consultaDTO) {
        try {
            Consulta nuevaConsulta = consultaService.guardarConsultaCompleta(consultaDTO);
            return new ResponseEntity<>(nuevaConsulta, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>("Error al guardar la consulta: " + e.getMessage(), 
                                      HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping
    public ResponseEntity<Consulta> crearConsulta(@RequestBody Consulta consulta) {
        try {
            Consulta nuevaConsulta = consultaService.guardar(consulta);
            return new ResponseEntity<>(nuevaConsulta, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> eliminarConsulta(@PathVariable Long id) {
        try {
            consultaService.eliminar(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/fecha/{fecha}")
    public List<Consulta> obtenerPorFecha(@PathVariable String fecha) {
        return consultaService.obtenerPorFecha(java.time.LocalDate.parse(fecha));
    }

    @GetMapping("/paciente/{idPaciente}")
    public List<Consulta> obtenerPorPaciente(@PathVariable Long idPaciente) {
        return consultaService.obtenerPorPaciente(idPaciente);
    }

    @GetMapping("/estudiante/{idEstudiante}")
    public List<Consulta> obtenerPorEstudiante(@PathVariable Long idEstudiante) {
        return consultaService.obtenerPorEstudiante(idEstudiante);
    }
    @GetMapping("/paciente/{idPaciente}/completo")
    public ResponseEntity<List<ConsultaCompletaDTO>> obtenerConsultasCompletasPorPaciente(@PathVariable Long idPaciente) {
        try {
            List<ConsultaCompletaDTO> consultas = consultaService.obtenerConsultasCompletasPorPaciente(idPaciente);
            System.out.println("✅ Consultas DTO encontradas: " + consultas.size());
            if (!consultas.isEmpty()) {
                System.out.println("✅ Primera consulta DTO: " + consultas.get(0));
            }
            return ResponseEntity.ok(consultas);
        } catch (Exception e) {
            System.out.println("❌ Error al obtener consultas completas: " + e.getMessage());
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}