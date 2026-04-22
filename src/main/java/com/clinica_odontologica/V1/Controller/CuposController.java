package com.clinica_odontologica.V1.Controller;

import com.clinica_odontologica.V1.Model.Dto.TratamientoCupoDTO;
import com.clinica_odontologica.V1.Model.Entity.Cupos;
import com.clinica_odontologica.V1.Service.CupoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cupos")
@CrossOrigin(origins = "*") // ajusta según necesidades
public class CuposController {

    @Autowired
    private CupoService cupoService;

    @GetMapping("/tratamientos-por-materia/{idMateria}")
    public ResponseEntity<List<TratamientoCupoDTO>> getTratamientosByMateria(@PathVariable Long idMateria) {
        List<TratamientoCupoDTO> list = cupoService.obtenerTratamientosConCuposPorMateria(idMateria);
        return ResponseEntity.ok(list);
    }


    @GetMapping
    public ResponseEntity<List<Cupos>> listarTodosLosCupos() {
        List<Cupos> cupos = cupoService.listarTodos(); // necesitas implementar este método en el servicio
        return ResponseEntity.ok(cupos);
    }

}