package com.clinica_odontologica.V1.Controller;

import com.clinica_odontologica.V1.Model.Entity.Tratamiento;
import com.clinica_odontologica.V1.Service.TratamientoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/tratamientos")
public class TratamientoController {

    @Autowired
    private TratamientoService tratamientoService;

    @GetMapping
    public List<Tratamiento> listarTodos() {
        return tratamientoService.obtenerTodos();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Tratamiento> obtenerPorId(@PathVariable Long id) {
        return tratamientoService.obtenerPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Tratamiento> crear(@RequestBody Tratamiento tratamiento) {
        Tratamiento nuevo = tratamientoService.guardar(tratamiento);
        return ResponseEntity.ok(nuevo);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Tratamiento> actualizar(@PathVariable Long id, @RequestBody Tratamiento tratamiento) {
        return tratamientoService.obtenerPorId(id)
                .map(tratamientoExistente -> {
                    tratamientoExistente.setNombreTratamiento(tratamiento.getNombreTratamiento());
                    tratamientoExistente.setDescripcionTratamiento(tratamiento.getDescripcionTratamiento());
                    tratamientoExistente.setPrecioTratamiento(tratamiento.getPrecioTratamiento());
                    tratamientoExistente.setTipoTratamiento(tratamiento.getTipoTratamiento());
                    return ResponseEntity.ok(tratamientoService.guardar(tratamientoExistente));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        return tratamientoService.obtenerPorId(id)
                .map(tratamiento -> {
                    tratamientoService.eliminar(id);
                    return ResponseEntity.noContent().<Void>build();
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/por-tipo/{idTipoTratamiento}")
    public ResponseEntity<List<Tratamiento>> listarPorTipoTratamiento(@PathVariable Long idTipoTratamiento) {
        List<Tratamiento> tratamientos = tratamientoService.obtenerPorTipoTratamiento(idTipoTratamiento);
        return ResponseEntity.ok(tratamientos);
    }
}