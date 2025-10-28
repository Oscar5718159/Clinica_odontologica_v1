package com.clinica_odontologica.V1.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/estudiantes")
public class RevisionViewController {
    
    @GetMapping
    public String paginaRevision() {
        return "estudiantes/estudiantes_pg"; // ✅ Correcto
    }   
    
    @GetMapping("/Revision/revision")
    public String mostrarRevision() {
        return "estudiantes/Revision/revision"; // ✅ Ahora coincide con tu estructura
    }    
}