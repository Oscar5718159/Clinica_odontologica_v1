package com.clinica_odontologica.V1.Web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/archivos")
public class ArchivosViewController {
    @GetMapping
    public String paginagestion() {
        return "archivos_encargado/dasboard-archivo"; 
    }   
    
    @GetMapping("/gestion/gestion-vista")
    public String mostrarGestion() {
        return "archivos_encargado/gestion/gestion-vista"; 
    }    

    @GetMapping("/prestamo/prestamo-vista")
    public String mostrarRegistro() {
        return "archivos_encargado/prestamo/prestamo-vista"; 
    }


    
}
