package com.clinica_odontologica.V1.Controller;

import com.clinica_odontologica.V1.Model.Entity.Persona;
import com.clinica_odontologica.V1.Model.Entity.Paciente;
import com.clinica_odontologica.V1.Service.PersonaService;
import com.clinica_odontologica.V1.Service.PacienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@Controller
@RequestMapping("/recepcion")
public class RecepcionViewController {

    @Autowired
    private PersonaService personaService;

    @Autowired
    private PacienteService pacienteService;

    // Página principal de recepción
    @GetMapping
    public String paginaRecepcion(Model model) {
        List<Persona> personas = personaService.obtenerTodos();
        List<Paciente> pacientes = pacienteService.obtenerTodos();
        
        model.addAttribute("personas", personas);
        model.addAttribute("pacientes", pacientes);
        model.addAttribute("persona", new Persona());
        model.addAttribute("paciente", new Paciente());
        
        return "recepcion/recepcion_pg";
    }

    // ========== OPERACIONES PARA PERSONA ==========

    // Crear persona desde recepción - MANTIENE IGUAL
    @PostMapping("/persona/crear")
    public String crearPersona(@RequestParam String nombre,
                              @RequestParam String apellido) {
        Persona persona = new Persona();
        persona.setNombre(nombre);
        persona.setApellido(apellido);
        
        personaService.guardar(persona);
        return "redirect:/recepcion";
    }

    // ========== OPERACIONES PARA PACIENTE ==========

    // Crear paciente desde recepción - ACTUALIZADO
    @PostMapping("/paciente/crear")
    @ResponseBody
    public ResponseEntity<String> crearPaciente(@RequestParam String nombre,
                                               @RequestParam String apellido,
                                               @RequestParam Integer edad, 
                                               @RequestParam Character sexo,
                                               @RequestParam String historialClinico,
                                               @RequestParam String lugarNacimiento,
                                               @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate fechaNacimiento,
                                               @RequestParam String ocupacion,
                                               @RequestParam String direccion,
                                               @RequestParam String telefono,
                                               @RequestParam String estadoCivil,
                                               @RequestParam(required = false) String nacionesOriginarias,
                                               @RequestParam(required = false) String idioma) {
        
        try {
            // 1. Crear o buscar la Persona
            Persona persona = new Persona();
            persona.setNombre(nombre);
            persona.setApellido(apellido);
            persona.setEdad(edad);       
            persona.setSexo(sexo);
            persona = personaService.guardar(persona); // Guardar para obtener ID
            
            // 2. Crear el Paciente
            Paciente paciente = new Paciente();
            paciente.setHistorialClinico(historialClinico);
            paciente.setPersona(persona);
            paciente.setLugarNacimiento(lugarNacimiento);
            paciente.setFechaNacimiento(fechaNacimiento);
            paciente.setOcupacion(ocupacion);
            paciente.setDireccion(direccion);
            paciente.setTelefono(telefono);
            paciente.setEstadoCivil(estadoCivil);
            paciente.setNacionesOriginarias(nacionesOriginarias);
            paciente.setIdioma(idioma);
            
            pacienteService.guardar(paciente);
            
            return ResponseEntity.ok("¡Paciente registrado exitosamente!");
            
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                               .body("Error al crear paciente: " + e.getMessage());
        }
    }

    // Actualizar paciente - ACTUALIZADO
    @PostMapping("/paciente/actualizar/{id}")
    public String actualizarPaciente(@PathVariable Long id,
                                    @RequestParam String nombre,
                                    @RequestParam String apellido,
                                    @RequestParam String historialClinico,
                                    @RequestParam String lugarNacimiento,
                                    @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate fechaNacimiento,
                                    @RequestParam String ocupacion,
                                    @RequestParam String direccion,
                                    @RequestParam String telefono,
                                    @RequestParam String estadoCivil,
                                    @RequestParam(required = false) String nacionesOriginarias,
                                    @RequestParam(required = false) String idioma) {
        
        try {
            // Obtener el paciente existente
            Paciente pacienteExistente = pacienteService.obtenerPorId(id)
                    .orElseThrow(() -> new RuntimeException("Paciente no encontrado"));
            
            // Actualizar datos de Persona
            Persona persona = pacienteExistente.getPersona();
            persona.setNombre(nombre);
            persona.setApellido(apellido);
    
            personaService.guardar(persona); // Actualizar persona
            
            // Actualizar datos específicos de Paciente
            pacienteExistente.setHistorialClinico(historialClinico);
            pacienteExistente.setLugarNacimiento(lugarNacimiento);
            pacienteExistente.setFechaNacimiento(fechaNacimiento);
            pacienteExistente.setOcupacion(ocupacion);
            pacienteExistente.setDireccion(direccion);
            pacienteExistente.setTelefono(telefono);
            pacienteExistente.setEstadoCivil(estadoCivil);
            pacienteExistente.setNacionesOriginarias(nacionesOriginarias);
            pacienteExistente.setIdioma(idioma);
            
            pacienteService.guardar(pacienteExistente);
            
        } catch (Exception e) {
            e.printStackTrace();
            // Puedes manejar el error de manera más específica aquí
        }
        
        return "redirect:/recepcion";
    }

    // Eliminar paciente - MANTIENE IGUAL
    @GetMapping("/paciente/eliminar/{id}")
    public String eliminarPaciente(@PathVariable Long id) {
        pacienteService.eliminar(id);
        return "redirect:/recepcion";
    }

    // ========== BÚSQUEDAS ==========

    // Buscar personas por nombre - MANTIENE IGUAL
    @GetMapping("/buscar/personas")
    public String buscarPersonas(@RequestParam String nombre, Model model) {
        List<Persona> personas = personaService.buscarPorNombre(nombre);
        List<Paciente> pacientes = pacienteService.obtenerTodos();
        
        model.addAttribute("personas", personas);
        model.addAttribute("pacientes", pacientes);
        model.addAttribute("persona", new Persona());
        model.addAttribute("paciente", new Paciente());
        
        return "recepcion/recepcion_pg";
    }

    // Buscar pacientes por nombre - ACTUALIZADO
    @GetMapping("/buscar/pacientes")
    public String buscarPacientes(@RequestParam String nombre, Model model) {
        List<Persona> personas = personaService.obtenerTodos();
        List<Paciente> pacientes = pacienteService.buscarPorNombre(nombre);
        
        model.addAttribute("personas", personas);
        model.addAttribute("pacientes", pacientes);
        model.addAttribute("persona", new Persona());
        model.addAttribute("paciente", new Paciente());
        
        return "recepcion/recepcion_pg";
    }

    // Servir la página de registro de paciente dentro del iframe
    @GetMapping("/paginas/registro_paciente")
    public String mostrarRegistroPaciente() {
        return "recepcion/paginas/registro_paciente";
    }

}