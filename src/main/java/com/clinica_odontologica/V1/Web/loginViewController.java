package com.clinica_odontologica.V1.Web;

import com.clinica_odontologica.V1.Model.Dto.LoginRequest;
import com.clinica_odontologica.V1.Model.Dto.LoginResponse;
import com.clinica_odontologica.V1.Model.Entity.Estudiante;
import com.clinica_odontologica.V1.Service.EstudianteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
@RequestMapping("/")
public class loginViewController {

    @Autowired
    private EstudianteService estudianteService;

    @GetMapping
    public String paginaLogin() {
        return "login/login-vista";
    }

    @PostMapping("/autenticar")
    @ResponseBody
    public ResponseEntity<LoginResponse> autenticarEstudiante(@RequestBody LoginRequest loginRequest) {
        
        boolean credencialesValidas = estudianteService.validarCredenciales(
            loginRequest.getUsuario(), 
            loginRequest.getContraseña()
        );

        if (credencialesValidas) {
            // Obtener información del estudiante para la respuesta
            Optional<Estudiante> estudianteOpt = estudianteService.obtenerPorCodigo(loginRequest.getContraseña());
            
            if (estudianteOpt.isPresent()) {
                Estudiante estudiante = estudianteOpt.get();
                LoginResponse response = new LoginResponse(
                    true, 
                    "Login exitoso", 
                    estudiante.getNombre(),
                    estudiante.getApellidos(),
                    estudiante.getSemestre()
                );
                return ResponseEntity.ok(response);
            }
        }

        LoginResponse response = new LoginResponse(false, "Credenciales incorrectas");
        return ResponseEntity.status(401).body(response);
    }
}