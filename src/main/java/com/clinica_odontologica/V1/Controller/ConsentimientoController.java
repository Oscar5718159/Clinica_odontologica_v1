package com.clinica_odontologica.V1.Controller;


import com.clinica_odontologica.V1.Service.EstudianteService;
import com.clinica_odontologica.V1.Model.Entity.*;
import com.clinica_odontologica.V1.Service.*;
import com.clinica_odontologica.V1.Model.Dto.ConsentimientoDTO;
import com.clinica_odontologica.V1.Model.Dto.ConsentimientoDetalleDTO;
import com.clinica_odontologica.V1.Model.Dto.DocenteDTO;
import com.clinica_odontologica.V1.Service.ConsentimientoService;
import com.clinica_odontologica.V1.Service.ConsultaService;
import com.clinica_odontologica.V1.Service.DocenteService;


import com.clinica_odontologica.V1.Service.SolicitudInsumoService;
import com.clinica_odontologica.V1.Service.TratamientoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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

    @Autowired
    private TratamientoService tratamientoService;
    @Autowired
    private SolicitudInsumoService solicitudInsumoService;

    @Autowired
    private EstudianteService estudianteService;

    @Autowired
    private InscripcionMateriaService inscripcionMateriaService;

    @Autowired
    private DetalleConsentimientoService detalleConsentimientoService;

    @Autowired
    private TratamientoRealizadoService tratamientoRealizadoService;



    @GetMapping("/obtener-docentes")
    @ResponseBody
    public ResponseEntity<?> obtenerDocentes() {
        try {
            List<Docente> docentes = docenteService.obtenerTodosActivos();
            
            System.out.println("🎯 Usando obtenerTodosActivos()");
            System.out.println("📊 Docentes activos encontrados: " + docentes.size());
            
            // Convertir a DTO obteniendo el nombre desde Usuario -> Persona
            List<DocenteDTO> docentesDTO = docentes.stream()
                .map(d -> {
                    String nombreCompleto = "Nombre no disponible";
                    if (d.getUsuario() != null && d.getUsuario().getPersona() != null) {
                        Persona persona = d.getUsuario().getPersona();
                        nombreCompleto = (persona.getNombre() + " " + 
                                         persona.getApellidoPaterno() + " " + 
                                         persona.getApellidoMaterno()).trim();
                    }
                    return new DocenteDTO(
                        d.getIdDocente(), 
                        nombreCompleto, 
                        d.getEspecialidad(),
                        d.getEstado()
                    );
                })
                .collect(Collectors.toList());
            
            // DEBUG
            for (DocenteDTO d : docentesDTO) {
                System.out.println("✅ DTO - ID: " + d.getIdDocente() + ", Nombre: " + d.getNombreCompleto());
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
            e.printStackTrace();
            return ResponseEntity.internalServerError()
                .body("Error al buscar consultas: " + e.getMessage());
        }
    }

    @PostMapping("/crear")
    @ResponseBody
    public ResponseEntity<?> crearConsentimiento(@RequestBody ConsentimientoDTO dto) {
        try {
            // Validaciones básicas
            if (dto.getIdConsulta() == null) return badRequest("idConsulta requerido");
            if (dto.getIdDocente() == null) return badRequest("idDocente requerido");
            if (dto.getIdTratamiento() == null) return badRequest("idTratamiento requerido");
            if (dto.getIdEstudiante() == null) return badRequest("idEstudiante requerido");
            if (dto.getIdMateria() == null) return badRequest("idMateria requerido"); // NUEVA VALIDACIÓN
            if (dto.getExplicacion() == null || dto.getExplicacion().trim().isEmpty())
                return badRequest("Explicación requerida");
            if (dto.getDecision() == null || (!dto.getDecision().equals("aceptar") && !dto.getDecision().equals("rechazar")))
                return badRequest("Decisión inválida");

            // Obtener entidades relacionadas
            Consulta consulta = consultaService.obtenerPorId(dto.getIdConsulta())
                    .orElseThrow(() -> new RuntimeException("Consulta no encontrada"));
            Docente docente = docenteService.obtenerPorId(dto.getIdDocente())
                    .orElseThrow(() -> new RuntimeException("Docente no encontrado"));
            Tratamiento tratamiento = tratamientoService.obtenerPorId(dto.getIdTratamiento())
                    .orElseThrow(() -> new RuntimeException("Tratamiento no encontrado"));
            Estudiante estudiante = estudianteService.obtenerPorId(dto.getIdEstudiante())
                    .orElseThrow(() -> new RuntimeException("Estudiante no encontrado"));

            // ========== NUEVO: OBTENER INSCRIPCIÓN ACTIVA ==========
            InscripcionMateria inscripcion = inscripcionMateriaService
                    .obtenerInscripcionActivaPorEstudianteYMateria(dto.getIdEstudiante(), dto.getIdMateria())
                    .orElseThrow(() -> new RuntimeException("El estudiante no está inscrito activamente en esta materia"));

            // Crear Consentimiento
            Consentimiento consentimiento = new Consentimiento();
            consentimiento.setConsulta(consulta);
            consentimiento.setDocente(docente);
            consentimiento.setTratamiento(tratamiento);
            consentimiento.setEstudiante(estudiante);
            consentimiento.setExplicacion(dto.getExplicacion());
            consentimiento.setDecision(dto.getDecision());
            consentimiento.setFecha(LocalDateTime.now());
            consentimiento.setEstado(true);

            Consentimiento guardado = consentimientoService.guardar(consentimiento);

            // ========== CREAR DETALLE CONSENTIMIENTO ==========
            DetalleConsentimiento detalle = new DetalleConsentimiento();
            detalle.setConsentimiento(guardado);
            detalle.setTratamiento(tratamiento);
            detalle.setCantidadTratamiento(1);   // valor inicial (luego el docente lo cambiará al validar)
            detalle.setEstadoDetalle(true);      // pendiente de validación
            detalle = detalleConsentimientoService.guardar(detalle);

            // ========== CREAR TRATAMIENTO REALIZADO (PENDIENTE) ==========
            TratamientoRealizado realizado = new TratamientoRealizado();
            realizado.setInscripcionMateria(inscripcion);
            realizado.setDetalleConsentimiento(detalle);
            realizado.setTratamiento(tratamiento);
            realizado.setDocente(docente);
            realizado.setFechaRealizacion(LocalDate.now());
            realizado.setValidacion(false);      // aún no validado por el docente
            realizado.setCantidadTratamiento(1); // temporal
            tratamientoRealizadoService.guardar(realizado);

            // Guardar insumos (si existen)
            if (dto.getInsumos() != null && !dto.getInsumos().isEmpty()) {
                List<SolicitudInsumo> insumos = dto.getInsumos().stream()
                    .map(i -> {
                        SolicitudInsumo si = new SolicitudInsumo();
                        si.setConsentimiento(guardado);
                        si.setNombreInsumo(i.getNombreInsumo());
                        si.setCantidad(i.getCantidad());
                        return si;
                    })
                    .collect(Collectors.toList());
                solicitudInsumoService.guardarTodos(insumos);
            }

            Map<String, Object> respuesta = new HashMap<>();
            respuesta.put("mensaje", "Consentimiento guardado correctamente");
            respuesta.put("id", guardado.getIdConsentimiento());
            return ResponseEntity.ok(respuesta);

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError()
                .body(Map.of("error", e.getMessage()));
        }
    }

    private ResponseEntity<?> badRequest(String mensaje) {
        return ResponseEntity.badRequest().body(Map.of("error", mensaje));
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
                    .map(d -> {
                        String nombreCompleto = "Nombre no disponible";
                        if (d.getUsuario() != null && d.getUsuario().getPersona() != null) {
                            Persona persona = d.getUsuario().getPersona();
                            nombreCompleto = (persona.getNombre() + " " + 
                                             persona.getApellidoPaterno() + " " + 
                                             persona.getApellidoMaterno()).trim();
                        }
                        return new DocenteDTO(d.getIdDocente(), nombreCompleto, d.getEspecialidad(), d.getEstado());
                    })
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

    // @GetMapping("/obtener/{id}")
    // @ResponseBody
    // public ResponseEntity<?> obtenerConsentimiento(@PathVariable Long id) {
    //     try {
    //         Optional<Consentimiento> consentimiento = consentimientoService.obtenerPorId(id);
    //         if (consentimiento.isPresent()) {
    //             Consentimiento c = consentimiento.get();
                
    //             Map<String, Object> datos = new HashMap<>();
    //             datos.put("id", c.getIdConsentimiento());
    //             datos.put("decision", c.getDecision());
    //             datos.put("explicacion", c.getExplicacion());
    //             datos.put("estado", c.getEstado());
                
    //             return ResponseEntity.ok(datos);
    //         } else {
    //             return ResponseEntity.notFound().build();
    //         }
    //     } catch (Exception e) {
    //         return ResponseEntity.badRequest().body("Error: " + e.getMessage());
    //     }
    // }


    @GetMapping("/obtener/{id}")
    @ResponseBody
    public ResponseEntity<?> obtenerConsentimiento(@PathVariable Long id) {
        try {
            Optional<Consentimiento> optional = consentimientoService.obtenerPorId(id);
            if (optional.isPresent()) {
                Consentimiento c = optional.get();
                
                // Crear DTO de respuesta
                ConsentimientoDetalleDTO dto = new ConsentimientoDetalleDTO();
                dto.setIdConsentimiento(c.getIdConsentimiento());
                dto.setDecision(c.getDecision());
                dto.setExplicacion(c.getExplicacion());
                dto.setEstado(c.getEstado());
                
                // Datos del paciente (a través de la consulta)
                if (c.getConsulta() != null && c.getConsulta().getPaciente() != null) {
                    var paciente = c.getConsulta().getPaciente();
                    dto.setIdPaciente(paciente.getIdPaciente());
                    dto.setNombrePaciente(paciente.getPersona().getNombre() + " " + 
                                        paciente.getPersona().getApellidoPaterno());
                    dto.setEdadPaciente(paciente.getPersona().getEdad());
                }
                
                // Datos del tratamiento
                if (c.getTratamiento() != null) {
                    dto.setIdTratamiento(c.getTratamiento().getIdTratamiento());
                    dto.setNombreTratamiento(c.getTratamiento().getNombreTratamiento());
                    dto.setDescripcionTratamiento(c.getTratamiento().getDescripcionTratamiento());
                    dto.setPrecioTratamiento(c.getTratamiento().getPrecioTratamiento());
                }
                
                return ResponseEntity.ok(dto);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }
    }
}