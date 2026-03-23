package com.clinica_odontologica.V1.Controller;

import com.clinica_odontologica.V1.Model.Entity.OdontogramaFoto;
import com.clinica_odontologica.V1.Model.Dao.OdontogramaFotoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/odontograma-fotos")
@CrossOrigin(origins = "*")
public class OdontogramaFotoController {

    @Autowired
    private OdontogramaFotoRepository odontogramaFotoRepository;

    @GetMapping("/consulta/{idConsulta}")
    public ResponseEntity<List<OdontogramaFoto>> getFotosByConsulta(@PathVariable Long idConsulta) {
        List<OdontogramaFoto> fotos = odontogramaFotoRepository.findByConsultaIdConsulta(idConsulta);
        return ResponseEntity.ok(fotos);
    }
}