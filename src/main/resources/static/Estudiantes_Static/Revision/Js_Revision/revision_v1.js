// URL base de tu API Spring Boot - AJUSTA SEGÚN TU CONFIGURACIÓN
const API_BASE_URL = 'http://localhost:8080/api/pacientes';

document.addEventListener('DOMContentLoaded', async function() {

    const idEstudiante = sessionStorage.getItem('idEstudiante');  

    if (!idEstudiante){
        alert('debe iniciar sesiòn primero');
        window.location.href='/';
        return;
    }

    await mostrarPacientePrestado();

    // ===== PREVISUALIZACIÓN DE IMÁGENES DEL ODONTOGRAMA (NUEVO) =====
    const odontogramaFotos = document.getElementById('odontogramaFotos');
    const previsualizacion = document.getElementById('previsualizacionOdontograma');

    if (odontogramaFotos) {
        odontogramaFotos.addEventListener('change', function(e) {
            previsualizacion.innerHTML = ''; // Limpiar
            const archivos = Array.from(e.target.files);
            
            archivos.forEach(archivo => {
                if (archivo.type.startsWith('image/')) {
                    const reader = new FileReader();
                    reader.onload = function(event) {
                        const img = document.createElement('img');
                        img.src = event.target.result;
                        img.style.width = '100px';
                        img.style.height = '100px';
                        img.style.objectFit = 'cover';
                        img.style.borderRadius = '5px';
                        img.style.border = '1px solid #ccc';
                        previsualizacion.appendChild(img);
                    };
                    reader.readAsDataURL(archivo);
                }
            });
        });
    }

    async function mostrarPacientePrestado() {
        try {
            // 1. Verificar qué tenemos en sessionStorage (frontend)
            const idEstudianteFront = sessionStorage.getItem('idEstudiante');
            console.log('🔵 Frontend - ID en sessionStorage:', idEstudianteFront);
            
            // 2. Verificar qué tiene el backend en sesión
            const sesionResponse = await fetch('/api/pacientes/debug/verificar-sesion');
            const sesionData = await sesionResponse.json();
            console.log('🟢 Backend - Datos de sesión:', sesionData);
            
            // 3. Si el backend no tiene sesión, pero el frontend sí, algo está mal
            if (!sesionData.tieneSesion && idEstudianteFront) {
                console.warn('⚠️ El frontend tiene ID pero el backend no. Intentando reparar...');
            }
            
            // 4. Ahora sí, buscar el préstamo
            const response = await fetch('/api/pacientes/estudiante/paciente-prestado');
            const data = await response.json();
            
            console.log('📦 Datos del préstamo:', data);
            
            if (data.tienePrestamo) {
                const bannerHTML = `
                    <div style="
                        background-color: #e3f2fd;
                        border: 2px solid #2196f3;
                        border-radius: 8px;
                        padding: 15px;
                        margin-bottom: 20px;
                        display: flex;
                        align-items: center;
                        justify-content: space-between;
                    ">
                        <div>
                            <strong style="color: #1976d2;">📋 PACIENTE ASIGNADO:</strong>
                            <span style="margin-left: 10px; font-size: 1.2em; color: #0d47a1;">
                                ${data.nombreCompleto}
                            </span>
                        </div>
                        <div>
                            <span style="background-color: #4caf50; color: white; padding: 5px 10px; border-radius: 20px;">
                                CI: ${data.ci}
                            </span>
                            <span style="margin-left: 10px; color: #f57c00;">
                                ⏰ Límite: ${data.fechaLimite}
                            </span>
                        </div>
                    </div>
                `;
                
                document.querySelector('.form-section').insertAdjacentHTML('afterbegin', bannerHTML);
                document.getElementById('nombreBusqueda').value = data.nombreCompleto;
                
            } else {
                // Mostrar más información para debug
                const warningHTML = `
                    <div style="
                        background-color: #fff3e0;
                        border: 2px solid #ff9800;
                        border-radius: 8px;
                        padding: 15px;
                        margin-bottom: 20px;
                        color: #e65100;
                    ">
                        ⚠️ No tienes ningún préstamo activo. Debes solicitar un archivo en la biblioteca.
                        <br><br>
                        <small>
                            Debug: ID Estudiante (Frontend): ${idEstudianteFront}<br>
                            Debug: ID Estudiante (Backend): ${sesionData.idEstudianteEnSesion}<br>
                            Debug: ¿Tiene sesión?: ${sesionData.tieneSesion}
                        </small>
                    </div>
                `;
                document.querySelector('.form-section').insertAdjacentHTML('afterbegin', warningHTML);
            }
        } catch (error) {
            console.error('❌ Error al obtener paciente prestado:', error);
        }
    }

    // ===== VALIDACIONES ANTECEDENTES PATOLÓGICOS =====
    
    // Elementos de enfermedades
    const ningunaEnfermedad = document.getElementById('ningunaEnfermedad');
    const enfermedadesCheckboxes = document.querySelectorAll('.enfermedad-checkbox');
    const otrosEnfermedades = document.getElementById('otrosEnfermedades');
    const otrosValidation = document.getElementById('otrosValidation');

    function actualizarValidacionEnfermedades() {
        const algunaEnfermedadSeleccionada = Array.from(enfermedadesCheckboxes).some(cb => cb.checked) || otrosEnfermedades.value.trim() !== '';
        
        if (algunaEnfermedadSeleccionada && ningunaEnfermedad.checked) {
            otrosValidation.style.display = 'block';
            ningunaEnfermedad.classList.add('error-border');
        } else {
            otrosValidation.style.display = 'none';
            ningunaEnfermedad.classList.remove('error-border');
        }

        // Actualizar también el estado de los campos basado en "Ninguno"
        if (ningunaEnfermedad.checked) {
            enfermedadesCheckboxes.forEach(checkbox => {
                checkbox.disabled = true;
            });
            otrosEnfermedades.disabled = true;
        } else {
            enfermedadesCheckboxes.forEach(checkbox => {
                checkbox.disabled = false;
            });
            otrosEnfermedades.disabled = false;
        }
    }

    ningunaEnfermedad.addEventListener('change', function() {
        if (this.checked) {
            // Deshabilitar todas las otras opciones
            enfermedadesCheckboxes.forEach(checkbox => {
                checkbox.checked = false;
                checkbox.disabled = true;
            });
            otrosEnfermedades.value = '';
            otrosEnfermedades.disabled = true;
        } else {
            // Habilitar todas las otras opciones
            enfermedadesCheckboxes.forEach(checkbox => {
                checkbox.disabled = false;
            });
            otrosEnfermedades.disabled = false;
        }
        actualizarValidacionEnfermedades();
    });

    enfermedadesCheckboxes.forEach(checkbox => {
        checkbox.addEventListener('change', function() {
            if (this.checked) {
                ningunaEnfermedad.checked = false;
            }
            actualizarValidacionEnfermedades();
        });
    });

    otrosEnfermedades.addEventListener('input', function() {
        if (this.value.trim() !== '') {
            ningunaEnfermedad.checked = false;
        }
        actualizarValidacionEnfermedades();
    });

    // Validación: Alergias
    const alergiaSi = document.getElementById('alergias-si');
    const alergiaNo = document.getElementById('alergias-no');
    const especifiqueAlergia = document.getElementById('especifiqueAlergia');
    const alergiaValidation = document.getElementById('alergiaValidation');

    function actualizarValidacionAlergias() {
        if (alergiaSi.checked && especifiqueAlergia.value.trim() === '') {
            alergiaValidation.style.display = 'block';
            especifiqueAlergia.classList.add('error-border');
        } else {
            alergiaValidation.style.display = 'none';
            especifiqueAlergia.classList.remove('error-border');
        }
    }

    alergiaSi.addEventListener('change', function() {
        especifiqueAlergia.disabled = false;
        actualizarValidacionAlergias();
    });

    alergiaNo.addEventListener('change', function() {
        especifiqueAlergia.disabled = true;
        especifiqueAlergia.value = '';
        alergiaValidation.style.display = 'none';
    });

    especifiqueAlergia.addEventListener('input', actualizarValidacionAlergias);

    // Validación: Embarazo
    const embarazoSi = document.getElementById('embarazo-si');
    const embarazoNo = document.getElementById('embarazo-no');
    const semanasEmbarazo = document.getElementById('semanasEmbarazo');
    const embarazoValidation = document.getElementById('embarazoValidation');

    function actualizarValidacionEmbarazo() {
        if (embarazoSi.checked && semanasEmbarazo.value.trim() === '') {
            embarazoValidation.style.display = 'block';
            semanasEmbarazo.classList.add('error-border');
        } else {
            embarazoValidation.style.display = 'none';
            semanasEmbarazo.classList.remove('error-border');
        }
    }

    embarazoSi.addEventListener('change', function() {
        semanasEmbarazo.disabled = false;
        actualizarValidacionEmbarazo();
    });

    embarazoNo.addEventListener('change', function() {
        semanasEmbarazo.disabled = true;
        semanasEmbarazo.value = '';
        embarazoValidation.style.display = 'none';
    });

    semanasEmbarazo.addEventListener('input', actualizarValidacionEmbarazo);

    // Validación: Hemorragia
    const hemorragiaSi = document.getElementById('hemorragia-si');
    const hemorragiaNo = document.getElementById('hemorragia-no');
    const especifiqueHemorragia = document.getElementById('especifiqueHemorragia');
    const hemorragiaValidation = document.getElementById('hemorragiaValidation');

    function actualizarValidacionHemorragia() {
        if (hemorragiaSi.checked && especifiqueHemorragia.value.trim() === '') {
            hemorragiaValidation.style.display = 'block';
            especifiqueHemorragia.classList.add('error-border');
        } else {
            hemorragiaValidation.style.display = 'none';
            especifiqueHemorragia.classList.remove('error-border');
        }
    }

    hemorragiaSi.addEventListener('change', function() {
        especifiqueHemorragia.disabled = false;
        actualizarValidacionHemorragia();
    });

    hemorragiaNo.addEventListener('change', function() {
        especifiqueHemorragia.disabled = true;
        especifiqueHemorragia.value = '';
        hemorragiaValidation.style.display = 'none';
    });

    especifiqueHemorragia.addEventListener('input', actualizarValidacionHemorragia);

    // ===== VALIDACIONES PRÓTESIS DENTAL =====
    const protesisSi = document.getElementById('protesis-si');
    const protesisNo = document.getElementById('protesis-no');
    const tipoProtesis = document.getElementById('tipoProtesis');
    const tiempoProtesis = document.getElementById('tiempoProtesis');
    const protesisValidation = document.getElementById('protesisValidation');

    function actualizarValidacionProtesis() {
        if (protesisSi.checked && tipoProtesis.value.trim() === '') {
            protesisValidation.style.display = 'block';
            tipoProtesis.classList.add('error-border');
        } else {
            protesisValidation.style.display = 'none';
            tipoProtesis.classList.remove('error-border');
        }
    }

    protesisSi.addEventListener('change', function() {
        tipoProtesis.disabled = false;
        tiempoProtesis.disabled = false;
        actualizarValidacionProtesis();
    });

    protesisNo.addEventListener('change', function() {
        tipoProtesis.disabled = true;
        tiempoProtesis.disabled = true;
        tipoProtesis.value = '';
        tiempoProtesis.value = '';
        protesisValidation.style.display = 'none';
    });

    tipoProtesis.addEventListener('input', actualizarValidacionProtesis);

    // ===== VALIDACIÓN TRATAMIENTO MÉDICO =====
    const tratamientoSi = document.getElementById('tratamiento-si');
    const tratamientoNo = document.getElementById('tratamiento-no');
    const tratamientoMedico = document.getElementById('tratamientoMedico');
    const tratamientoValidation = document.getElementById('tratamientoValidation');

    function actualizarValidacionTratamiento() {
        if (tratamientoSi && tratamientoSi.checked && tratamientoMedico.value.trim() === '') {
            tratamientoValidation.style.display = 'block';
            tratamientoMedico.classList.add('error-border');
        } else {
            tratamientoValidation.style.display = 'none';
            tratamientoMedico.classList.remove('error-border');
        }
    }

    if (tratamientoSi && tratamientoNo) {
        tratamientoSi.addEventListener('change', function() {
            tratamientoMedico.disabled = false;
            actualizarValidacionTratamiento();
        });

        tratamientoNo.addEventListener('change', function() {
            tratamientoMedico.disabled = true;
            tratamientoMedico.value = '';
            tratamientoValidation.style.display = 'none';
            tratamientoMedico.classList.remove('error-border');
        });
    }

    if (tratamientoMedico) {
        tratamientoMedico.addEventListener('input', actualizarValidacionTratamiento);
    }

    // ===== VALIDACIÓN MEDICAMENTO ACTUAL =====
    const medicamentoSi = document.getElementById('medicamento-si');
    const medicamentoNo = document.getElementById('medicamento-no');
    const medicamentoActual = document.getElementById('medicamentoActual');
    const medicamentoValidation = document.getElementById('medicamentoValidation');

    function actualizarValidacionMedicamento() {
        if (medicamentoSi && medicamentoSi.checked && medicamentoActual.value.trim() === '') {
            medicamentoValidation.style.display = 'block';
            medicamentoActual.classList.add('error-border');
        } else {
            medicamentoValidation.style.display = 'none';
            medicamentoActual.classList.remove('error-border');
        }
    }

    if (medicamentoSi && medicamentoNo) {
        medicamentoSi.addEventListener('change', function() {
            medicamentoActual.disabled = false;
            actualizarValidacionMedicamento();
        });

        medicamentoNo.addEventListener('change', function() {
            medicamentoActual.disabled = true;
            medicamentoActual.value = '';
            medicamentoValidation.style.display = 'none';
            medicamentoActual.classList.remove('error-border');
        });
    }

    if (medicamentoActual) {
        medicamentoActual.addEventListener('input', actualizarValidacionMedicamento);
    }

    // ===== VALIDACIÓN HÁBITOS =====
    const habitoCheckboxes = document.querySelectorAll('.habito-checkbox');
    const otrosHabitos = document.getElementById('otrosHabitos');

    habitoCheckboxes.forEach(checkbox => {
        checkbox.addEventListener('change', function() {
            const algunHabitoSeleccionado = Array.from(habitoCheckboxes).some(cb => cb.checked);
            if (algunHabitoSeleccionado) {
                otrosHabitos.value = '';
            }
        });
    });

    otrosHabitos.addEventListener('input', function() {
        if (this.value.trim() !== '') {
            habitoCheckboxes.forEach(checkbox => {
                checkbox.checked = false;
            });
        }
    });

    // ===== MANEJO DEL CAMPO "OTROS" EN RESPIRADOR =====
    const otrosRespiratorio = document.getElementById('otrosRespiratorio');
    
    document.querySelectorAll('input[name="respirador"]').forEach(radio => {
        radio.addEventListener('change', function() {
            otrosRespiratorio.value = '';
        });
    });

    otrosRespiratorio.addEventListener('focus', function() {
        document.querySelectorAll('input[name="respirador"]').forEach(radio => {
            radio.checked = false;
        });
    });

    // ========== FUNCIONALIDAD DE BÚSQUEDA ==========
    
    // Elementos de búsqueda
    const nombreBusqueda = document.getElementById('nombreBusqueda');
    const ciBusqueda = document.getElementById('ciBusqueda');
    const btnBuscar = document.getElementById('btnBuscar');
    const resultadosBusqueda = document.getElementById('resultadosBusqueda');
    const listaResultados = document.getElementById('listaResultados');
    
    // Elementos del formulario para llenar con datos del paciente
    const numHistoriaClinica = document.getElementById('numHistoriaClinica');
    const ciPaciente = document.getElementById('ciPaciente');
    const numExpediente = document.getElementById('numExpediente');
    const personaInformacion = document.getElementById('personaInformacion');
    const edadPaciente = document.getElementById('edadPaciente');
    
    // Elementos de datos personales
    const apellidoPaterno = document.getElementById('apellidoPaterno');
    const apellidoMaterno = document.getElementById('apellidoMaterno');
    const nombres = document.getElementById('nombres');
    const lugarNacimiento = document.getElementById('lugarNacimiento');
    const ocupacion = document.getElementById('ocupacion');
    const estadoCivil = document.getElementById('estadoCivil');
    const direccion = document.getElementById('direccion');
    const telefono = document.getElementById('telefono');
    const gradoInstruccion = document.getElementById('gradoInstruccion');
    
    // Elementos del header
    const headerExpediente = document.getElementById('headerExpediente');
    const headerHistoria = document.getElementById('headerHistoria');
    const headerCI = document.getElementById('headerCI');

    // Función para buscar pacientes
    async function buscarPacientes() {
        const nombre = nombreBusqueda.value.trim();
        const ci = ciBusqueda.value.trim();

        if (!nombre && !ci) {
            alert('Por favor, ingrese un nombre o CI para buscar.');
            return;
        }

        listaResultados.innerHTML = '<div class="loading">Buscando pacientes...</div>';
        resultadosBusqueda.style.display = 'block';
        btnBuscar.disabled = true;
        btnBuscar.textContent = 'BUSCANDO...';

        try {
            let url;
                        
            if (ci) {
                url = `/api/pacientes/estudiante/buscar-por-ci?ci=${encodeURIComponent(ci)}`;
            } else {
                url = `/api/pacientes/estudiante/buscar?term=${encodeURIComponent(nombre)}`;
            }

            console.log('Buscando pacientes con URL:', url);

            const response = await fetch(url);

            if (response.status === 403) {
                const errorData = await response.json();
                
                if (errorData.pacientePermitido) {
                    const pacientePermitido = errorData.pacientePermitido;
                    
                    listaResultados.innerHTML = `
                        <div style="
                            background-color: #ffebee;
                            border-left: 4px solid #f44336;
                            padding: 20px;
                            border-radius: 4px;
                        ">
                            <h3 style="color: #d32f2f; margin-bottom: 10px;">
                                ⛔ Paciente Incorrecto
                            </h3>
                            <p style="margin-bottom: 10px;">
                                ${errorData.mensaje || 'No tienes permiso para consultar este paciente.'}
                            </p>
                            <div style="background-color: #fff; padding: 15px; border-radius: 4px; margin-top: 10px;">
                                <strong style="color: #1976d2;">✅ Paciente que DEBES consultar:</strong>
                                <p style="font-size: 1.1em; margin-top: 5px;">
                                    ${pacientePermitido.nombreCompleto} (CI: ${pacientePermitido.ci})
                                </p>
                            </div>
                            <button onclick="autoCompletarPacientePermitido()" 
                                    style="margin-top: 15px; background-color: #2196f3; color: white; 
                                           border: none; padding: 10px 20px; border-radius: 4px; cursor: pointer;">
                                Buscar paciente permitido
                            </button>
                        </div>
                    `;
                }
                return;
            }

            if (response.status === 403) {
                const errorData = await response.json();
                
                alert(`⛔ ACCESO BLOQUEADO\n\n` +
                    `Motivo: No devolvió el archivo a tiempo\n` +
                    `Fecha de bloqueo: ${errorData.fechaBloqueo || 'Desconocida'}\n\n` +
                    `Por favor, acuda a la biblioteca para regularizar su situación.`);
                
                window.location.href = '/bloqueado';
                return;
            }

            if (!response.ok) {
                throw new Error(`Error en la búsqueda: ${response.status}`);
            }

            const pacientes = await response.json();
            console.log('Pacientes encontrados:', pacientes);
            mostrarResultados(pacientes);

        } catch (error) {
            console.error('Error al buscar pacientes:', error);
            
            if (error.message.includes('403')) {
                window.location.href = '/bloqueado';
            } else {
                listaResultados.innerHTML = '<div class="no-results">Error al realizar la búsqueda. Verifique la conexión.</div>';
            }
        } finally {
            btnBuscar.disabled = false;
            btnBuscar.textContent = 'BUSCAR';
        }
    }

    // Función para auto-completar con el paciente permitido
    function autoCompletarPacientePermitido() {
        fetch('/api/pacientes/estudiante/paciente-prestado')
            .then(response => response.json())
            .then(data => {
                if (data.tienePrestamo) {
                    nombreBusqueda.value = data.nombreCompleto;
                    buscarPacientes();
                }
            });
    }
        
    // Función para mostrar resultados de búsqueda
    function mostrarResultados(pacientes) {
        listaResultados.innerHTML = '';
        
        if (!pacientes || pacientes.length === 0) {
            listaResultados.innerHTML = '<div class="no-results">No se encontraron pacientes con los criterios de búsqueda.</div>';
        } else {
            pacientes.forEach(paciente => {
                const item = document.createElement('div');
                item.className = 'result-item';

                const nombreCompleto = `${paciente.persona?.nombre || ''} ${paciente.persona?.apellidoPaterno || ''} ${paciente.persona?.apellidoMaterno || ''}`.trim();
                const ciMostrado = paciente.ci !== null && paciente.ci !== undefined ? paciente.ci : (paciente.persona?.ci || 'N/A');

                item.innerHTML = `
                    <strong>${nombreCompleto}</strong> - CI: ${ciMostrado} - Historial: ${paciente.historialClinico || 'N/A'}
                `;
                item.addEventListener('click', () => seleccionarPaciente(paciente));
                listaResultados.appendChild(item);
            });
        }
        
        resultadosBusqueda.style.display = 'block';
    }
    
    // Función para seleccionar un paciente y llenar el formulario
    function seleccionarPaciente(paciente) {
        numHistoriaClinica.value = paciente.historialClinico || '';
        ciPaciente.value = paciente.ci !== null && paciente.ci !== undefined ? paciente.ci : '';
        numExpediente.value = paciente.idPaciente || paciente.id || '';
        const nombreCompleto = `${paciente.persona?.nombre || ''} ${paciente.persona?.apellidoPaterno || ''} ${paciente.persona?.apellidoMaterno || ''}`.trim();
        personaInformacion.value = nombreCompleto;
        edadPaciente.value = paciente.persona?.edad || '';

        apellidoPaterno.value = paciente.persona?.apellidoPaterno || '';
        apellidoMaterno.value = paciente.persona?.apellidoMaterno || '';
        nombres.value = paciente.persona?.nombre || '';
        lugarNacimiento.value = paciente.lugarNacimiento || '';
        ocupacion.value = paciente.ocupacion || '';
        estadoCivil.value = paciente.estadoCivil || '';
        direccion.value = paciente.direccion || '';
        telefono.value = paciente.telefono || '';
        gradoInstruccion.value = paciente.gradoInstruccion || '';

        if (paciente.persona?.sexo) {
            const sexo = String(paciente.persona.sexo).toUpperCase();
            if (sexo === 'M') {
                document.getElementById('masculino').checked = true;
            } else if (sexo === 'F') {
                document.getElementById('femenino').checked = true;
            }
        }

        headerExpediente.textContent = paciente.idPaciente || paciente.id || '-';
        headerHistoria.textContent = paciente.historialClinico || '-';
        headerCI.textContent = paciente.ci !== null && paciente.ci !== undefined ? paciente.ci : '-';

        resultadosBusqueda.style.display = 'none';
        nombreBusqueda.value = '';
        ciBusqueda.value = '';

        document.querySelector('.form-section').scrollIntoView({ behavior: 'smooth' });
    }
    
    // Event listeners para búsqueda
    btnBuscar.addEventListener('click', buscarPacientes);
    
    nombreBusqueda.addEventListener('keypress', function(e) {
        if (e.key === 'Enter') {
            buscarPacientes();
        }
    });
    
    ciBusqueda.addEventListener('keypress', function(e) {
        if (e.key === 'Enter') {
            buscarPacientes();
        }
    });
    
    document.addEventListener('click', function(e) {
        if (!resultadosBusqueda.contains(e.target) && 
            e.target !== nombreBusqueda && 
            e.target !== ciBusqueda && 
            e.target !== btnBuscar) {
            resultadosBusqueda.style.display = 'none';
        }
    });

    // ===== FUNCIONES AUXILIARES PARA OBTENER VALORES =====
    
    // Función para obtener respirador seleccionado (incluye "Otros")
    function obtenerRespiradorSeleccionado() {
        if (document.getElementById('resp-nasal').checked) return 'Nasal';
        if (document.getElementById('resp-bucal').checked) return 'Bucal';
        if (document.getElementById('resp-buco-nasal').checked) return 'Buco nasal';
        
        const otrosRespiratorio = document.getElementById('otrosRespiratorio').value.trim();
        if (otrosRespiratorio) return `Otros: ${otrosRespiratorio}`;
        
        return '';
    }

    // Función auxiliar para obtener valores booleanos
    function obtenerValorBooleano(elementoSi, elementoNo) {
        return elementoSi.checked;
    }

    // Función para obtener higiene bucal seleccionada
    function obtenerHigieneBucalSeleccionada() {
        if (document.getElementById('higiene-buena').checked) return 'Buena';
        if (document.getElementById('higiene-regular').checked) return 'Regular';
        if (document.getElementById('higiene-mala').checked) return 'Mala';
        return '';
    }

    // ===== VALIDACIÓN GENERAL DEL FORMULARIO =====
    function validarFormularioCompleto() {
        let errores = [];

        // Validar campos obligatorios básicos
        const numHistoria = document.getElementById('numHistoriaClinica');
        const ci = document.getElementById('ciPaciente');
        const nombres = document.getElementById('nombres');
        const apellidoPaterno = document.getElementById('apellidoPaterno');
        
        if (!numHistoria.value.trim()) errores.push('Número de Historia Clínica es obligatorio');
        if (!ci.value.trim()) errores.push('C.I. es obligatorio');
        if (!nombres.value.trim()) errores.push('Nombres son obligatorios');
        if (!apellidoPaterno.value.trim()) errores.push('Apellido Paterno es obligatorio');

        // Validar alergias
        const alergiaSi = document.getElementById('alergias-si');
        const especifiqueAlergia = document.getElementById('especifiqueAlergia');
        if (alergiaSi.checked && !especifiqueAlergia.value.trim()) {
            errores.push('Debe especificar la alergia');
        }

        // Validar embarazo
        const embarazoSi = document.getElementById('embarazo-si');
        const semanasEmbarazo = document.getElementById('semanasEmbarazo');
        if (embarazoSi.checked && !semanasEmbarazo.value.trim()) {
            errores.push('Debe especificar las semanas de embarazo');
        }

        // Validar hemorragia
        const hemorragiaSi = document.getElementById('hemorragia-si');
        const especifiqueHemorragia = document.getElementById('especifiqueHemorragia');
        if (hemorragiaSi.checked && !especifiqueHemorragia.value.trim()) {
            errores.push('Debe especificar el tipo de hemorragia');
        }

        // Validar prótesis
        const protesisSi = document.getElementById('protesis-si');
        const tipoProtesis = document.getElementById('tipoProtesis');
        if (protesisSi.checked && !tipoProtesis.value.trim()) {
            errores.push('Debe especificar el tipo de prótesis');
        }

        // Validar tratamiento médico
        if (tratamientoSi && tratamientoSi.checked && !tratamientoMedico.value.trim()) {
            errores.push('Debe especificar el tratamiento médico');
        }

        // Validar medicamento actual
        if (medicamentoSi && medicamentoSi.checked && !medicamentoActual.value.trim()) {
            errores.push('Debe especificar los medicamentos que recibe');
        }

        // Validar conflicto "ninguno" vs enfermedades
        const ningunaEnfermedad = document.getElementById('ningunaEnfermedad');
        const enfermedadesCheckboxes = document.querySelectorAll('.enfermedad-checkbox');
        const otrosEnfermedades = document.getElementById('otrosEnfermedades');
        
        const algunaEnfermedadSeleccionada = Array.from(enfermedadesCheckboxes).some(cb => cb.checked) || otrosEnfermedades.value.trim() !== '';
        if (algunaEnfermedadSeleccionada && ningunaEnfermedad.checked) {
            errores.push('No puede seleccionar "Ninguno" junto con otras enfermedades');
        }

        if (errores.length > 0) {
            alert('Errores encontrados:\n' + errores.join('\n'));
            return false;
        } else {
            return true;
        }
    }

    // ===== ENVÍO DEL FORMULARIO CON FOTOS (MODIFICADO) =====
    document.getElementById('enviarFormulario').addEventListener('click', async function() {
        if (!validarFormularioCompleto()) {
            return;
        }

        const idPaciente = document.getElementById('numExpediente').value;
        if (!idPaciente) {
            alert('Error: Debe seleccionar un paciente primero');
            return;
        }

        console.log('🔍 ID Paciente a verificar:', idPaciente);
        console.log('🔍 ID Estudiante en sesión:', sessionStorage.getItem('idEstudiante'));

        const permisoResponse = await fetch(`/api/pacientes/estudiante/verificar-permiso?idPaciente=${idPaciente}`);
        const permisoData = await permisoResponse.json();
        
        if (!permisoData.puedeConsultar) {
            alert('No puedes guardar esta consulta. Solo puedes consultar al paciente que tienes prestado.');
            return;
        }
        
        // Crear FormData en lugar de objeto JSON
        const formData = new FormData();

        // --- Datos básicos ---
        formData.append('fecha', new Date().toISOString().split('T')[0]);
        formData.append('observaciones', document.getElementById('observacionesHigiene').value);
        formData.append('idPaciente', idPaciente);
        formData.append('idEstudiante', sessionStorage.getItem('idEstudiante'));

        // --- Informante ---
        formData.append('informanteNombres', document.getElementById('nombres_pesona').value);
        formData.append('informanteApellidoPaterno', document.getElementById('apellidoPaterno_persona').value);
        formData.append('informanteApellidoMaterno', document.getElementById('apellidoMaterno_persona').value);
        formData.append('informanteDireccion', document.getElementById('direccion_persona').value);
        formData.append('informanteTelefono', document.getElementById('telefono_persona').value);

        // --- Patologia Personal ---
        formData.append('anemia', document.getElementById('anemia').checked);
        formData.append('cardiopatias', document.getElementById('cardiopatias').checked);
        formData.append('enfGastricos', document.getElementById('gastricas').checked);
        formData.append('hepatitis', document.getElementById('hepatitis').checked);
        formData.append('tuberculosis', document.getElementById('tuberculosis').checked);
        formData.append('asma', document.getElementById('asma').checked);
        formData.append('diabetesMel', document.getElementById('diabetes').checked);
        formData.append('epilepsia', document.getElementById('epilepsia').checked);
        formData.append('hipertension', document.getElementById('hipertension').checked);
        formData.append('vih', document.getElementById('vih').checked);
        formData.append('otros', document.getElementById('otrosEnfermedades').value);
        formData.append('ninguno', document.getElementById('ningunaEnfermedad').checked);

        // Alergias
        const alergiaSi = document.getElementById('alergias-si').checked;
        formData.append('alergias', alergiaSi);
        if (alergiaSi) {
            formData.append('especifiqueAlergia', document.getElementById('especifiqueAlergia').value);
        }

        // Embarazo
        const embarazoSi = document.getElementById('embarazo-si').checked;
        formData.append('embarazo', embarazoSi);
        if (embarazoSi) {
            formData.append('semanaEmbarazo', document.getElementById('semanasEmbarazo').value);
        }

        // --- Tratamiento médico ---
        const tratamientoSi = document.getElementById('tratamiento-si').checked;
        formData.append('tratamientoMedico', tratamientoSi ? document.getElementById('tratamientoMedico').value : '');
        const medicamentoSi = document.getElementById('medicamento-si').checked;
        formData.append('recibeAlgunMedicamento', medicamentoSi ? document.getElementById('medicamentoActual').value : '');

        // Hemorragia
        const hemorragiaSi = document.getElementById('hemorragia-si').checked;
        formData.append('tuvoHemorragiaDental', hemorragiaSi);
        if (hemorragiaSi) {
            formData.append('especifiqueHemorragia', document.getElementById('especifiqueHemorragia').value);
        }

        // --- ExamenExtraOral ---
        formData.append('atm', document.getElementById('atm').value);
        formData.append('gangliosLinfaticos', document.getElementById('ganglios').value);
        formData.append('respirador', obtenerRespiradorSeleccionado());
        formData.append('otrosRespiratorio', document.getElementById('otrosRespiratorio').value);

        // --- ExamenIntraOral ---
        formData.append('labios', document.getElementById('labios').value);
        formData.append('lengua', document.getElementById('lengua').value);
        formData.append('paladar', document.getElementById('paladar').value);
        formData.append('pisoDeLaBoca', document.getElementById('pisoBoca').value);
        formData.append('mucosaYugal', document.getElementById('mucosaYugal').value);
        formData.append('encias', document.getElementById('encias').value);
        const protesisSi = document.getElementById('protesis-si').checked;
        formData.append('utilizaProtesisDental', protesisSi);
        if (protesisSi) {
            formData.append('tipoProtesis', document.getElementById('tipoProtesis').value);
            formData.append('tiempoProtesis', document.getElementById('tiempoProtesis').value);
        }

        // --- AntecedentesBucodentales ---
        formData.append('fechaRevision', document.getElementById('ultimaVisita').value);
        formData.append('habitoFuma', document.getElementById('fuma').checked);
        formData.append('habitoBebe', document.getElementById('bebe').checked);
        formData.append('otrosHabitos', document.getElementById('otrosHabitos').value);

        // --- AntecedentesHigieneOral ---
        formData.append('utilizaCepilloDental', document.getElementById('cepillo-si').checked);
        formData.append('utilizaHiloDental', document.getElementById('hilo-si').checked);
        formData.append('utilizaEnjuagueBucal', document.getElementById('enjuague-si').checked);
        formData.append('frecuenciaCepillo', document.getElementById('frecuenciaCepillado').value);
        formData.append('sangradoEncias', document.getElementById('sangrado-si').checked);
        formData.append('higieneBucal', obtenerHigieneBucalSeleccionada());
        formData.append('observacionesHigiene', document.getElementById('observacionesHigiene').value);

        // --- FOTOS: agregar archivos ---
        const fotos = document.getElementById('odontogramaFotos').files;
        for (let i = 0; i < fotos.length; i++) {
            formData.append('odontogramaFotos', fotos[i]); // el nombre debe coincidir con el campo del DTO
        }

        // Enviar datos al backend
        const btnEnviar = document.getElementById('enviarFormulario');
        btnEnviar.disabled = true;
        btnEnviar.textContent = 'Guardando...';
        document.getElementById('loadingMessage').style.display = 'block';

        try {
            const response = await fetch('/api/consultas/completa-con-fotos', { // NUEVO ENDPOINT
                method: 'POST',
                body: formData
                // No poner 'Content-Type', el navegador lo añade automáticamente
            });

            if (!response.ok) {
                const errorText = await response.text();
                throw new Error(errorText || `Error ${response.status}: ${response.statusText}`);
            }

            const data = await response.json();
            console.log('✅ Consulta guardada con fotos:', data);
            alert('¡Historia clínica guardada exitosamente!');
        } catch (error) {
            console.error('❌ Error al guardar:', error);
            alert('Error al guardar la historia clínica: ' + error.message);
        } finally {
            document.getElementById('loadingMessage').style.display = 'none';
            btnEnviar.disabled = false;
            btnEnviar.textContent = 'GUARDAR HISTORIA CLÍNICA COMPLETA';
        }
    });

    // Inicializar estado de los campos al cargar la página
    actualizarValidacionEnfermedades();
    
    // Inicializar estado de tratamiento médico y medicamento
    if (tratamientoNo) {
        tratamientoNo.checked = true;
        if (tratamientoMedico) {
            tratamientoMedico.disabled = true;
            tratamientoMedico.value = '';
        }
    }

    if (medicamentoNo) {
        medicamentoNo.checked = true;
        if (medicamentoActual) {
            medicamentoActual.disabled = true;
            medicamentoActual.value = '';
        }
    }
});