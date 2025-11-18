package com.clinica_odontologica.V1.Web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
@RequestMapping("/encargado_insumo")
public class EncargadoViewController {
    
    @GetMapping()
    public String paginaEncargadoInsumos() {
        return "encargado_insumos/encargado-vista";
    }
    
}