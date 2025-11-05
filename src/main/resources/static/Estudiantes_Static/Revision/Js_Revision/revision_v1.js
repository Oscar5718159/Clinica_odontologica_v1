// URL base de tu API Spring Boot - AJUSTA SEGÚN TU CONFIGURACIÓN
const API_BASE_URL = 'http://localhost:8080/api/pacientes';

document.addEventListener('DOMContentLoaded', function() {
    // ===== VALIDACIONES ANTECEDENTES PATOLÓGICOS =====
    
    // Validación: Ninguna enfermedad vs enfermedades específicas
    const ningunaEnfermedad = document.getElementById('ningunaEnfermedad');
    const enfermedadesCheckboxes = document.querySelectorAll('.enfermedad-checkbox');
    const otrosEnfermedades = document.getElementById('otrosEnfermedades');
    const ningunoValidation = document.getElementById('ningunoValidation');

    function actualizarValidacionEnfermedades() {
        const algunaEnfermedadSeleccionada = Array.from(enfermedadesCheckboxes).some(cb => cb.checked) || otrosEnfermedades.value.trim() !== '';
        
        if (algunaEnfermedadSeleccionada && ningunaEnfermedad.checked) {
            ningunoValidation.style.display = 'block';
            ningunaEnfermedad.classList.add('error-border');
        } else {
            ningunoValidation.style.display = 'none';
            ningunaEnfermedad.classList.remove('error-border');
        }
    }

    ningunaEnfermedad.addEventListener('change', function() {
        if (this.checked) {
            // Deshabilitar todas las otras opciones
            enfermedadesCheckboxes.forEach(checkbox => {
                checkbox.checked = false;
                checkbox.classList.add('checkbox-disabled');
                checkbox.classList.remove('error-border');
            });
            otrosEnfermedades.value = '';
            otrosEnfermedades.disabled = true;
            otrosEnfermedades.classList.add('input-disabled');
            otrosEnfermedades.classList.remove('error-border');
        } else {
            // Habilitar todas las otras opciones
            enfermedadesCheckboxes.forEach(checkbox => {
                checkbox.classList.remove('checkbox-disabled');
            });
            otrosEnfermedades.disabled = false;
            otrosEnfermedades.classList.remove('input-disabled');
        }
        actualizarValidacionEnfermedades();
    });

    enfermedadesCheckboxes.forEach(checkbox => {
        checkbox.addEventListener('change', function() {
            if (this.checked) {
                ningunaEnfermedad.checked = false;
                ningunaEnfermedad.classList.remove('checkbox-disabled');
                otrosEnfermedades.disabled = false;
                otrosEnfermedades.classList.remove('input-disabled');
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
        especifiqueAlergia.classList.remove('input-disabled');
        actualizarValidacionAlergias();
    });

    alergiaNo.addEventListener('change', function() {
        especifiqueAlergia.disabled = true;
        especifiqueAlergia.value = '';
        especifiqueAlergia.classList.add('input-disabled');
        especifiqueAlergia.classList.remove('error-border');
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
        semanasEmbarazo.classList.remove('input-disabled');
        actualizarValidacionEmbarazo();
    });

    embarazoNo.addEventListener('change', function() {
        semanasEmbarazo.disabled = true;
        semanasEmbarazo.value = '';
        semanasEmbarazo.classList.add('input-disabled');
        semanasEmbarazo.classList.remove('error-border');
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
        especifiqueHemorragia.classList.remove('input-disabled');
        actualizarValidacionHemorragia();
    });

    hemorragiaNo.addEventListener('change', function() {
        especifiqueHemorragia.disabled = true;
        especifiqueHemorragia.value = '';
        especifiqueHemorragia.classList.add('input-disabled');
        especifiqueHemorragia.classList.remove('error-border');
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
        tipoProtesis.classList.remove('input-disabled');
        tiempoProtesis.classList.remove('input-disabled');
        actualizarValidacionProtesis();
    });

    protesisNo.addEventListener('change', function() {
        tipoProtesis.disabled = true;
        tiempoProtesis.disabled = true;
        tipoProtesis.value = '';
        tiempoProtesis.value = '';
        tipoProtesis.classList.add('input-disabled');
        tiempoProtesis.classList.add('input-disabled');
        tipoProtesis.classList.remove('error-border');
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

    // Modificar el evento de envío para incluir validaciones
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
            
            // Datos de PatologiaPersonal
            nombrePatologia: obtenerEnfermedadesSeleccionadas(),
            alergias: document.getElementById('alergias-si').checked,
            especifiqueAlergia: document.getElementById('alergias-si').checked ? document.getElementById('especifiqueAlergia').value : null,
            embarazo: document.getElementById('embarazo-si').checked,
            semanaEmbarazo: document.getElementById('embarazo-si').checked && document.getElementById('semanasEmbarazo').value ? 
                           parseInt(document.getElementById('semanasEmbarazo').value) : null,
            
            // Datos de TratamientoMedico
            tratamientoMedico: document.getElementById('tratamientoMedico').value !== '',
            tratamientoMedicoDetalle: document.getElementById('tratamientoMedico').value,
            recibeAlgunMedicamento: document.getElementById('medicamentoActual').value !== '',
            medicamentoActual: document.getElementById('medicamentoActual').value,
            tuvoHemorragiaDental: document.getElementById('hemorragia-si').checked,
            especifiqueHemorragia: document.getElementById('hemorragia-si').checked ? document.getElementById('especifiqueHemorragia').value : null,
            
            // Datos de ExamenExtraOral
            atm: document.getElementById('atm').value,
            gangliosLinfaticos: document.getElementById('ganglios').value,
            respirador: obtenerRespiradorSeleccionado(),
            otrosRespiratorio: document.getElementById('otrosRespiratorio').value,
            
            // Datos de ExamenIntraOral
            labios: document.getElementById('labios').value,
            lengua: document.getElementById('lengua').value,
            paladar: document.getElementById('paladar').value,
            pisoDeLaBoca: document.getElementById('pisoBoca').value,
            mucosaYugal: document.getElementById('mucosaYugal').value,
            encias: document.getElementById('encias').value,
            utilizaProtesisDental: document.getElementById('protesis-si').checked,
            tipoProtesis: document.getElementById('protesis-si').checked ? document.getElementById('tipoProtesis').value : null,
            tiempoProtesis: document.getElementById('protesis-si').checked ? document.getElementById('tiempoProtesis').value : null,
            
            // Datos de AntecedentesBucodentales
            fechaRevision: document.getElementById('ultimaVisita').value,
            habitoFuma: document.getElementById('fuma').checked,
            habitoBebe: document.getElementById('bebe').checked,
            otrosHabitos: document.getElementById('otrosHabitos').value,
            
            // Datos de AntecedentesHigieneOral
            utilizaCepilloDental: document.getElementById('cepillo-si').checked,
            utilizaHiloDental: document.getElementById('hilo-si').checked,
            utilizaEnjuagueBucal: document.getElementById('enjuague-si').checked,
            frecuenciaCepillo: document.getElementById('frecuenciaCepillado').value,
            sangradoEncias: document.getElementById('sangrado-si').checked,
            higieneBucal: obtenerHigieneBucalSeleccionada(),
            observacionesHigiene: document.getElementById('observacionesHigiene').value
        };

        // Función auxiliar para obtener enfermedades seleccionadas
        function obtenerEnfermedadesSeleccionadas() {
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
            
            const otros = document.getElementById('otrosEnfermedades').value;
            if (otros) enfermedades.push(otros);
            
            return enfermedades.join(', ');
        }

        function obtenerRespiradorSeleccionado() {
            if (document.getElementById('resp-nasal').checked) return 'Nasal';
            if (document.getElementById('resp-bucal').checked) return 'Bucal';
            if (document.getElementById('resp-buco-nasal').checked) return 'Buco nasal';
            return '';
        }

        function obtenerHigieneBucalSeleccionada() {
            if (document.getElementById('higiene-buena').checked) return 'Buena';
            if (document.getElementById('higiene-regular').checked) return 'Regular';
            if (document.getElementById('higiene-mala').checked) return 'Mala';
            return '';
        }

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
            //window.location.reload();
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
});