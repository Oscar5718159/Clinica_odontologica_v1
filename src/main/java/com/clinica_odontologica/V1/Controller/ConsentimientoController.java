package com.clinica_odontologica.V1.Controller;

import com.clinica_odontologica.V1.Model.Entity.Consentimiento;
import com.clinica_odontologica.V1.Model.Entity.Consulta;
import com.clinica_odontologica.V1.Model.Entity.Docente;
import com.clinica_odontologica.V1.Model.Dto.ConsentimientoRequest;
import com.clinica_odontologica.V1.Service.ConsentimientoService;
import com.clinica_odontologica.V1.Service.ConsultaService;
import com.clinica_odontologica.V1.Service.DocenteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/consentimientos")
public class ConsentimientoController {

    @Autowired
    private ConsentimientoService consentimientoService;

    @Autowired
    private ConsultaService consultaService;

    @Autowired
    private DocenteService docenteService;

    // Vista del formulario de consentimiento
    @GetMapping("/formulario")
    public String mostrarFormularioConsentimiento(Model model) {
        try {
            List<Docente> docentes = docenteService.obtenerTodosActivos();
            model.addAttribute("docentes", docentes);
            return "consentimiento/formulario";
        } catch (Exception e) {
            model.addAttribute("error", "Error al cargar el formulario: " + e.getMessage());
            return "error";
        }
    }

    // Buscar consulta para consentimiento (API)
    @GetMapping("/buscar-consulta")
    @ResponseBody
    public ResponseEntity<?> buscarConsulta(@RequestParam String criterio) {
        try {
            if (criterio == null || criterio.trim().isEmpty()) {
                return ResponseEntity.badRequest().body("El criterio de búsqueda no puede estar vacío");
            }
            
            List<Consulta> consultas = consultaService.buscarPorCriterio(criterio.trim());
            return ResponseEntity.ok(consultas);
            
        } catch (Exception e) {
            return ResponseEntity.internalServerError()
                .body("Error al buscar consultas: " + e.getMessage());
        }
    }

    // Crear nuevo consentimiento
    @PostMapping("/crear")
    @ResponseBody
    public ResponseEntity<?> crearConsentimiento(@RequestBody ConsentimientoRequest request) {
        try {
            // Validaciones
            if (request.getIdConsulta() == null) {
                return ResponseEntity.badRequest().body("El ID de consulta es requerido");
            }
            if (request.getIdDocente() == null) {
                return ResponseEntity.badRequest().body("El ID de docente es requerido");
            }
            if (request.getExplicacion() == null || request.getExplicacion().trim().isEmpty()) {
                return ResponseEntity.badRequest().body("La explicación del procedimiento es requerida");
            }
            if (request.getDecision() == null || (!request.getDecision().equals("aceptar") && !request.getDecision().equals("rechazar"))) {
                return ResponseEntity.badRequest().body("La decisión debe ser 'aceptar' o 'rechazar'");
            }

            // Verificar si ya existe consentimiento
            if (consentimientoService.existePorConsulta(request.getIdConsulta())) {
                return ResponseEntity.badRequest()
                    .body("Ya existe un consentimiento para esta consulta");
            }

            // Obtener consulta
            Optional<Consulta> consulta = consultaService.obtenerPorId(request.getIdConsulta());
            if (consulta.isEmpty()) {
                return ResponseEntity.badRequest()
                    .body("Consulta no encontrada con ID: " + request.getIdConsulta());
            }

            // Obtener docente
            Optional<Docente> docente = docenteService.obtenerPorId(request.getIdDocente());
            if (docente.isEmpty()) {
                return ResponseEntity.badRequest()
                    .body("Docente no encontrado con ID: " + request.getIdDocente());
            }

            // Crear y guardar consentimiento
            Consentimiento consentimiento = new Consentimiento(
                consulta.get(),
                docente.get(),
                request.getExplicacion().trim(),
                request.getDecision()
            );

            Consentimiento consentimientoGuardado = consentimientoService.guardar(consentimiento);

            return ResponseEntity.ok(java.util.Map.of(
                "mensaje", "Consentimiento creado exitosamente",
                "id", consentimientoGuardado.getIdConsentimiento()
            ));
            
        } catch (Exception e) {
            return ResponseEntity.internalServerError()
                .body("Error interno al crear consentimiento: " + e.getMessage());
        }
    }

    // Obtener consentimiento por consulta
    @GetMapping("/por-consulta/{idConsulta}")
    @ResponseBody
    public ResponseEntity<?> obtenerPorConsulta(@PathVariable Long idConsulta) {
        try {
            Optional<Consentimiento> consentimiento = consentimientoService.obtenerPorConsulta(idConsulta);
            if (consentimiento.isPresent()) {
                return ResponseEntity.ok(consentimiento.get());
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error al obtener consentimiento: " + e.getMessage());
        }
    }

    // Listar todos los consentimientos (vista)
    @GetMapping("/lista")
    public String listarConsentimientos(Model model) {
        List<Consentimiento> consentimientos = consentimientoService.obtenerTodos();
        model.addAttribute("consentimientos", consentimientos);
        return "consentimiento/lista";
    }
}