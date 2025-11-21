package com.clinica_odontologica.V1.Controller;

import com.clinica_odontologica.V1.Model.Entity.Consentimiento;
import com.clinica_odontologica.V1.Model.Entity.Consulta;
import com.clinica_odontologica.V1.Model.Entity.Docente;
import com.clinica_odontologica.V1.Model.Dto.ConsentimientoRequest;
import com.clinica_odontologica.V1.Model.Dto.DocenteDTO;
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
import java.util.stream.Collectors;

@Controller
@RequestMapping("/consentimientos")
public class ConsentimientoController {

    @Autowired
    private ConsentimientoService consentimientoService;

    @Autowired
    private ConsultaService consultaService;

    @Autowired
    private DocenteService docenteService;


    @GetMapping("/obtener-docentes")
    @ResponseBody
    public ResponseEntity<?> obtenerDocentes() {
        try {
            List<Docente> docentes = docenteService.obtenerTodosActivos();
            
            System.out.println("🎯 Usando obtenerTodosActivos()");
            System.out.println("📊 Docentes activos encontrados: " + docentes.size());
            
            // Convertir a DTO para evitar relaciones circulares
            List<DocenteDTO> docentesDTO = docentes.stream()
                .map(d -> new DocenteDTO(
                    d.getIdDocente(), 
                    d.getNombreDocente(), 
                    d.getEspecialidad(),
                    d.getEstado()
                ))
                .collect(Collectors.toList());
            
            // DEBUG
            for (DocenteDTO d : docentesDTO) {
                System.out.println("✅ DTO - ID: " + d.getIdDocente() + ", Nombre: " + d.getNombreDocente());
            }
            
            return ResponseEntity.ok(docentesDTO);
        } catch (Exception e) {
            System.err.println("❌ Error: " + e.getMessage());
            e.printStackTrace();
            return ResponseEntity.internalServerError()
                .body("Error al obtener docentes: " + e.getMessage());
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
        return "doctor/estado_consentimiento/estadoConsentimiento";
    }


    // Mostrar formulario de edición
    @GetMapping("/editar/{id}")
    public String mostrarFormularioEdicion(@PathVariable Long id, Model model) {
        try {
            Optional<Consentimiento> consentimiento = consentimientoService.obtenerPorId(id);
            
            if (consentimiento.isPresent()) {
                model.addAttribute("consentimiento", consentimiento.get());
                
                // Para cargar docentes en el formulario de edición
                List<Docente> docentes = docenteService.obtenerTodosActivos();
                List<DocenteDTO> docentesDTO = docentes.stream()
                    .map(d -> new DocenteDTO(d.getIdDocente(), d.getNombreDocente(), d.getEspecialidad(), d.getEstado()))
                    .collect(Collectors.toList());
                model.addAttribute("docentes", docentesDTO);
                
                return "doctor/estado_consentimiento/editarConsentimiento";
            } else {
                return "redirect:/consentimientos/lista?error=Consentimiento no encontrado";
            }
        } catch (Exception e) {
            return "redirect:/consentimientos/lista?error=Error al cargar consentimiento";
        }
    }

    // Procesar la edición
    @PostMapping("/editar/{id}")
    public String procesarEdicion(@PathVariable Long id, 
                                @RequestParam String explicacion,
                                @RequestParam String decision,
                                @RequestParam Long idDocente,
                                @RequestParam(defaultValue = "true") Boolean estado) {
        try {
            Optional<Consentimiento> consentimientoOpt = consentimientoService.obtenerPorId(id);
            
            if (consentimientoOpt.isPresent()) {
                Consentimiento consentimiento = consentimientoOpt.get();
                
                // Actualizar campos
                consentimiento.setExplicacion(explicacion);
                consentimiento.setDecision(decision);
                consentimiento.setEstado(estado);
                
                // Actualizar docente si es diferente
                if (!consentimiento.getDocente().getIdDocente().equals(idDocente)) {
                    Optional<Docente> nuevoDocente = docenteService.obtenerPorId(idDocente);
                    nuevoDocente.ifPresent(consentimiento::setDocente);
                }
                
                consentimientoService.guardar(consentimiento);
                
                return "redirect:/consentimientos/lista?success=Consentimiento actualizado correctamente";
            } else {
                return "redirect:/consentimientos/lista?error=Consentimiento no encontrado";
            }
        } catch (Exception e) {
            return "redirect:/consentimientos/lista?error=Error al actualizar consentimiento";
        }
    }


}