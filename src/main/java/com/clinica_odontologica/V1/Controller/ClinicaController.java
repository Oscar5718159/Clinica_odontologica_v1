package com.clinica_odontologica.V1.Controller;

import com.clinica_odontologica.V1.Model.Entity.Clinica;
import com.clinica_odontologica.V1.Service.ClinicaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/clinicas")
@CrossOrigin(origins = "*")
public class ClinicaController {

    @Autowired
    private ClinicaService clinicaService;

    // GET /api/clinicas - Listar todas las clínicas
    @GetMapping
    public ResponseEntity<List<Clinica>> listarTodas() {
        List<Clinica> clinicas = clinicaService.obtenerTodas();
        return ResponseEntity.ok(clinicas);
    }

    // GET /api/clinicas/{id} - Obtener una clínica por ID
    @GetMapping("/{id}")
    public ResponseEntity<Clinica> obtenerPorId(@PathVariable Long id) {
        return clinicaService.obtenerPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // GET /api/clinicas/buscar/nombre?nombre=XXX - Buscar por nombre
    @GetMapping("/buscar/nombre")
    public ResponseEntity<List<Clinica>> buscarPorNombre(@RequestParam String nombre) {
        return ResponseEntity.ok(clinicaService.buscarPorNombre(nombre));
    }

    // GET /api/clinicas/buscar/turno?turno=XXX - Buscar por turno
    @GetMapping("/buscar/turno")
    public ResponseEntity<List<Clinica>> buscarPorTurno(@RequestParam String turno) {
        return ResponseEntity.ok(clinicaService.buscarPorTurno(turno));
    }

    // POST /api/clinicas - Crear nueva clínica
    @PostMapping
    public ResponseEntity<Clinica> crear(@RequestBody Clinica clinica) {
        Clinica nuevaClinica = clinicaService.guardar(clinica);
        return ResponseEntity.ok(nuevaClinica);
    }

    // DELETE /api/clinicas/{id} - Eliminar clínica
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        clinicaService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}