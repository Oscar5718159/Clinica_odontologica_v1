package com.clinica_odontologica.V1.Controller;

import com.clinica_odontologica.V1.Model.Entity.TratamientoRealizado;
import com.clinica_odontologica.V1.Service.TratamientoRealizadoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/tratamientos-realizados")
public class TratamientoRealizadoController {

    @Autowired
    private TratamientoRealizadoService tratamientoRealizadoService;

    @GetMapping
    public ResponseEntity<List<TratamientoRealizado>> listar() {
        return ResponseEntity.ok(tratamientoRealizadoService.listarTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<TratamientoRealizado> obtener(@PathVariable Long id) {
        Optional<TratamientoRealizado> tr = tratamientoRealizadoService.obtenerPorId(id);
        return tr.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<TratamientoRealizado> crear(@RequestBody TratamientoRealizado tratamientoRealizado) {
        return ResponseEntity.ok(tratamientoRealizadoService.guardar(tratamientoRealizado));
    }

    @PutMapping("/{id}")
    public ResponseEntity<TratamientoRealizado> actualizar(@PathVariable Long id, @RequestBody TratamientoRealizado tratamientoRealizado) {
        if (!tratamientoRealizadoService.obtenerPorId(id).isPresent()) {
            return ResponseEntity.notFound().build();
        }
        tratamientoRealizado.setIdTratamientoRealizado(id);
        return ResponseEntity.ok(tratamientoRealizadoService.guardar(tratamientoRealizado));
    }

    

    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminar(@PathVariable Long id) {
        if (!tratamientoRealizadoService.obtenerPorId(id).isPresent()) {
            return ResponseEntity.notFound().build();
        }
        tratamientoRealizadoService.eliminar(id);
        return ResponseEntity.ok(Map.of("mensaje", "Tratamiento realizado eliminado"));
    }

    @PatchMapping("/{id}/validar")
    public ResponseEntity<?> validar(@PathVariable Long id,
                                    @RequestParam Long docenteId,
                                    @RequestParam Integer cantidad) {
        try {
            tratamientoRealizadoService.validarTratamiento(id, docenteId, cantidad);
            return ResponseEntity.ok(Map.of("mensaje", "Tratamiento validado correctamente"));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }
}