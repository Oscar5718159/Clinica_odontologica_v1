package com.clinica_odontologica.V1.Web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/doctor")
public class DoctorViewController {

    @GetMapping
    public String paginaDoctor() {
        return "doctor/doctor"; // ✅ Correcto
    }   
    
    @GetMapping("/historial_clinico/historial_clinico_ENPROCESO")
    public String mostrarDoctor() {
        return "doctor/historial_clinico/historial_clinico_ENPROCESO"; 
    }    

    // Redirige a la URL correcta que sí tiene los datos
    @GetMapping("/estado_consentimiento/estadoConsentimiento")
    public String mostrarConsentimientoEstado() {
        return "redirect:/consentimientos/lista"; 
    }   

    
}
