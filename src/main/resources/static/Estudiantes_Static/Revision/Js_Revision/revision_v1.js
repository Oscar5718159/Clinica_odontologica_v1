        // URL base de tu API Spring Boot - AJUSTA SEGÚN TU CONFIGURACIÓN
        const API_BASE_URL = 'http://localhost:8080/api/pacientes';
        
        document.addEventListener('DOMContentLoaded', function() {
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
            
            // ========== FUNCIONALIDAD DEL ODONTOGRAMA ==========
            
            const dentalChart = document.getElementById('dentalChart');
            const diagnosticCriteria = document.getElementById('diagnosticCriteria');
            const clearAllBtn = document.getElementById('clearAll');
            const saveOdontogramaBtn = document.getElementById('saveOdontograma');
            const currentSelection = document.getElementById('currentSelection');
            const areaSelector = document.getElementById('areaSelector');
            const totalTeethInput = document.getElementById('totalTeeth');
            
            // Elementos CPO-D
            const cValue = document.getElementById('cValue');
            const pValue = document.getElementById('pValue');
            const oValue = document.getElementById('oValue');
            const dValue = document.getElementById('dValue');
            const cpodValue = document.getElementById('cpodValue');
            
            let selectedTooth = null;
            let selectedArea = null;
            let selectedState = null;
            
            // Generar todos los dientes automáticamente
            function generateTeeth() {
                // Dientes superiores (18-11, 21-28)
                const upperTeeth = [18, 17, 16, 15, 14, 13, 12, 11, 21, 22, 23, 24, 25, 26, 27, 28];
                // Dientes inferiores (48-41, 31-38)
                const lowerTeeth = [48, 47, 46, 45, 44, 43, 42, 41, 31, 32, 33, 34, 35, 36, 37, 38];
                
                // Añadir etiqueta para maxilar superior
                const upperLabel = document.createElement('div');
                upperLabel.className = 'jaw-label';
                upperLabel.textContent = 'MAXILAR SUPERIOR';
                dentalChart.appendChild(upperLabel);
                
                // Generar dientes superiores
                upperTeeth.forEach(toothNumber => {
                    createToothElement(toothNumber);
                });
                
                // Añadir etiqueta para mandíbula inferior
                const lowerLabel = document.createElement('div');
                lowerLabel.className = 'jaw-label';
                lowerLabel.textContent = 'MANDÍBULA INFERIOR';
                dentalChart.appendChild(lowerLabel);
                
                // Generar dientes inferiores
                lowerTeeth.forEach(toothNumber => {
                    createToothElement(toothNumber);
                });
            }
            
            // Crear elemento de diente individual
            function createToothElement(toothNumber) {
                const toothContainer = document.createElement('div');
                toothContainer.className = 'tooth-container';
                
                const toothNumberElement = document.createElement('div');
                toothNumberElement.className = 'tooth-number';
                toothNumberElement.textContent = toothNumber;
                
                const toothElement = document.createElement('div');
                toothElement.className = 'tooth';
                toothElement.dataset.tooth = toothNumber;
                
                const svgElement = document.createElementNS('http://www.w3.org/2000/svg', 'svg');
                svgElement.setAttribute('viewBox', '0 0 100 100');
                svgElement.setAttribute('width', '100%');
                svgElement.setAttribute('height', '100%');
                
                // Contorno del diente
                const outline = document.createElementNS('http://www.w3.org/2000/svg', 'rect');
                outline.setAttribute('x', '5');
                outline.setAttribute('y', '5');
                outline.setAttribute('width', '90');
                outline.setAttribute('height', '90');
                outline.setAttribute('stroke', 'black');
                outline.setAttribute('stroke-width', '2');
                outline.setAttribute('fill', 'none');
                
                // Área central
                const center = document.createElementNS('http://www.w3.org/2000/svg', 'rect');
                center.setAttribute('class', 'center');
                center.setAttribute('x', '35');
                center.setAttribute('y', '35');
                center.setAttribute('width', '30');
                center.setAttribute('height', '30');
                center.setAttribute('stroke', 'black');
                center.setAttribute('stroke-width', '2');
                center.setAttribute('fill', 'white');
                
                // Área izquierda
                const left = document.createElementNS('http://www.w3.org/2000/svg', 'path');
                left.setAttribute('class', 'left');
                left.setAttribute('d', 'M5,5 L35,35 L35,65 L5,95 Z');
                left.setAttribute('stroke', 'black');
                left.setAttribute('stroke-width', '1.5');
                left.setAttribute('fill', 'white');
                
                // Área derecha
                const right = document.createElementNS('http://www.w3.org/2000/svg', 'path');
                right.setAttribute('class', 'right');
                right.setAttribute('d', 'M95,5 L65,35 L65,65 L95,95 Z');
                right.setAttribute('stroke', 'black');
                right.setAttribute('stroke-width', '1.5');
                right.setAttribute('fill', 'white');
                
                // Área superior
                const top = document.createElementNS('http://www.w3.org/2000/svg', 'path');
                top.setAttribute('class', 'top');
                top.setAttribute('d', 'M5,5 L35,35 L65,35 L95,5 Z');
                top.setAttribute('stroke', 'black');
                top.setAttribute('stroke-width', '1.5');
                top.setAttribute('fill', 'white');
                
                // Área inferior
                const bottom = document.createElementNS('http://www.w3.org/2000/svg', 'path');
                bottom.setAttribute('class', 'bottom');
                bottom.setAttribute('d', 'M5,95 L35,65 L65,65 L95,95 Z');
                bottom.setAttribute('stroke', 'black');
                bottom.setAttribute('stroke-width', '1.5');
                bottom.setAttribute('fill', 'white');
                
                // Añadir todos los elementos al SVG
                svgElement.appendChild(outline);
                svgElement.appendChild(center);
                svgElement.appendChild(left);
                svgElement.appendChild(right);
                svgElement.appendChild(top);
                svgElement.appendChild(bottom);
                
                toothElement.appendChild(svgElement);
                toothContainer.appendChild(toothNumberElement);
                toothContainer.appendChild(toothElement);
                dentalChart.appendChild(toothContainer);
            }
            
            // Inicializar el odontograma
            generateTeeth();
            
            // Añadir eventos a los dientes
            document.addEventListener('click', function(e) {
                if (e.target.closest('.tooth')) {
                    const tooth = e.target.closest('.tooth');
                    
                    // Quitar selección anterior
                    document.querySelectorAll('.tooth').forEach(t => {
                        t.classList.remove('selected');
                    });
                    
                    // Seleccionar nuevo diente
                    tooth.classList.add('selected');
                    selectedTooth = tooth;
                    
                    // Mostrar selector de áreas
                    areaSelector.style.display = 'flex';
                    
                    // Resetear selección de área
                    document.querySelectorAll('.area-btn').forEach(btn => {
                        btn.classList.remove('active');
                    });
                    selectedArea = null;
                    selectedState = null;
                    
                    currentSelection.textContent = `Diente seleccionado: ${tooth.dataset.tooth}. Seleccione un área.`;
                }
            });
            
            // Añadir eventos a las áreas del selector
            document.querySelectorAll('.area-btn').forEach(areaBtn => {
                areaBtn.addEventListener('click', function() {
                    // Quitar selección anterior
                    document.querySelectorAll('.area-btn').forEach(btn => {
                        btn.classList.remove('active');
                    });
                    
                    // Seleccionar nueva área
                    this.classList.add('active');
                    selectedArea = this.dataset.area;
                    
                    currentSelection.textContent = `Diente ${selectedTooth.dataset.tooth}, área: ${selectedArea}. Seleccione un diagnóstico.`;
                });
            });
            
            // Añadir eventos a los criterios de diagnóstico
            document.querySelectorAll('.criteria-item').forEach(criteria => {
                criteria.addEventListener('click', function() {
                    if (selectedTooth && selectedArea) {
                        selectedState = this.dataset.state;
                        const svg = selectedTooth.querySelector('svg');
                        
                        // Remover todas las clases de estado del área seleccionada
                        const classesToRemove = [];
                        svg.classList.forEach(className => {
                            if (className.includes(selectedArea)) {
                                classesToRemove.push(className);
                            }
                        });
                        classesToRemove.forEach(className => {
                            svg.classList.remove(className);
                        });
                        
                        // Añadir la clase del estado seleccionado para el área específica
                        svg.classList.add(`${selectedState}-${selectedArea}`);
                        
                        currentSelection.textContent = `Diente ${selectedTooth.dataset.tooth}, área ${selectedArea} marcada como: ${this.querySelector('.criteria-label').textContent}`;
                        
                        // Actualizar valores CPO-D
                        updateCPOD();
                    } else if (!selectedTooth) {
                        alert('Por favor, seleccione un diente primero');
                    } else if (!selectedArea) {
                        alert('Por favor, seleccione un área del diente primero');
                    }
                });
            });
            
            // Función para actualizar valores CPO-D
            function updateCPOD() {
                let c = 0, p = 0, o = 0, d = 0;
                
                document.querySelectorAll('.tooth').forEach(tooth => {
                    const svg = tooth.querySelector('svg');
                    
                    // Verificar si el diente tiene alguna área cariada
                    let hasCaries = false;
                    svg.classList.forEach(className => {
                        if (className.includes('cariado-')) {
                            hasCaries = true;
                        }
                    });
                    
                    if (hasCaries) {
                        c++;
                    }
                    
                    // Aquí puedes agregar lógica adicional para P, O y D según tus criterios
                });
                
                cValue.value = c;
                pValue.value = p;
                oValue.value = o;
                dValue.value = d;
                cpodValue.value = c + p + o;
            }
            
            // Limpiar todo
            clearAllBtn.addEventListener('click', function() {
                document.querySelectorAll('.tooth').forEach(tooth => {
                    tooth.classList.remove('selected');
                    const svg = tooth.querySelector('svg');
                    
                    // Remover todas las clases de diagnóstico
                    const classesToRemove = [];
                    svg.classList.forEach(className => {
                        if (className.includes('-center') || 
                            className.includes('-left') || 
                            className.includes('-right') ||
                            className.includes('-top') ||
                            className.includes('-bottom')) {
                            classesToRemove.push(className);
                        }
                    });
                    classesToRemove.forEach(className => {
                        svg.classList.remove(className);
                    });
                });
                
                document.querySelectorAll('.area-btn').forEach(btn => {
                    btn.classList.remove('active');
                });
                
                selectedTooth = null;
                selectedArea = null;
                selectedState = null;
                areaSelector.style.display = 'none';
                currentSelection.textContent = 'Seleccione un diente y luego elija un estado';
                
                // Limpiar CPO-D
                cValue.value = '';
                pValue.value = '';
                oValue.value = '';
                dValue.value = '';
                cpodValue.value = '';
            });
            
            // Guardar odontograma
            saveOdontogramaBtn.addEventListener('click', function() {
                const teethData = {};
                document.querySelectorAll('.tooth').forEach(tooth => {
                    const toothNumber = tooth.dataset.tooth;
                    const svg = tooth.querySelector('svg');
                    
                    const areasData = {
                        center: getDiagnosisForArea(svg, 'center'),
                        left: getDiagnosisForArea(svg, 'left'),
                        right: getDiagnosisForArea(svg, 'right'),
                        top: getDiagnosisForArea(svg, 'top'),
                        bottom: getDiagnosisForArea(svg, 'bottom')
                    };
                    
                    teethData[toothNumber] = areasData;
                });
                
                alert('Odontograma guardado correctamente');
                console.log('Datos del odontograma:', teethData);
            });
            
            // Función auxiliar para obtener el diagnóstico de un área específica
            function getDiagnosisForArea(svg, area) {
                let diagnosis = null;
                svg.classList.forEach(className => {
                    if (className.endsWith(`-${area}`)) {
                        diagnosis = className.split('-')[0];
                    }
                });
                return diagnosis;
            }
            
            // Event listener para el botón de guardar historia clínica
            document.getElementById('enviarFormulario').addEventListener('click', function() {
                alert('Historia clínica guardada exitosamente');
                // Aquí puedes implementar la lógica para enviar todos los datos al backend
            });
        });