package com.clinica_odontologica.V1.Controller;

import com.clinica_odontologica.V1.Model.Entity.Persona;
import com.clinica_odontologica.V1.Service.PersonaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/personas")
public class PersonaController {

    @Autowired
    private PersonaService personaService;

    @GetMapping
    public List<Persona> obtenerTodos() {
        return personaService.obtenerTodos();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Persona> obtenerPorId(@PathVariable Long id) {
        Optional<Persona> persona = personaService.obtenerPorId(id);
        return persona.map(ResponseEntity::ok)
                      .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Persona> crearPersona(@RequestBody Persona persona) {
        try {
            Persona nuevaPersona = personaService.guardar(persona);
            return new ResponseEntity<>(nuevaPersona, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Persona> actualizarPersona(@PathVariable Long id, 
                                                    @RequestBody Persona persona) {
        try {
            Persona personaActualizada = personaService.actualizar(id, persona);
            return ResponseEntity.ok(personaActualizada);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> eliminarPersona(@PathVariable Long id) {
        try {
            personaService.eliminar(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/buscar/nombre")
    public List<Persona> buscarPorNombre(@RequestParam String nombre) {
        return personaService.buscarPorNombre(nombre);
    }

    @GetMapping("/buscar/apellido")
    public List<Persona> buscarPorApellido(@RequestParam String apellido) {
        return personaService.buscarPorApellido(apellido);
    }

    @GetMapping("/buscar/edad")
    public List<Persona> buscarPorEdad(@RequestParam Integer edad) {
        return personaService.buscarPorEdad(edad);
    }

    @GetMapping("/buscar/sexo")
    public List<Persona> buscarPorSexo(@RequestParam Character sexo) {
        return personaService.buscarPorSexo(sexo);
    }

    @GetMapping("/buscar/nombre-apellido")
    public List<Persona> buscarPorNombreYApellido(@RequestParam String nombre, 
                                                 @RequestParam String apellido) {
        return personaService.buscarPorNombreYApellido(nombre, apellido);
    }
}