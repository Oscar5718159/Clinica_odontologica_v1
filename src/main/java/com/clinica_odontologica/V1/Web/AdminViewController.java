package com.clinica_odontologica.V1.Web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
public class AdminViewController {
    @GetMapping
    public String paginaAdmin() {
        return "admin/admin-panel"; 

    }   

    @GetMapping("/historial-clinico")
    public String paginaHistorialClinico() {
        return "admin/historial-clinico"; 
    }

    
    @GetMapping("/tipos-tratamiento/vista-tipo")
    public String paginaTipoTratamiento() {
        return "admin/tipos-tratamiento/vista-tipo"; 
    }

    @GetMapping("/tratamientos/formulario")
    public String paginaTratamientos() {
        return "admin/tratamientos/formulario"; 
    }
    
}