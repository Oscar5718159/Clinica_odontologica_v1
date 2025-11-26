package com.clinica_odontologica.V1.Web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
public class AdminViewController {
    @GetMapping
    public String paginaAdmin() {
        return "admin/admin-panel"; // ✅ Correcto
    }   
}