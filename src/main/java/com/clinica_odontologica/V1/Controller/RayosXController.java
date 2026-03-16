package com.clinica_odontologica.V1.Controller;

import com.clinica_odontologica.V1.Model.Entity.RayosX;
import com.clinica_odontologica.V1.Service.RayosXService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/rayosx")
@CrossOrigin(origins = "*")  // Solo para desarrollo; ajusta según tu frontend
public class RayosXController {

    @Autowired
    private RayosXService rayosXService;

    @GetMapping
    public List<RayosX> listarTodos() {
        return rayosXService.listarTodos();
    }
}