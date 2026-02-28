package com.clinica_odontologica.V1.Controller;

import com.clinica_odontologica.V1.Model.Entity.Estudiante;
import com.clinica_odontologica.V1.Model.Dao.EstudianteRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class BloqueadoController {

    @Autowired
    private EstudianteRepository estudianteRepository;
    
    @GetMapping("/bloqueado")
    public String paginaBloqueado(HttpSession session, Model model) {
        Long idEstudiante = (Long) session.getAttribute("idEstudiante");
        
        if (idEstudiante != null) {
            Estudiante estudiante = estudianteRepository.findById(idEstudiante).orElse(null);
            if (estudiante != null) {
                model.addAttribute("nombreEstudiante", 
                    estudiante.getUsuario().getPersona().getNombre() + " " +
                    estudiante.getUsuario().getPersona().getApellidoPaterno());
            }
        }
        
        return "bloqueado";  // Crear esta vista
    }
}