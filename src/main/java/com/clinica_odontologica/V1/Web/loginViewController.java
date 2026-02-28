package com.clinica_odontologica.V1.Web;

import com.clinica_odontologica.V1.Model.Dto.LoginRequest;
import com.clinica_odontologica.V1.Model.Dto.LoginResponse;
import com.clinica_odontologica.V1.Model.Entity.Estudiante;
import com.clinica_odontologica.V1.Service.AuthService;
import com.clinica_odontologica.V1.Service.EstudianteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import jakarta.servlet.http.HttpSession;

import java.util.Optional;

@Controller
@RequestMapping("/")
public class loginViewController {

    @Autowired
    private AuthService authService;


    @Autowired
    private HttpSession session;


    @GetMapping
    public String paginaLogin() {
        return "login/login-vista";
    }

    @PostMapping("/autenticar")
    @ResponseBody
    public ResponseEntity<LoginResponse> autenticarEstudiante(@RequestBody LoginRequest loginRequest) {
        
        // Usar el AuthService para autenticar
        Optional<Estudiante> estudianteOpt = authService.autenticar(
            loginRequest.getUsuario(), 
            loginRequest.getContraseña()
        );

        if (estudianteOpt.isPresent()) {
            Estudiante estudiante = estudianteOpt.get();
            
            // Verificar si el estudiante no está bloqueado
            if (!estudiante.getBloqueado()) {

                session.setAttribute("idEstudiante", estudiante.getIdEstudiante());
                session.setAttribute("codigoEstudiante", estudiante.getCodigoEstudiante());
                
                // También puedes guardar otros datos si los necesitas
                session.setAttribute("nombreEstudiante", estudiante.getUsuario().getPersona().getNombre());
                
                System.out.println("✅ SESIÓN GUARDADA - ID Estudiante: " + estudiante.getIdEstudiante());


                // Obtener información de la persona asociada al usuario
                String nombre = estudiante.getUsuario().getPersona().getNombre();
                String apellidoPaterno = estudiante.getUsuario().getPersona().getApellidoPaterno();
                String apellidoMaterno = estudiante.getUsuario().getPersona().getApellidoMaterno();
                String apellidos = apellidoPaterno + " " + apellidoMaterno;
                
                LoginResponse response = new LoginResponse(
                    true, 
                    "Login exitoso", 
                    nombre,
                    apellidos,
                    estudiante.getGestion(), // Asumiendo que gestión es el semestre/año
                    estudiante.getIdEstudiante() // INCLUIMOS EL ID DEL ESTUDIANTE
                );
                return ResponseEntity.ok(response);
            } else {
                LoginResponse response = new LoginResponse(false, "Estudiante bloqueado");
                return ResponseEntity.status(401).body(response);
            }
        }

        LoginResponse response = new LoginResponse(false, "Credenciales incorrectas");
        return ResponseEntity.status(401).body(response);
    }
}