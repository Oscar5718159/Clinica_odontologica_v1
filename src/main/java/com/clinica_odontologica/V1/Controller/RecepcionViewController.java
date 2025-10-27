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

    // Página principal de recepción - MANTIENE IGUAL
    @GetMapping
    public String paginaRecepcion(Model model) {
        // Puedes cargar datos necesarios para la recepción
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
                              @RequestParam String apellido,
                              @RequestParam Integer edad,
                              @RequestParam Character sexo) {
        Persona persona = new Persona();
        persona.setNombre(nombre);
        persona.setApellido(apellido);
        persona.setEdad(edad);
        persona.setSexo(sexo);
        
        personaService.guardar(persona);
        return "redirect:/recepcion";
    }

    // ========== OPERACIONES PARA PACIENTE ==========

    // Crear paciente desde recepción - ESTE SÍ CAMBIA
    @PostMapping("/paciente/crear")
    @ResponseBody  // ← AÑADE ESTO
    public ResponseEntity<String> crearPaciente(@RequestParam String nombre,
                                               @RequestParam String apellido,
                                               @RequestParam Integer edad,
                                               @RequestParam Character sexo,
                                               @RequestParam String lugarNacimiento,
                                               @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate fechaNacimiento,
                                               @RequestParam String ocupacion,
                                               @RequestParam String direccion,
                                               @RequestParam Integer telefono,
                                               @RequestParam String estadoCivil,
                                               @RequestParam(required = false) String nacionesOriginarias,
                                               @RequestParam(required = false) String idioma) {
        
        try {
            Paciente paciente = new Paciente();
            // Campos de Persona
            paciente.setNombre(nombre);
            paciente.setApellido(apellido);
            paciente.setEdad(edad);
            paciente.setSexo(sexo);
            
            // Campos específicos de Paciente
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

    @PostMapping("/paciente/actualizar/{id}")
    public String actualizarPaciente(@PathVariable Long id,
                                    @RequestParam String nombre,
                                    @RequestParam String apellido,
                                    @RequestParam Integer edad,
                                    @RequestParam Character sexo,
                                    @RequestParam String lugarNacimiento,
                                    @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate fechaNacimiento, // AÑADE ESTA LÍNEA
                                    @RequestParam String ocupacion,
                                    @RequestParam String direccion,
                                    @RequestParam Integer telefono,
                                    @RequestParam String estadoCivil,
                                    @RequestParam(required = false) String nacionesOriginarias,
                                    @RequestParam(required = false) String idioma) {
        
        Paciente pacienteActualizado = new Paciente();
        // Campos de Persona
        pacienteActualizado.setNombre(nombre);
        pacienteActualizado.setApellido(apellido);
        pacienteActualizado.setEdad(edad);
        pacienteActualizado.setSexo(sexo);
        
        // Campos específicos de Paciente
        pacienteActualizado.setLugarNacimiento(lugarNacimiento);
        pacienteActualizado.setFechaNacimiento(fechaNacimiento); // ESTABLECER LA FECHA
        pacienteActualizado.setOcupacion(ocupacion);
        pacienteActualizado.setDireccion(direccion);
        pacienteActualizado.setTelefono(telefono);
        pacienteActualizado.setEstadoCivil(estadoCivil);
        pacienteActualizado.setNacionesOriginarias(nacionesOriginarias);
        pacienteActualizado.setIdioma(idioma);
        
        pacienteService.actualizar(id, pacienteActualizado);
        return "redirect:/recepcion";
    }

    // Eliminar paciente
    @GetMapping("/paciente/eliminar/{id}")
    public String eliminarPaciente(@PathVariable Long id) {
        pacienteService.eliminar(id);
        return "redirect:/recepcion";
    }

    // ========== BÚSQUEDAS ==========

    // Buscar personas por nombre
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

    // Buscar pacientes por nombre
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

    // Mostrar formulario de registro de paciente (página interna)
    @GetMapping("/paginas/registro_paciente")
    public String mostrarRegistroPaciente(Model model) {
        // Preparar modelo con un objeto Paciente vacío si la plantilla lo necesita
        model.addAttribute("paciente", new Paciente());
        return "recepcion/paginas/registro_paciente";
    }

}