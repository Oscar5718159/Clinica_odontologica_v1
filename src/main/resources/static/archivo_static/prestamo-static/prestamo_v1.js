// URL para búsqueda de pacientes
const API_BASE_URL = 'http://localhost:8080/api/pacientes/public';

// URL base para gestión de archivos 
const API_ARCHIVOS_URL = 'http://localhost:8080/api/archivos';

// URL base para gestión de estudiantes
const API_ESTUDIANTES_URL = 'http://localhost:8080/api/estudiantes';

document.addEventListener('DOMContentLoaded', function() {
    // Elementos de búsqueda de pacientes
    const nombreBusqueda = document.getElementById('nombreBusqueda');
    const ciBusqueda = document.getElementById('ciBusqueda');
    const btnBuscarPaciente = document.getElementById('btnBuscarPaciente');
    const resultadosBusquedaPaciente = document.getElementById('resultadosBusquedaPaciente');
    const listaResultadosPaciente = document.getElementById('listaResultadosPaciente');
    
    const codigoEstudianteBusqueda = document.getElementById('codigoEstudianteBusqueda');
    const btnBuscarEstudiante = document.getElementById('btnBuscarEstudiante');
    const resultadosBusquedaEstudiante = document.getElementById('resultadosBusquedaEstudiante');
    const listaResultadosEstudiante = document.getElementById('listaResultadosEstudiante');

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
    
    // ===== MODIFICAR INTERFAZ PARA AÑADIR BOTÓN DE ARCHIVOS =====
    function modificarInterfaz() {
        const searchForm = document.querySelector('.search-form');
        
        const archivoBtn = document.createElement('div');
        archivoBtn.className = 'form-group';
        archivoBtn.innerHTML = `
            <button type="button" class="search-btn secondary" id="btnBuscarArchivos">
                <i class="fas fa-folder-open"></i> BUSCAR ARCHIVOS EXISTENTES
            </button>
        `;
        
        searchForm.appendChild(archivoBtn);
        
        // Event listener para el nuevo botón
        document.getElementById('btnBuscarArchivos').addEventListener('click', buscarArchivos);
    }
    
    // Llamar a la función para modificar la interfaz
    modificarInterfaz();
    
    // ===== BÚSQUEDA DE PACIENTES =====
    async function buscarPacientes() {
        const nombre = nombreBusqueda.value.trim();
        const ci = ciBusqueda.value.trim();

        // Validar que al menos un campo tenga datos
        if (!nombre && !ci) {
            showNotification('warning', 'Por favor, ingrese un nombre o CI para buscar.');
            return;
        }

        listaResultadosPaciente.innerHTML = '<div class="loading">Buscando pacientes...</div>';
        resultadosBusquedaPaciente.style.display = 'block';
        btnBuscarPaciente.disabled = true;
        btnBuscarPaciente.textContent = 'BUSCANDO...';

        try {
            let url;
            
            // BÚSQUEDA SEPARADA: Si hay CI, buscar solo por CI
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
            listaResultadosPaciente.innerHTML = '<div class="no-results">Error al realizar la búsqueda. Verifique la conexión.</div>';
            showNotification('error', 'Error al buscar pacientes.');
        } finally {
            btnBuscarPaciente.disabled = false;
            btnBuscarPaciente.textContent = 'BUSCAR';
        }
    }
    
    // ===== FUNCIÓN PARA MOSTRAR RESULTADOS DE PACIENTES =====
    function mostrarResultados(pacientes) {
        listaResultadosPaciente.innerHTML = '';
        
        if (!pacientes || pacientes.length === 0) {
            listaResultadosPaciente.innerHTML = '<div class="no-results">No se encontraron pacientes con los criterios de búsqueda.</div>';
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
                listaResultadosPaciente.appendChild(item);
            });
        }
        
        resultadosBusquedaPaciente.style.display = 'block';
    }
    
    // ===== FUNCIÓN PARA SELECCIONAR PACIENTE =====
    async function seleccionarPaciente(paciente) {
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
        resultadosBusquedaPaciente.style.display = 'none';

        // Limpiar campos de búsqueda
        nombreBusqueda.value = '';
        ciBusqueda.value = '';

        // Buscar automáticamente el archivo del paciente
        await obtenerIdArchivoDelPaciente(paciente);

        // Desplazar al formulario principal
        document.querySelector('.form-section').scrollIntoView({ behavior: 'smooth' });
    }
    
    // ===== FUNCIÓN PARA OBTENER ID DEL ARCHIVO DEL PACIENTE =====
    async function obtenerIdArchivoDelPaciente(paciente) {
        try {
            const ci = paciente.ci;
            const nombreCompleto = `${paciente.persona?.nombre || ''} ${paciente.persona?.apellidoPaterno || ''} ${paciente.persona?.apellidoMaterno || ''}`.trim();
            
            // Preparar parámetros
            const params = new URLSearchParams();
            if (ci) params.append('ci', ci);
            if (nombreCompleto) params.append('nombre', nombreCompleto);
            
            // URL para obtener solo el ID del archivo
            const url = `${API_ARCHIVOS_URL}/id-por-paciente?${params.toString()}`;
            
            console.log('Buscando ID de archivo para paciente:', { ci, nombreCompleto });
            
            const response = await fetch(url);
            
            if (!response.ok) {
                throw new Error(`Error HTTP: ${response.status}`);
            }
            
            const result = await response.json();
            console.log('Resultado de búsqueda de ID de archivo:', result);
            
            if (result.success && result.found && result.idArchivo) {
                // Guardar el ID del archivo en el campo oculto
                const idArchivo = result.idArchivo;
                document.getElementById('idArchivo').value = idArchivo;
                
                showNotification('success', 
                    `✅ Archivo encontrado automáticamente<br>
                     <small>ID del Archivo: ${idArchivo}</small>`, 
                    'Archivo asignado'
                );
                
                return idArchivo;
                
            } else {
                showNotification('warning', 
                    `⚠️ No se encontró archivo para este paciente<br>
                     <small>CI: ${ci || 'No especificado'}</small><br>
                     <small>Nombre: ${nombreCompleto || 'No especificado'}</small><br>
                     <small>Debe crear un archivo primero.</small>`, 
                    'Archivo no encontrado'
                );
                
                // Resetear el campo ID del archivo
                document.getElementById('idArchivo').value = '';
                
                // Mostrar opción para crear archivo
                mostrarOpcionCrearArchivo(paciente);
                return null;
            }
            
        } catch (error) {
            console.error('Error al buscar ID del archivo:', error);
            showNotification('error', 
                `❌ Error al buscar archivo del paciente<br>
                 <small>${error.message}</small>`, 
                'Error en la búsqueda'
            );
            
            document.getElementById('idArchivo').value = '';
            return null;
        }
    }
    
    // ===== FUNCIÓN PARA MOSTRAR OPCIÓN DE CREAR ARCHIVO =====
    function mostrarOpcionCrearArchivo(paciente) {
        // Verificar si ya existe un mensaje
        if (document.getElementById('mensajeCrearArchivo')) {
            return;
        }
        
        const mensajeDiv = document.createElement('div');
        mensajeDiv.id = 'mensajeCrearArchivo';
        mensajeDiv.className = 'alert alert-warning';
        mensajeDiv.style.marginTop = '10px';
        mensajeDiv.style.padding = '10px';
        mensajeDiv.style.borderRadius = '5px';
        mensajeDiv.style.border = '1px solid #ffc107';
        mensajeDiv.innerHTML = `
            <div style="display: flex; justify-content: space-between; align-items: center;">
                <div>
                    <strong><i class="fas fa-exclamation-triangle"></i> Paciente sin archivo</strong>
                    <p style="margin: 5px 0 0 0; font-size: 14px;">
                        El paciente seleccionado no tiene un archivo creado en el sistema.
                    </p>
                </div>
                <div style="display: flex; gap: 10px;">
                    <button id="btnCrearArchivo" class="btn btn-sm btn-primary">
                        <i class="fas fa-plus"></i> Crear Archivo
                    </button>
                    <button id="btnIgnorarArchivo" class="btn btn-sm btn-secondary">
                        <i class="fas fa-times"></i> Ignorar
                    </button>
                </div>
            </div>
        `;
        
        // Insertar después del formulario de paciente
        const pacienteSection = document.querySelector('.form-section');
        if (pacienteSection) {
            pacienteSection.insertBefore(mensajeDiv, pacienteSection.firstChild);
        }
        
        // Event listener para crear archivo
        document.getElementById('btnCrearArchivo').addEventListener('click', async () => {
            await crearArchivoParaPaciente(paciente);
            mensajeDiv.remove();
        });
        
        // Event listener para ignorar
        document.getElementById('btnIgnorarArchivo').addEventListener('click', () => {
            mensajeDiv.remove();
            showNotification('info', 
                'Continuando sin archivo. Recuerde crear uno para el paciente más tarde.', 
                'Aviso'
            );
        });
    }
    
    // ===== FUNCIÓN PARA CREAR ARCHIVO =====
    async function crearArchivoParaPaciente(paciente) {
        try {
            showNotification('info', 'Creando archivo para el paciente...', 'Creando archivo');
            
            const archivoData = {
                paciente: { idPaciente: paciente.idPaciente || paciente.id },
                codigoArchivo: `ARCH-${paciente.ci || paciente.idPaciente || 'NOC'}-${Date.now().toString().slice(-6)}`,
                estado: 'ACTIVO',
                fechaCreacion: new Date().toISOString().split('T')[0]
            };
            
            console.log('Creando archivo con datos:', archivoData);
            
            const response = await fetch(API_ARCHIVOS_URL, {
                method: 'POST',
                headers: { 
                    'Content-Type': 'application/json',
                    'Accept': 'application/json'
                },
                body: JSON.stringify(archivoData)
            });
            
            if (response.ok) {
                const archivoCreado = await response.json();
                const idArchivo = archivoCreado.idArchivo || archivoCreado.id;
                document.getElementById('idArchivo').value = idArchivo;
                
                showNotification('success', 
                    `✅ Archivo creado exitosamente<br>
                     <small>ID: ${idArchivo}</small><br>
                     <small>Código: ${archivoCreado.codigoArchivo}</small>`, 
                    'Archivo creado'
                );
                
                return idArchivo;
            } else {
                const errorText = await response.text();
                throw new Error(`Error ${response.status}: ${errorText}`);
            }
        } catch (error) {
            console.error('Error al crear archivo:', error);
            showNotification('error', 
                `❌ Error al crear archivo<br>
                 <small>${error.message}</small>`, 
                'Error al crear archivo'
            );
            return null;
        }
    }
    
    // ===== EVENT LISTENERS PARA BÚSQUEDA DE PACIENTES =====
    btnBuscarPaciente.addEventListener('click', buscarPacientes);
    
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
    
    // ===== BÚSQUEDA DE ESTUDIANTES =====
    async function buscarEstudiante() {
        const codigo = codigoEstudianteBusqueda.value.trim();
        
        if (!codigo) {
            showNotification('warning', 'Por favor, ingrese el código del estudiante.');
            return;
        }
        
        // Validar que sea un número
        if (isNaN(codigo)) {
            showNotification('error', 'El código del estudiante debe ser numérico.');
            return;
        }
        
        // Mostrar loading
        listaResultadosEstudiante.innerHTML = '<div class="loading">Buscando estudiante...</div>';
        resultadosBusquedaEstudiante.style.display = 'block';
        btnBuscarEstudiante.disabled = true;
        btnBuscarEstudiante.textContent = 'BUSCANDO...';
        
        try {
            // Construir URL para buscar estudiante
            const url = `${API_ESTUDIANTES_URL}/buscar-por-codigo?codigo=${encodeURIComponent(codigo)}`;
            
            console.log('Buscando estudiante con URL:', url);
            
            const response = await fetch(url);
            
            if (!response.ok) {
                throw new Error(`Error en la búsqueda: ${response.status}`);
            }
            
            const estudiantes = await response.json();
            console.log('Estudiantes encontrados:', estudiantes);
            
            if (estudiantes.length === 0) {
                listaResultadosEstudiante.innerHTML = '<div class="no-results">No se encontró ningún estudiante con el código ingresado.</div>';
                showNotification('info', 'No se encontró estudiante con ese código.');
            } else {
                mostrarResultadosEstudiantes(estudiantes);
            }
            
        } catch (error) {
            console.error('Error al buscar estudiante:', error);
            listaResultadosEstudiante.innerHTML = '<div class="no-results">Error al realizar la búsqueda. Verifique la conexión.</div>';
            showNotification('error', 'Error al buscar estudiante.');
        } finally {
            btnBuscarEstudiante.disabled = false;
            btnBuscarEstudiante.textContent = 'BUSCAR ESTUDIANTE';
        }
    }
    
    // ===== FUNCIÓN PARA MOSTRAR RESULTADOS DE ESTUDIANTES =====
    function mostrarResultadosEstudiantes(estudiantes) {
        listaResultadosEstudiante.innerHTML = '';
        
        estudiantes.forEach(estudiante => {
            const item = document.createElement('div');
            item.className = 'result-item';
            
            // Obtener información del usuario asociado al estudiante
            const usuario = estudiante.usuario || {};
            const nombreCompleto = `${usuario.nombre || ''} ${usuario.apellido || ''}`.trim();
            
            item.innerHTML = `
                <div class="estudiante-resultado">
                    <div class="estudiante-header">
                        <strong>🎓 Estudiante #${estudiante.idEstudiante || estudiante.id}</strong>
                        <span class="estudiante-codigo">Código: ${estudiante.codigoEstudiante || 'N/A'}</span>
                    </div>
                    <div class="estudiante-info">
                        <div><small><strong>Nombre:</strong> ${nombreCompleto || 'No especificado'}</small></div>
                        <div><small><strong>Gestión:</strong> ${estudiante.gestion || 'N/A'}</small></div>
                        <div><small><strong>Estado:</strong> ${estudiante.bloqueado ? '❌ Bloqueado' : '✅ Activo'}</small></div>
                    </div>
                    <div class="estudiante-acciones">
                        <button class="btn-seleccionar-estudiante" data-id="${estudiante.idEstudiante || estudiante.id}">
                            <i class="fas fa-check"></i> Seleccionar Estudiante
                        </button>
                    </div>
                </div>
            `;
            
            // Agregar evento para seleccionar estudiante
            const btnSeleccionar = item.querySelector('.btn-seleccionar-estudiante');
            btnSeleccionar.addEventListener('click', (e) => {
                e.stopPropagation();
                seleccionarEstudiante(estudiante);
            });
            
            // También permitir selección haciendo clic en todo el item
            item.addEventListener('click', () => {
                seleccionarEstudiante(estudiante);
            });
            
            listaResultadosEstudiante.appendChild(item);
        });
        
        resultadosBusquedaEstudiante.style.display = 'block';
    }
    
    // ===== FUNCIÓN PARA SELECCIONAR ESTUDIANTE =====
    function seleccionarEstudiante(estudiante) {
        // Guardar el ID del estudiante en un campo oculto
        const estudianteId = estudiante.idEstudiante || estudiante.id;
        document.getElementById('idEstudiante').value = estudianteId;
        
        // También puedes mostrar el código del estudiante en algún campo visible
        if (document.getElementById('codigoEstudianteBusqueda')) {
            document.getElementById('codigoEstudianteBusqueda').value = estudiante.codigoEstudiante || '';
        }
        
        // Ocultar resultados
        resultadosBusquedaEstudiante.style.display = 'none';
        
        showNotification('success', 
            `Estudiante "${estudiante.codigoEstudiante}" seleccionado.<br>
             ID: ${estudianteId}`, 
            'Estudiante seleccionado'
        );
        
        // Desplazar al formulario principal
        document.querySelector('.form-section').scrollIntoView({ behavior: 'smooth' });
    }
    
    // ===== EVENT LISTENERS PARA BÚSQUEDA DE ESTUDIANTES =====
    if (btnBuscarEstudiante) {
        btnBuscarEstudiante.addEventListener('click', buscarEstudiante);
        
        // Permitir búsqueda con Enter
        codigoEstudianteBusqueda.addEventListener('keypress', function(e) {
            if (e.key === 'Enter') {
                buscarEstudiante();
            }
        });
    }
    
    // ===== FUNCIÓN PARA BUSCAR ARCHIVOS =====
    async function buscarArchivos() {
        const ci = ciBusqueda.value.trim();
        const nombre = nombreBusqueda.value.trim();
        
        if (!ci && !nombre) {
            showNotification('warning', 'Ingrese CI o nombre para buscar archivos.');
            return;
        }
        
        // Mostrar loading
        listaResultados.innerHTML = '<div class="loading">Buscando archivos...</div>';
        resultadosBusqueda.style.display = 'block';
        
        try {
            // Usar el endpoint que acepta ambos parámetros
            let url = `${API_ARCHIVOS_URL}/buscar-por-paciente?`;
            const params = [];
            
            if (ci) params.push(`ci=${encodeURIComponent(ci)}`);
            if (nombre) params.push(`nombre=${encodeURIComponent(nombre)}`);
            
            url += params.join('&');
            
            console.log('Buscando archivos con URL:', url);
            
            const response = await fetch(url);
            if (!response.ok) throw new Error('Error en la búsqueda');
            
            const result = await response.json();
            
            if (result.success && result.archivos) {
                const archivos = result.archivos;
                console.log('Archivos encontrados:', archivos);
                
                if (archivos.length === 0) {
                    listaResultados.innerHTML = '<div class="no-results">No se encontraron archivos para los criterios de búsqueda.</div>';
                    showNotification('info', 'No se encontraron archivos. Puede crear uno nuevo.');
                } else {
                    mostrarResultadosArchivos(archivos);
                }
            } else {
                showNotification('error', result.message || 'Error al buscar archivos.');
                listaResultados.innerHTML = '<div class="no-results">Error en la búsqueda.</div>';
            }
            
        } catch (error) {
            console.error('Error:', error);
            listaResultados.innerHTML = '<div class="no-results">Error al buscar archivos. Verifique la conexión.</div>';
            showNotification('error', 'Error al buscar archivos.');
        }
    }
    
    // ===== FUNCIÓN PARA MOSTRAR RESULTADOS DE ARCHIVOS =====
    function mostrarResultadosArchivos(archivos) {
        listaResultados.innerHTML = '';
        
        archivos.forEach(archivo => {
            const item = document.createElement('div');
            item.className = 'result-item';
            
            const paciente = archivo.paciente || {};
            const persona = paciente.persona || {};
            const nombreCompleto = `${persona.nombre || ''} ${persona.apellidoPaterno || ''} ${persona.apellidoMaterno || ''}`.trim();
            
            item.innerHTML = `
                <div class="archivo-resultado">
                    <div class="archivo-header">
                        <strong>📁 Archivo #${archivo.idArchivo || archivo.id}</strong>
                        <span class="archivo-estado ${archivo.estado === 'ACTIVO' ? 'activo' : 'inactivo'}">
                            ${archivo.estado || 'ACTIVO'}
                        </span>
                    </div>
                    <div class="archivo-info">
                        <div><small><strong>Paciente:</strong> ${nombreCompleto || 'No especificado'}</small></div>
                        <div><small><strong>CI:</strong> ${paciente.ci || 'N/A'}</small></div>
                        <div><small><strong>Código:</strong> ${archivo.codigoArchivo || 'N/A'}</small></div>
                    </div>
                    <div class="archivo-acciones">
                        <button class="btn-seleccionar-archivo" data-id="${archivo.idArchivo || archivo.id}">
                            <i class="fas fa-check"></i> Seleccionar Archivo
                        </button>
                    </div>
                </div>
            `;
            
            // Agregar evento para seleccionar archivo
            const btnSeleccionar = item.querySelector('.btn-seleccionar-archivo');
            btnSeleccionar.addEventListener('click', (e) => {
                e.stopPropagation();
                seleccionarArchivo(archivo);
            });
            
            // También permitir selección haciendo clic en todo el item
            item.addEventListener('click', () => {
                seleccionarArchivo(archivo);
            });
            
            listaResultados.appendChild(item);
        });
        
        resultadosBusqueda.style.display = 'block';
    }
    
    // ===== FUNCIÓN PARA SELECCIONAR ARCHIVO =====
    function seleccionarArchivo(archivo) {
        const archivoId = archivo.idArchivo || archivo.id;
        document.getElementById('idArchivo').value = archivoId;
        
        // Ocultar resultados
        resultadosBusqueda.style.display = 'none';
        
        const paciente = archivo.paciente || {};
        const persona = paciente.persona || {};
        const nombreCompleto = `${persona.nombre || ''} ${persona.apellidoPaterno || ''} ${persona.apellidoMaterno || ''}`.trim();
        
        showNotification('success', 
            `📁 Archivo seleccionado:<br>
             <small>ID: ${archivoId}</small><br>
             <small>Paciente: ${nombreCompleto}</small><br>
             <small>Código: ${archivo.codigoArchivo || 'N/A'}</small>`, 
            'Archivo seleccionado'
        );
    }
    
    // ===== FUNCIÓN DE ENVÍO PARA GUARDAR PRÉSTAMO (CORREGIDA) =====
    document.getElementById('enviarFormulario').addEventListener('click', async function() {
        if (!validarFormularioPrestamo()) {
            return;
        }

        const idPaciente = document.getElementById('numExpediente').value;
        const idEstudiante = document.getElementById('idEstudiante').value;
        const idArchivo = document.getElementById('idArchivo').value;
        const fechaLimitePrestamo = document.getElementById('fechaLimitePrestamo').value;
        const tipoPrestamo = document.getElementById('tipoPrestamo').value;
        const encargadoPrestamo = document.getElementById('encargadoPrestamo').value.trim();
        const motivoPrestamo = document.getElementById('motivoPrestamo') ? document.getElementById('motivoPrestamo').value.trim() : '';

        try {
            // ✅ SOLUCIÓN: Usar la fecha actual en formato local YYYY-MM-DD
            // sin pasar por el objeto Date que causa problemas de zona horaria
            const fechaActual = new Date();
            
            // Obtener la fecha en la zona horaria local del usuario
            const año = fechaActual.getFullYear();
            const mes = String(fechaActual.getMonth() + 1).padStart(2, '0');
            const dia = String(fechaActual.getDate()).padStart(2, '0');
            
            // Formato YYYY-MM-DD que el backend espera
            const fechaPrestamoFormateada = `${año}-${mes}-${dia}`;

            // IMPORTANTE: Verificar que la fecha límite no sea menor que la fecha actual
            if (fechaLimitePrestamo < fechaPrestamoFormateada) {
                showNotification('warning', 
                    'La fecha límite no puede ser menor que la fecha actual',
                    'Validación de fechas'
                );
                return;
            }

            // Crear objeto PrestamoActual con fechas correctamente formateadas
            const prestamoData = {
                idArchivo: parseInt(idArchivo),
                idEstudiante: parseInt(idEstudiante),
                fechaLimitePrestamo: fechaLimitePrestamo, // Viene del input date (YYYY-MM-DD)
                fechaPrestamo: fechaPrestamoFormateada, // ✅ Usar fecha local formateada manualmente
                tipoPrestamo: tipoPrestamo,
                encargadoPrestamo: encargadoPrestamo,
                motivoPrestamo: motivoPrestamo || null,
                estadoPrestamo: 'ACTIVO'
            };

            console.log('Fechas enviadas:', {
                fechaLocalActual: fechaPrestamoFormateada,
                fechaLimite: fechaLimitePrestamo,
                fechaActualObjeto: fechaActual.toString(),
                fechaActualISO: fechaActual.toISOString().split('T')[0] // ESTA DARÍA UN DÍA MENOS
            });

            const url = 'http://localhost:8080/api/prestamos-actuales';
            
            const response = await fetch(url, {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify(prestamoData)
            });

            if (response.ok) {
                const prestamoGuardado = await response.json();
                showNotification('success', 
                    `✅ <strong>Préstamo registrado exitosamente</strong><br>
                    <small>ID del Préstamo: ${prestamoGuardado.idPrestamo || prestamoGuardado.id}</small><br>
                    <small>Fecha préstamo: ${prestamoGuardado.fechaPrestamo}</small><br>
                    <small>Fecha límite: ${prestamoGuardado.fechaLimitePrestamo}</small>`,
                    'Préstamo registrado'
                );
                
                limpiarFormularioPrestamo();
                    
            } else {
                const errorText = await response.text();
                console.error('Error del servidor:', errorText);
                showNotification('error',
                    `⚠️ <strong>Error al registrar el préstamo</strong><br>
                    <small>${errorText || 'Error desconocido'}</small>`,
                    'Error en la operación'
                );
            }   
        } catch (error) {
            console.error('Error en la solicitud:', error);
            showNotification('error',
                `⚠️ <strong>Error en la solicitud</strong><br>
                <small>${error.message}</small>`,
                'Error en la operación'
            );
        }
    });

    // ===== FUNCIÓN PARA VALIDAR FORMULARIO DE PRÉSTAMO =====
    function validarFormularioPrestamo() {
        // Verificar que se haya seleccionado un paciente
        const idPaciente = document.getElementById('numExpediente').value;
        if (!idPaciente) {
            showNotification('error', 'Debe seleccionar un paciente primero');
            return false;
        }

        // Verificar que se haya seleccionado un estudiante
        const idEstudiante = document.getElementById('idEstudiante').value;
        if (!idEstudiante) {
            showNotification('error', 'Debe seleccionar un estudiante primero');
            return false;
        }

        // Verificar que se tenga un ID de archivo
        const idArchivo = document.getElementById('idArchivo').value;
        if (!idArchivo) {
            showNotification('error', 
                'El paciente no tiene archivo. Por favor:<br>' +
                '1. Cree un archivo para el paciente<br>' +
                '2. O busque archivos existentes',
                'Archivo requerido'
            );
            return false;
        }

        // Validar campos del préstamo
        const fechaLimitePrestamo = document.getElementById('fechaLimitePrestamo').value;
        const tipoPrestamo = document.getElementById('tipoPrestamo').value;
        const encargadoPrestamo = document.getElementById('encargadoPrestamo').value.trim();

        if (!fechaLimitePrestamo) {
            showNotification('error', 'La fecha límite del préstamo es obligatoria');
            return false;
        }

        if (!tipoPrestamo) {
            showNotification('error', 'El tipo de préstamo es obligatorio');
            return false;
        }

        if (!encargadoPrestamo) {
            showNotification('error', 'El encargado del préstamo es obligatorio');
            return false;
        }

        return true;
    }

    // ===== FUNCIÓN PARA LIMPIAR FORMULARIO =====
    function limpiarFormularioPrestamo() {
        // Limpiar campos del préstamo
        document.getElementById('fechaLimitePrestamo').value = '';
        document.getElementById('tipoPrestamo').value = '';
        document.getElementById('encargadoPrestamo').value = '';
        document.getElementById('motivoPrestamo').value = '';
        
        // Limpiar selecciones
        document.getElementById('idEstudiante').value = '';
        document.getElementById('idArchivo').value = '';
        document.getElementById('codigoEstudianteBusqueda').value = '';
        
        // Opcional: limpiar datos del paciente si quieres
        // document.getElementById('numExpediente').value = '';
        // document.getElementById('ciPaciente').value = '';
        // ... etc
    }

    // ===== FUNCIÓN PARA OCULTAR RESULTADOS AL HACER CLIC FUERA =====
    document.addEventListener('click', function(e) {
        if (!resultadosBusqueda.contains(e.target) && 
            e.target !== nombreBusqueda && 
            e.target !== ciBusqueda && 
            e.target !== btnBuscarPaciente &&
            e.target !== document.getElementById('btnBuscarArchivos') &&
            e.target !== codigoEstudianteBusqueda &&
            e.target !== btnBuscarEstudiante) {
            resultadosBusqueda.style.display = 'none';
        }
        
        // También ocultar resultados de pacientes
        if (!resultadosBusquedaPaciente.contains(e.target) && 
            e.target !== nombreBusqueda && 
            e.target !== ciBusqueda && 
            e.target !== btnBuscarPaciente) {
            resultadosBusquedaPaciente.style.display = 'none';
        }
        
        // También ocultar resultados de estudiantes
        if (!resultadosBusquedaEstudiante.contains(e.target) && 
            e.target !== codigoEstudianteBusqueda && 
            e.target !== btnBuscarEstudiante) {
            resultadosBusquedaEstudiante.style.display = 'none';
        }
    });

    // ===== FUNCIÓN PARA MOSTRAR NOTIFICACIONES =====
    function showNotification(type, message, title = '') {
        // Eliminar notificaciones anteriores
        const existingNotifications = document.querySelectorAll('.notification');
        existingNotifications.forEach(notification => {
            notification.style.animation = 'slideOut 0.3s ease-out';
            setTimeout(() => notification.remove(), 300);
        });

        // Crear nueva notificación
        const notification = document.createElement('div');
        notification.className = `notification notification-${type}`;
        
        // Iconos según el tipo
        const icons = {
            success: 'fas fa-check-circle',
            error: 'fas fa-exclamation-triangle',
            warning: 'fas fa-exclamation-circle',
            info: 'fas fa-info-circle'
        };

        notification.innerHTML = `
            <i class="${icons[type]} notification-icon"></i>
            <div class="notification-content">
                ${title ? `<strong>${title}</strong><br>` : ''}
                ${message}
            </div>
            <button class="notification-close">
                <i class="fas fa-times"></i>
            </button>
        `;

        document.body.appendChild(notification);

        // Configurar auto-cierre
        const closeBtn = notification.querySelector('.notification-close');
        closeBtn.addEventListener('click', () => {
            notification.style.animation = 'slideOut 0.3s ease-out';
            setTimeout(() => notification.remove(), 300);
        });

        // Auto-remover después de 5 segundos
        setTimeout(() => {
            if (notification.parentNode) {
                notification.style.animation = 'slideOut 0.3s ease-out';
                setTimeout(() => notification.remove(), 300);
            }
        }, 5000);
    }
});