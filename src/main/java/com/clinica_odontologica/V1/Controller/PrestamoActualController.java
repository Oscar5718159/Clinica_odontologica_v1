package com.clinica_odontologica.V1.Controller;

import com.clinica_odontologica.V1.Model.Entity.PrestamoActual;
import com.clinica_odontologica.V1.Service.PrestamoActualService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/prestamos-actuales")
@CrossOrigin(origins = "*")
public class PrestamoActualController {

    @Autowired
    private PrestamoActualService prestamoActualService;

    // 1. Obtener todos los préstamos
    @GetMapping
    public List<PrestamoActual> obtenerTodos() {
        return prestamoActualService.obtenerTodos();
    }

    // 2. Obtener préstamo por ID
    @GetMapping("/{id}")
    public ResponseEntity<PrestamoActual> obtenerPorId(@PathVariable Long id) {
        Optional<PrestamoActual> prestamo = prestamoActualService.obtenerPorId(id);
        return prestamo.map(ResponseEntity::ok)
                      .orElse(ResponseEntity.notFound().build());
    }

        // 3. Crear nuevo préstamo
    @PostMapping
    public ResponseEntity<PrestamoActual> crearPrestamo(@RequestBody PrestamoActual prestamoActual) {
        try {
            // ✅ ELIMINA ESTA VALIDACIÓN - USA LA FECHA QUE VIENE DEL FRONTEND
            // Simplemente guarda lo que recibes
            PrestamoActual nuevoPrestamo = prestamoActualService.registrarPrestamo(prestamoActual);
            return new ResponseEntity<>(nuevoPrestamo, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // 4. Actualizar préstamo existente
    @PutMapping("/{id}")
    public ResponseEntity<PrestamoActual> actualizarPrestamo(@PathVariable Long id, 
                                                           @RequestBody PrestamoActual prestamoActual) {
        try {
            PrestamoActual prestamoActualizado = prestamoActualService.actualizar(id, prestamoActual);
            return ResponseEntity.ok(prestamoActualizado);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // 5. Eliminar préstamo
    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> eliminarPrestamo(@PathVariable Long id) {
        try {
            prestamoActualService.eliminar(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // 6. Buscar préstamos por ID de estudiante
    @GetMapping("/estudiante/{idEstudiante}")
    public ResponseEntity<List<PrestamoActual>> buscarPorIdEstudiante(@PathVariable Long idEstudiante) {
        try {
            List<PrestamoActual> prestamos = prestamoActualService.buscarPorIdEstudiante(idEstudiante);
            if (prestamos.isEmpty()) {
                return ResponseEntity.noContent().build();
            }
            return ResponseEntity.ok(prestamos);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // 7. Buscar préstamos por ID de archivo
    @GetMapping("/archivo/{idArchivo}")
    public ResponseEntity<List<PrestamoActual>> buscarPorIdArchivo(@PathVariable Long idArchivo) {
        try {
            List<PrestamoActual> prestamos = prestamoActualService.buscarPorIdArchivo(idArchivo);
            if (prestamos.isEmpty()) {
                return ResponseEntity.noContent().build();
            }
            return ResponseEntity.ok(prestamos);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // // 9. Buscar préstamos por encargado
    // @GetMapping("/buscar/encargado")
    // public ResponseEntity<List<PrestamoActual>> buscarPorEncargadoPrestamo(@RequestParam String encargado) {
    //     try {
    //         List<PrestamoActual> prestamos = prestamoActualService.buscarPorEncargadoPrestamo(encargado);
    //         if (prestamos.isEmpty()) {
    //             return ResponseEntity.noContent().build();
    //         }
    //         return ResponseEntity.ok(prestamos);
    //     } catch (Exception e) {
    //         return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    //     }
    // }


    
    // // 13. Endpoint para registrar devolución de préstamo
    // @PutMapping("/{id}/devolver")
    // public ResponseEntity<PrestamoActual> registrarDevolucion(@PathVariable Long id) {
    //     try {
    //         Optional<PrestamoActual> prestamoOpt = prestamoActualService.obtenerPorId(id);
            
    //         if (prestamoOpt.isEmpty()) {
    //             return ResponseEntity.notFound().build();
    //         }
            
    //         PrestamoActual prestamo = prestamoOpt.get();
    //         prestamo.setFechaDevolucion(new Date()); // Establecer fecha de devolución actual
            
    //         PrestamoActual actualizado = prestamoActualService.guardar(prestamo);
    //         return ResponseEntity.ok(actualizado);
            
    //     } catch (Exception e) {
    //         return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    //     }
    // }


// En PrestamoActualController.java

    // Endpoint para registrar devolución
    @PutMapping("/{id}/devolver")
    public ResponseEntity<?> registrarDevolucion(@PathVariable Long id) {
        try {
            PrestamoActual actualizado = prestamoActualService.devolverArchivo(id, LocalDate.now());
            return ResponseEntity.ok(actualizado);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // Endpoint para desbloquear estudiante
    @PostMapping("/desbloquear/{idEstudiante}")
    public ResponseEntity<?> desbloquearEstudiante(@PathVariable Long idEstudiante) {
        try {
            prestamoActualService.desbloquearEstudiante(idEstudiante);
            return ResponseEntity.ok().build();
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}