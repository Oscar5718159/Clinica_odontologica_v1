// URL base de tu API Spring Boot - AJUSTA SEGÚN TU CONFIGURACIÓN
const API_BASE_URL = 'http://localhost:8080/api/pacientes';

document.addEventListener('DOMContentLoaded', function() {
    // ===== VALIDACIONES ANTECEDENTES PATOLÓGICOS =====
    
    // Validación: Ninguna enfermedad vs enfermedades específicas
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
            // Si se selecciona cualquier opción de respirador, limpiar el campo "Otros"
            otrosRespiratorio.value = '';
        });
    });

    otrosRespiratorio.addEventListener('focus', function() {
        // Deseleccionar cualquier opción de respirador cuando el usuario escriba en "Otros"
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
    
    // Función para buscar pacientes en el backend
    async function buscarPacientes() {
        const nombre = nombreBusqueda.value.trim();
        const ci = ciBusqueda.value.trim();

        // Validar que al menos un campo tenga datos
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
            
            // ✅ BÚSQUEDA SEPARADA: Si hay CI, buscar solo por CI
            if (ci) {
                url = `${API_BASE_URL}/buscar-por-ci?ci=${encodeURIComponent(ci)}`;
            } else {
                // Si solo hay nombre, buscar por nombre
                url = `${API_BASE_URL}/buscar?term=${encodeURIComponent(nombre)}`;
            }

            console.log('Buscando pacientes con URL:', url);

            const response = await fetch(url);

            if (!response.ok) {
                throw new Error(`Error en la búsqueda: ${response.status}`);
            }

            const pacientes = await response.json();
            console.log('Pacientes encontrados:', pacientes);
            mostrarResultados(pacientes);

        } catch (error) {
            console.error('Error al buscar pacientes:', error);
            listaResultados.innerHTML = '<div class="no-results">Error al realizar la búsqueda. Verifique la conexión.</div>';
        } finally {
            btnBuscar.disabled = false;
            btnBuscar.textContent = 'BUSCAR';
        }
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

                // Formatear nombre completo usando los campos reales
                const nombreCompleto = `${paciente.persona?.nombre || ''} ${paciente.persona?.apellidoPaterno || ''} ${paciente.persona?.apellidoMaterno || ''}`.trim();

                // CI está en la entidad Paciente (campo ci)
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
        // Llenar campos del formulario con datos del paciente
        numHistoriaClinica.value = paciente.historialClinico || '';
        ciPaciente.value = paciente.ci !== null && paciente.ci !== undefined ? paciente.ci : '';
        numExpediente.value = paciente.idPaciente || paciente.id || '';
        const nombreCompleto = `${paciente.persona?.nombre || ''} ${paciente.persona?.apellidoPaterno || ''} ${paciente.persona?.apellidoMaterno || ''}`.trim();
        personaInformacion.value = nombreCompleto;
        edadPaciente.value = paciente.persona?.edad || '';

        // Llenar datos personales con campos correctos
        apellidoPaterno.value = paciente.persona?.apellidoPaterno || '';
        apellidoMaterno.value = paciente.persona?.apellidoMaterno || '';
        nombres.value = paciente.persona?.nombre || '';
        lugarNacimiento.value = paciente.lugarNacimiento || '';
        ocupacion.value = paciente.ocupacion || '';
        estadoCivil.value = paciente.estadoCivil || '';
        direccion.value = paciente.direccion || '';
        telefono.value = paciente.telefono || '';
        gradoInstruccion.value = paciente.gradoInstruccion || '';

        // Establecer el sexo
        if (paciente.persona?.sexo) {
            const sexo = String(paciente.persona.sexo).toUpperCase();
            if (sexo === 'M') {
                document.getElementById('masculino').checked = true;
            } else if (sexo === 'F') {
                document.getElementById('femenino').checked = true;
            }
        }

        // Actualizar header
        headerExpediente.textContent = paciente.idPaciente || paciente.id || '-';
        headerHistoria.textContent = paciente.historialClinico || '-';
        headerCI.textContent = paciente.ci !== null && paciente.ci !== undefined ? paciente.ci : '-';

        // Ocultar resultados
        resultadosBusqueda.style.display = 'none';

        // Limpiar campos de búsqueda
        nombreBusqueda.value = '';
        ciBusqueda.value = '';

        // Desplazar al formulario principal
        document.querySelector('.form-section').scrollIntoView({ behavior: 'smooth' });
    }
    
    // Event listeners para búsqueda
    btnBuscar.addEventListener('click', buscarPacientes);
    
    // Permitir búsqueda con Enter
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
    
    // Ocultar resultados al hacer clic fuera
    document.addEventListener('click', function(e) {
        if (!resultadosBusqueda.contains(e.target) && 
            e.target !== nombreBusqueda && 
            e.target !== ciBusqueda && 
            e.target !== btnBuscar) {
            resultadosBusqueda.style.display = 'none';
        }
    });

    // ===== FUNCIONES AUXILIARES PARA OBTENER VALORES =====
    
    // Función para obtener enfermedades seleccionadas (incluye "Ninguno" y "Otros")
    function obtenerEnfermedadesSeleccionadas() {
        // Si está marcado "Ninguno", retornar "Ninguno"
        if (document.getElementById('ningunaEnfermedad').checked) {
            return 'Ninguno';
        }
        
        const enfermedades = [];
        if (document.getElementById('anemia').checked) enfermedades.push('Anemia');
        if (document.getElementById('cardiopatias').checked) enfermedades.push('Cardiopatías');
        if (document.getElementById('gastricas').checked) enfermedades.push('Enf. Gástricas');
        if (document.getElementById('hepatitis').checked) enfermedades.push('Hepatitis');
        if (document.getElementById('tuberculosis').checked) enfermedades.push('Tuberculosis');
        if (document.getElementById('asma').checked) enfermedades.push('Asma');
        if (document.getElementById('diabetes').checked) enfermedades.push('Diabetes Mel.');
        if (document.getElementById('epilepsia').checked) enfermedades.push('Epilepsia');
        if (document.getElementById('hipertension').checked) enfermedades.push('Hipertensión');
        if (document.getElementById('vih').checked) enfermedades.push('VIH');
        
        const otros = document.getElementById('otrosEnfermedades').value.trim();
        if (otros) enfermedades.push(`Otros: ${otros}`);
        
        return enfermedades.length > 0 ? enfermedades.join(', ') : 'Ninguno';
    }

    // Función para obtener respirador seleccionado (incluye "Otros")
    function obtenerRespiradorSeleccionado() {
        if (document.getElementById('resp-nasal').checked) return 'Nasal';
        if (document.getElementById('resp-bucal').checked) return 'Bucal';
        if (document.getElementById('resp-buco-nasal').checked) return 'Buco nasal';
        
        // Si hay texto en "Otros", usarlo
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

        // Validar conflicto "ninguno" vs enfermedades
        const ningunaEnfermedad = document.getElementById('ningunaEnfermedad');
        const enfermedadesCheckboxes = document.querySelectorAll('.enfermedad-checkbox');
        const otrosEnfermedades = document.getElementById('otrosEnfermedades');
        
        const algunaEnfermedadSeleccionada = Array.from(enfermedadesCheckboxes).some(cb => cb.checked) || otrosEnfermedades.value.trim() !== '';
        if (algunaEnfermedadSeleccionada && ningunaEnfermedad.checked) {
            errores.push('No puede seleccionar "Ninguno" junto con otras enfermedades');
        }

        // Mostrar errores o enviar formulario
        if (errores.length > 0) {
            alert('Errores encontrados:\n' + errores.join('\n'));
            return false;
        } else {
            return true;
        }
    }

    // ===== ENVÍO DEL FORMULARIO =====
    document.getElementById('enviarFormulario').addEventListener('click', function() {
        // Primero validar el formulario
        if (!validarFormularioCompleto()) {
            return;
        }

        // Obtener el ID del paciente del campo numExpediente
        const idPaciente = document.getElementById('numExpediente').value;
        if (!idPaciente) {
            alert('Error: Debe seleccionar un paciente primero');
            return;
        }

        // Recolectar todos los datos del formulario
        const consultaData = {
            // Datos básicos
            fecha: new Date().toISOString().split('T')[0],
            observaciones: document.getElementById('observacionesHigiene').value,
            
            // IDs del paciente y estudiante
            idPaciente: parseInt(idPaciente),
            idEstudiante: parseInt(sessionStorage.getItem('estudianteId') || '1'),
            
            // Datos del Informante
            informanteNombres: document.getElementById('nombres_pesona').value,
            informanteApellidoPaterno: document.getElementById('apellidoPaterno_persona').value,
            informanteApellidoMaterno: document.getElementById('apellidoMaterno_persona').value,
            informanteDireccion: document.getElementById('direccion_persona').value,
            informanteTelefono: document.getElementById('telefono_persona').value,
            
            // Datos de PatologiaPersonal - CORREGIDOS (usando booleanos)
            nombrePatologia: obtenerEnfermedadesSeleccionadas(),
            alergias: obtenerValorBooleano(document.getElementById('alergias-si'), document.getElementById('alergias-no')),
            especifiqueAlergia: document.getElementById('alergias-si').checked ? 
                               document.getElementById('especifiqueAlergia').value.trim() : null,
            
            embarazo: obtenerValorBooleano(document.getElementById('embarazo-si'), document.getElementById('embarazo-no')),
            semanaEmbarazo: document.getElementById('embarazo-si').checked && document.getElementById('semanasEmbarazo').value ? 
                           parseInt(document.getElementById('semanasEmbarazo').value) : null,
            
            // Datos de TratamientoMedico - CORREGIDOS (usando booleanos)
            tratamientoMedico: document.getElementById('tratamientoMedico').value !== '',
            tratamientoMedicoDetalle: document.getElementById('tratamientoMedico').value,
            recibeAlgunMedicamento: document.getElementById('medicamentoActual').value !== '',
            medicamentoActual: document.getElementById('medicamentoActual').value,
            
            // Hemorragia - CORREGIDO (usando booleanos)
            tuvoHemorragiaDental: obtenerValorBooleano(document.getElementById('hemorragia-si'), document.getElementById('hemorragia-no')),
            especifiqueHemorragia: document.getElementById('hemorragia-si').checked ? 
                                  document.getElementById('especifiqueHemorragia').value.trim() : null,
            
            // Datos de ExamenExtraOral - CORREGIDOS
            atm: document.getElementById('atm').value,
            gangliosLinfaticos: document.getElementById('ganglios').value,
            respirador: obtenerRespiradorSeleccionado(),
            otrosRespiratorio: document.getElementById('otrosRespiratorio').value,
            
            // Datos de ExamenIntraOral - CORREGIDOS (usando booleanos)
            labios: document.getElementById('labios').value,
            lengua: document.getElementById('lengua').value,
            paladar: document.getElementById('paladar').value,
            pisoDeLaBoca: document.getElementById('pisoBoca').value,
            mucosaYugal: document.getElementById('mucosaYugal').value,
            encias: document.getElementById('encias').value,
            utilizaProtesisDental: obtenerValorBooleano(document.getElementById('protesis-si'), document.getElementById('protesis-no')),
            tipoProtesis: document.getElementById('protesis-si').checked ? document.getElementById('tipoProtesis').value : null,
            tiempoProtesis: document.getElementById('protesis-si').checked ? document.getElementById('tiempoProtesis').value : null,
            
            // Datos de AntecedentesBucodentales - CORREGIDOS (usando booleanos)
            fechaRevision: document.getElementById('ultimaVisita').value,
            habitoFuma: document.getElementById('fuma').checked,
            habitoBebe: document.getElementById('bebe').checked,
            habitoCoca: document.getElementById('coca').checked,
            otrosHabitos: document.getElementById('otrosHabitos').value,
            
            // Datos de AntecedentesHigieneOral - CORREGIDOS (usando booleanos)
            utilizaCepilloDental: obtenerValorBooleano(document.getElementById('cepillo-si'), document.getElementById('cepillo-no')),
            utilizaHiloDental: obtenerValorBooleano(document.getElementById('hilo-si'), document.getElementById('hilo-no')),
            utilizaEnjuagueBucal: obtenerValorBooleano(document.getElementById('enjuague-si'), document.getElementById('enjuague-no')),
            frecuenciaCepillo: document.getElementById('frecuenciaCepillado').value,
            sangradoEncias: obtenerValorBooleano(document.getElementById('sangrado-si'), document.getElementById('sangrado-no')),
            higieneBucal: obtenerHigieneBucalSeleccionada(),
            observacionesHigiene: document.getElementById('observacionesHigiene').value
        };

        // Enviar datos al backend
        const btnEnviar = document.getElementById('enviarFormulario');
        btnEnviar.disabled = true;
        btnEnviar.textContent = 'Guardando...';

        console.log('Enviando datos de consulta:', consultaData);

        fetch('/api/consultas/completa', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify(consultaData)
        })
        .then(response => {
            if (!response.ok) {
                return response.text().then(text => {
                    throw new Error(text || `Error ${response.status}: ${response.statusText}`);
                });
            }
            return response.json();
        })
        .then(data => {
            console.log('Consulta guardada:', data);
            alert('Historia clínica guardada exitosamente!');
            // Opcional: limpiar formulario o redirigir
            // window.location.reload();
        })
        .catch((error) => {
            console.error('Error al guardar:', error);
            alert('Error al guardar la historia clínica: ' + error.message);
        })
        .finally(() => {
            btnEnviar.disabled = false;
            btnEnviar.textContent = 'Guardar Historia Clínica';
        });
    });

    // Inicializar estado de los campos al cargar la página
    actualizarValidacionEnfermedades();
});