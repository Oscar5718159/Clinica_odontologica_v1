package com.clinica_odontologica.V1.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;


/**
 * Controlador de vistas para la sección de estudiantes y revisión.
 * Solo maneja la presentación de vistas, sin lógica de negocio.
 */
@Controller
@RequestMapping("/estudiantes")
public class RevisionViewController {
    
    /**
     * Página principal de estudiantes
     */
    @GetMapping
    public String paginaEstudiantes() {
        return "estudiantes/estudiantes_pg";
    }   
    
    /**
     * Página de revisión de consultas
     */
    @GetMapping("/Revision/revision")
    public String mostrarRevision() {
        return "estudiantes/Revision/revision";
    }    
    
    /**
     * Vista del formulario de consentimiento
     * Las funciones de API (cargar docentes, buscar consultas, guardar consentimiento)
     * están en ConsentimientoController
     */
    @GetMapping("/consentimiento/consentimiento-vista")
    public String mostrarConsentimiento() {
        return "estudiantes/consentimiento/consentimiento-vista";
    }


    @GetMapping("/solicitud/insumos")
    public String mostrarInsumos() {
        return "estudiantes/solicitud/insumos";
    }
    

    @GetMapping("/seguimiento/seguimiento-vista")
    public String mostrarSeguimiento() {
        return "estudiantes/seguimiento/seguimiento-vista";
    }
    
}