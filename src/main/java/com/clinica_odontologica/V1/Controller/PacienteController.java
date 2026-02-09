package com.clinica_odontologica.V1.Controller;

import com.clinica_odontologica.V1.Model.Entity.Paciente;
import com.clinica_odontologica.V1.Service.PacienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/pacientes")
@CrossOrigin(origins = "*") // Añade esta anotación para permitir CORS
public class PacienteController {
    
    @Autowired
    private PacienteService pacienteService;

    @GetMapping
    public List<Paciente> obtenerTodos() {
        return pacienteService.obtenerTodos();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Paciente> obtenerPorId(@PathVariable Long id) {
        Optional<Paciente> paciente = pacienteService.obtenerPorId(id);
        return paciente.map(ResponseEntity::ok)
                      .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Paciente> crearPaciente(@RequestBody Paciente paciente) {
        try {
            Paciente nuevoPaciente = pacienteService.guardar(paciente);
            return new ResponseEntity<>(nuevoPaciente, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Paciente> actualizarPaciente(@PathVariable Long id, 
                                                      @RequestBody Paciente paciente) {
        try {
            Paciente pacienteActualizado = pacienteService.actualizar(id, paciente);
            return ResponseEntity.ok(pacienteActualizado);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> eliminarPaciente(@PathVariable Long id) {
        try {
            pacienteService.eliminar(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    // ✅ NUEVO ENDPOINT PARA BÚSQUEDA POR NOMBRE COMPLETO (FRONTEND)
    @GetMapping("/buscar-por-nombre-completo")
    public List<Paciente> buscarPorNombreCompleto(@RequestParam String nombreCompleto) {
        return pacienteService.buscarPorNombreCompleto(nombreCompleto);
    }

    // Nuevo endpoint: buscar por término único (nombre completo o CI)
    @GetMapping("/buscar")
    public List<Paciente> buscarPorTermino(@RequestParam String term) {
        return pacienteService.buscarPorTermino(term);
    }
    @GetMapping("/buscar-por-ci")
    public List<Paciente> buscarPorCi(@RequestParam String ci) {
        try {
            // Intentar como número exacto
            Integer ciNumero = Integer.valueOf(ci);
            return pacienteService.buscarPorCi(ciNumero);
        } catch (NumberFormatException e) {
            // Si no es número válido, buscar como string parcial
            return pacienteService.buscarPorCiContaining(ci);
        }
    }
}