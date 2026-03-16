package com.clinica_odontologica.V1.Web;

import com.clinica_odontologica.V1.Model.Dto.LoginRequest;
import com.clinica_odontologica.V1.Model.Dto.LoginResponse;
import com.clinica_odontologica.V1.Model.Entity.Estudiante;
import com.clinica_odontologica.V1.Model.Entity.Docente;
import com.clinica_odontologica.V1.Service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import jakarta.servlet.http.HttpSession;
import com.clinica_odontologica.V1.Model.Entity.Usuario;
import java.util.Optional; 
import java.util.Map; 
import java.util.HashMap; 


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
    public ResponseEntity<LoginResponse> autenticar(@RequestBody LoginRequest loginRequest) {
        
        // Usar el AuthService para autenticar
        Optional<AuthService.AuthResult> authResultOpt = authService.autenticar(
            loginRequest.getUsuario(), 
            loginRequest.getContraseña()
        );

        if (authResultOpt.isPresent()) {
            AuthService.AuthResult authResult = authResultOpt.get();
            Usuario usuario = authResult.getUsuario();
            
            // Guardar información básica en sesión
            session.setAttribute("idUsuario", usuario.getIdUsuario());
            session.setAttribute("nombreUsuario", usuario.getNombreUsuario());
            session.setAttribute("tipoUsuario", authResult.getTipo());
            
            // Obtener información de la persona
            String nombre = usuario.getPersona().getNombre();
            String apellidoPaterno = usuario.getPersona().getApellidoPaterno();
            String apellidoMaterno = usuario.getPersona().getApellidoMaterno();
            String apellidos = apellidoPaterno + " " + apellidoMaterno;
            
            // Según el tipo de usuario
            switch (authResult.getTipo()) {
                case "ESTUDIANTE":
                    Estudiante estudiante = authResult.getEstudiante();
                    if (estudiante != null && !estudiante.getBloqueado()) {
                        session.setAttribute("idEstudiante", estudiante.getIdEstudiante());
                        session.setAttribute("codigoEstudiante", estudiante.getCodigoEstudiante());
                        
                        System.out.println("✅ SESIÓN ESTUDIANTE - ID: " + estudiante.getIdEstudiante());
                        
                        LoginResponse response = new LoginResponse(
                            true, "Login exitoso", nombre, apellidos,
                            estudiante.getGestion(), usuario.getIdUsuario(), 
                            estudiante.getIdEstudiante()
                        );
                        return ResponseEntity.ok(response);
                    } else if (estudiante != null && estudiante.getBloqueado()) {
                        return ResponseEntity.status(401)
                            .body(new LoginResponse(false, "Estudiante bloqueado"));
                    }
                    break;
                    
                case "DOCENTE":
                    Docente docente = authResult.getDocente();
                    if (docente != null && docente.getEstado()) {
                        session.setAttribute("idDocente", docente.getIdDocente());
                        session.setAttribute("codigoDocente", docente.getCodigoDocente());
                        
                        System.out.println("✅ SESIÓN DOCENTE - ID: " + docente.getIdDocente());
                        
                        LoginResponse response = new LoginResponse(
                            true, "Login exitoso", nombre, apellidos,
                            usuario.getIdUsuario(), docente.getIdDocente()
                        );
                        return ResponseEntity.ok(response);
                    } else if (docente != null && !docente.getEstado()) {
                        return ResponseEntity.status(401)
                            .body(new LoginResponse(false, "Docente inactivo"));
                    }
                    break;
                    
                case "ADMIN":
                    System.out.println("✅ SESIÓN ADMIN - ID Usuario: " + usuario.getIdUsuario());
                    
                    LoginResponse response = new LoginResponse(
                        true, "Login exitoso", nombre, apellidos,
                        usuario.getIdUsuario(), null
                    );
                    response.setTipoUsuario("ADMIN");
                    return ResponseEntity.ok(response);
            }
        }

        return ResponseEntity.status(401)
            .body(new LoginResponse(false, "Credenciales incorrectas"));
    }
    
    @GetMapping("/logout")
    public String logout() {
        session.invalidate();
        return "redirect:/";
    }
    
    @GetMapping("/sesion-actual")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> getSesionActual() {
        Map<String, Object> sesionInfo = new HashMap<>();
        sesionInfo.put("idUsuario", session.getAttribute("idUsuario"));
        sesionInfo.put("tipoUsuario", session.getAttribute("tipoUsuario"));
        sesionInfo.put("idEstudiante", session.getAttribute("idEstudiante"));
        sesionInfo.put("idDocente", session.getAttribute("idDocente"));
        sesionInfo.put("autenticado", session.getAttribute("idUsuario") != null);
        
        return ResponseEntity.ok(sesionInfo);
    }
}