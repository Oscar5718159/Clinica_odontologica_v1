package com.clinica_odontologica.V1.Controller;

import com.clinica_odontologica.V1.Model.Entity.TipoTratamiento;
import com.clinica_odontologica.V1.Service.TipoTratamientoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/tipos-tratamiento")
public class TipoTratamientoController {

    @Autowired
    private TipoTratamientoService tipoTratamientoService;

    @GetMapping
    public List<TipoTratamiento> listarTodos() {
        return tipoTratamientoService.obtenerTodos();
    }

    @GetMapping("/{id}")
    public ResponseEntity<TipoTratamiento> obtenerPorId(@PathVariable Long id) {
        return tipoTratamientoService.obtenerPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<TipoTratamiento> crear(@RequestBody TipoTratamiento tipoTratamiento) {
        TipoTratamiento nuevo = tipoTratamientoService.guardar(tipoTratamiento);
        return ResponseEntity.ok(nuevo);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TipoTratamiento> actualizar(@PathVariable Long id, @RequestBody TipoTratamiento tipoTratamiento) {
        return tipoTratamientoService.obtenerPorId(id)
                .map(tipoExistente -> {
                    tipoExistente.setNombreTipo(tipoTratamiento.getNombreTipo());
                    tipoExistente.setDescripcionTipo(tipoTratamiento.getDescripcionTipo());
                    tipoExistente.setClinica(tipoTratamiento.getClinica());
                    return ResponseEntity.ok(tipoTratamientoService.guardar(tipoExistente));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        return tipoTratamientoService.obtenerPorId(id)
                .map(tipo -> {
                    tipoTratamientoService.eliminar(id);
                    return ResponseEntity.noContent().<Void>build();
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/por-clinica/{idClinica}")
    public ResponseEntity<List<TipoTratamiento>> listarPorClinica(@PathVariable Long idClinica) {
        List<TipoTratamiento> tipos = tipoTratamientoService.obtenerPorClinica(idClinica);
        return ResponseEntity.ok(tipos);
    }



}