package com.clinica_odontologica.V1.Controller;

import com.clinica_odontologica.V1.Model.Dto.RegistroEstudianteDTO;
import com.clinica_odontologica.V1.Model.Entity.Estudiante;
import com.clinica_odontologica.V1.Model.Entity.Persona;
import com.clinica_odontologica.V1.Model.Entity.Usuario;
import com.clinica_odontologica.V1.Service.EstudianteService;
import com.clinica_odontologica.V1.Service.PersonaService;
import com.clinica_odontologica.V1.Service.UsuarioService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/registro-integrado")
@CrossOrigin(origins = "*")
public class RegistroIntegradoController {

    @Autowired
    private PersonaService personaService;

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private EstudianteService estudianteService;

    @PostMapping("/estudiante")
    public ResponseEntity<?> registrarEstudianteCompleto(@Valid @RequestBody RegistroEstudianteDTO dto) {
        try {
            // ========== 1. VERIFICACIONES PREVIAS ==========
            if (usuarioService.existePorNombreUsuario(dto.getNombreUsuario())) {
                return ResponseEntity.badRequest().body(
                    crearRespuestaError("El nombre de usuario '" + dto.getNombreUsuario() + "' ya está registrado")
                );
            }
            
            if (estudianteService.existePorCodigoEstudiante(dto.getCodigoEstudiante())) {
                return ResponseEntity.badRequest().body(
                    crearRespuestaError("El código de estudiante " + dto.getCodigoEstudiante() + " ya está registrado")
                );
            }

            // ========== 2. CREAR Y GUARDAR PERSONA ==========
            Persona persona = new Persona();
            persona.setNombre(dto.getNombre());
            persona.setApellidoPaterno(dto.getApellidoPaterno());
            persona.setApellidoMaterno(dto.getApellidoMaterno());
            persona.setEdad(dto.getEdad());
            
            // ✅ CONVERTIR String a Character
            if (dto.getSexo() != null && !dto.getSexo().isEmpty()) {
                persona.setSexo(dto.getSexo().charAt(0));
            }
            
            Persona personaGuardada = personaService.guardar(persona);

            // ========== 3. CREAR Y GUARDAR USUARIO ==========
            Usuario usuario = new Usuario();
            usuario.setNombreUsuario(dto.getNombreUsuario());
            usuario.setContraseña(dto.getContraseña());
            usuario.setEstado(dto.getEstadoUsuario());
            usuario.setPersona(personaGuardada);
            
            Usuario usuarioGuardado = usuarioService.guardar(usuario);

            // ========== 4. CREAR Y GUARDAR ESTUDIANTE ==========
            Estudiante estudiante = new Estudiante();
            estudiante.setCodigoEstudiante(dto.getCodigoEstudiante());
            estudiante.setGestion(dto.getGestion());
            estudiante.setBloqueado(dto.getBloqueado());
            estudiante.setUsuario(usuarioGuardado);
            
            Estudiante estudianteGuardado = estudianteService.guardar(estudiante);

            // ========== 5. PREPARAR RESPUESTA ==========
            Map<String, Object> respuesta = new HashMap<>();
            respuesta.put("mensaje", "Estudiante registrado exitosamente");
            respuesta.put("id_estudiante", estudianteGuardado.getIdEstudiante());
            respuesta.put("codigo_estudiante", estudianteGuardado.getCodigoEstudiante());
            respuesta.put("nombre_completo", personaGuardada.getNombre() + " " + 
                                           personaGuardada.getApellidoPaterno() + " " + 
                                           personaGuardada.getApellidoMaterno());
            respuesta.put("nombre_usuario", usuarioGuardado.getNombreUsuario());
            respuesta.put("id_persona", personaGuardada.getId_persona());
            
            return ResponseEntity.status(HttpStatus.CREATED).body(respuesta);
            
        } catch (Exception e) {
            e.printStackTrace(); // Para ver el error completo
            return ResponseEntity.internalServerError().body(
                crearRespuestaError("Error al registrar estudiante: " + e.getMessage())
            );
        }
    }
    
    private Map<String, String> crearRespuestaError(String mensaje) {
        Map<String, String> error = new HashMap<>();
        error.put("error", mensaje);
        error.put("timestamp", String.valueOf(System.currentTimeMillis()));
        return error;
    }
    
    @GetMapping("/verificar-usuario/{nombreUsuario}")
    public ResponseEntity<Map<String, Object>> verificarUsuario(@PathVariable String nombreUsuario) {
        Boolean existe = usuarioService.existePorNombreUsuario(nombreUsuario);
        Map<String, Object> respuesta = new HashMap<>();
        respuesta.put("nombre_usuario", nombreUsuario);
        respuesta.put("disponible", !existe);
        return ResponseEntity.ok(respuesta);
    }
    
    @GetMapping("/verificar-codigo/{codigo}")
    public ResponseEntity<Map<String, Object>> verificarCodigo(@PathVariable Integer codigo) {
        Boolean existe = estudianteService.existePorCodigoEstudiante(codigo);
        Map<String, Object> respuesta = new HashMap<>();
        respuesta.put("codigo_estudiante", codigo);
        respuesta.put("disponible", !existe);
        return ResponseEntity.ok(respuesta);
    }
}