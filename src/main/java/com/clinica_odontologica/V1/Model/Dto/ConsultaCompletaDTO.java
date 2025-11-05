package com.clinica_odontologica.V1.Model.Dto;

import java.time.LocalDate;

public class ConsultaCompletaDTO {
    
    // Datos básicos de la consulta
    private LocalDate fecha;
    private String observaciones;
    
    // IDs de relaciones existentes
    private Long idPaciente;
    private Long idEstudiante;
    
    // Datos del Informante
    private String informanteNombres;
    private String informanteApellidoPaterno;
    private String informanteApellidoMaterno;
    private String informanteDireccion;
    private String informanteTelefono;
    
    // Datos de PatologiaPersonal
    private String nombrePatologia;
    private Boolean alergias;
    private String especifiqueAlergia;
    private Boolean embarazo;
    private Integer semanaEmbarazo;
    
    // Datos de TratamientoMedico
    private Boolean tratamientoMedico;
    private String tratamientoMedicoDetalle;
    private Boolean recibeAlgunMedicamento;
    private String medicamentoActual;
    private Boolean tuvoHemorragiaDental;
    private String especifiqueHemorragia;
    
    // Datos de ExamenExtraOral
    private String atm;
    private String gangliosLinfaticos;
    private String respirador;
    private String otrosRespiratorio;
    
    // Datos de ExamenIntraOral
    private String labios;
    private String lengua;
    private String paladar;
    private String pisoDeLaBoca;
    private String mucosaYugal;
    private String encias;
    private Boolean utilizaProtesisDental;
    private String tipoProtesis;
    private String tiempoProtesis;
    
    // Datos de AntecedentesBucodentales
    private LocalDate fechaRevision;
    private Boolean habitoFuma;
    private Boolean habitoBebe;
    private String otrosHabitos;
    
    // Datos de AntecedentesHigieneOral
    private Boolean utilizaCepilloDental;
    private Boolean utilizaHiloDental;
    private Boolean utilizaEnjuagueBucal;
    private String frecuenciaCepillo;
    private Boolean sangradoEncias;
    private String higieneBucal;
    private String observacionesHigiene;

    // Getters y Setters (generar todos)
    public LocalDate getFecha() { return fecha; }
    public void setFecha(LocalDate fecha) { this.fecha = fecha; }
    
    public String getObservaciones() { return observaciones; }
    public void setObservaciones(String observaciones) { this.observaciones = observaciones; }
    
    public Long getIdPaciente() { return idPaciente; }
    public void setIdPaciente(Long idPaciente) { this.idPaciente = idPaciente; }
    
    public Long getIdEstudiante() { return idEstudiante; }
    public void setIdEstudiante(Long idEstudiante) { this.idEstudiante = idEstudiante; }
    
    public String getInformanteNombres() { return informanteNombres; }
    public void setInformanteNombres(String informanteNombres) { this.informanteNombres = informanteNombres; }
    
    public String getInformanteApellidoPaterno() { return informanteApellidoPaterno; }
    public void setInformanteApellidoPaterno(String informanteApellidoPaterno) { this.informanteApellidoPaterno = informanteApellidoPaterno; }
    
    public String getInformanteApellidoMaterno() { return informanteApellidoMaterno; }
    public void setInformanteApellidoMaterno(String informanteApellidoMaterno) { this.informanteApellidoMaterno = informanteApellidoMaterno; }
    
    public String getInformanteDireccion() { return informanteDireccion; }
    public void setInformanteDireccion(String informanteDireccion) { this.informanteDireccion = informanteDireccion; }
    
    public String getInformanteTelefono() { return informanteTelefono; }
    public void setInformanteTelefono(String informanteTelefono) { this.informanteTelefono = informanteTelefono; }
    
    public String getNombrePatologia() { return nombrePatologia; }
    public void setNombrePatologia(String nombrePatologia) { this.nombrePatologia = nombrePatologia; }
    
    public Boolean getAlergias() { return alergias; }
    public void setAlergias(Boolean alergias) { this.alergias = alergias; }
    
    public String getEspecifiqueAlergia() { return especifiqueAlergia; }
    public void setEspecifiqueAlergia(String especifiqueAlergia) { this.especifiqueAlergia = especifiqueAlergia; }
    
    public Boolean getEmbarazo() { return embarazo; }
    public void setEmbarazo(Boolean embarazo) { this.embarazo = embarazo; }
    
    public Integer getSemanaEmbarazo() { return semanaEmbarazo; }
    public void setSemanaEmbarazo(Integer semanaEmbarazo) { this.semanaEmbarazo = semanaEmbarazo; }
    
    public Boolean getTratamientoMedico() { return tratamientoMedico; }
    public void setTratamientoMedico(Boolean tratamientoMedico) { this.tratamientoMedico = tratamientoMedico; }
    
    public String getTratamientoMedicoDetalle() { return tratamientoMedicoDetalle; }
    public void setTratamientoMedicoDetalle(String tratamientoMedicoDetalle) { this.tratamientoMedicoDetalle = tratamientoMedicoDetalle; }
    
    public Boolean getRecibeAlgunMedicamento() { return recibeAlgunMedicamento; }
    public void setRecibeAlgunMedicamento(Boolean recibeAlgunMedicamento) { this.recibeAlgunMedicamento = recibeAlgunMedicamento; }
    
    public String getMedicamentoActual() { return medicamentoActual; }
    public void setMedicamentoActual(String medicamentoActual) { this.medicamentoActual = medicamentoActual; }
    
    public Boolean getTuvoHemorragiaDental() { return tuvoHemorragiaDental; }
    public void setTuvoHemorragiaDental(Boolean tuvoHemorragiaDental) { this.tuvoHemorragiaDental = tuvoHemorragiaDental; }
    
    public String getEspecifiqueHemorragia() { return especifiqueHemorragia; }
    public void setEspecifiqueHemorragia(String especifiqueHemorragia) { this.especifiqueHemorragia = especifiqueHemorragia; }
    
    public String getAtm() { return atm; }
    public void setAtm(String atm) { this.atm = atm; }
    
    public String getGangliosLinfaticos() { return gangliosLinfaticos; }
    public void setGangliosLinfaticos(String gangliosLinfaticos) { this.gangliosLinfaticos = gangliosLinfaticos; }
    
    public String getRespirador() { return respirador; }
    public void setRespirador(String respirador) { this.respirador = respirador; }
    
    public String getOtrosRespiratorio() { return otrosRespiratorio; }
    public void setOtrosRespiratorio(String otrosRespiratorio) { this.otrosRespiratorio = otrosRespiratorio; }
    
    public String getLabios() { return labios; }
    public void setLabios(String labios) { this.labios = labios; }
    
    public String getLengua() { return lengua; }
    public void setLengua(String lengua) { this.lengua = lengua; }
    
    public String getPaladar() { return paladar; }
    public void setPaladar(String paladar) { this.paladar = paladar; }
    
    public String getPisoDeLaBoca() { return pisoDeLaBoca; }
    public void setPisoDeLaBoca(String pisoDeLaBoca) { this.pisoDeLaBoca = pisoDeLaBoca; }
    
    public String getMucosaYugal() { return mucosaYugal; }
    public void setMucosaYugal(String mucosaYugal) { this.mucosaYugal = mucosaYugal; }
    
    public String getEncias() { return encias; }
    public void setEncias(String encias) { this.encias = encias; }
    
    public Boolean getUtilizaProtesisDental() { return utilizaProtesisDental; }
    public void setUtilizaProtesisDental(Boolean utilizaProtesisDental) { this.utilizaProtesisDental = utilizaProtesisDental; }
    
    public String getTipoProtesis() { return tipoProtesis; }
    public void setTipoProtesis(String tipoProtesis) { this.tipoProtesis = tipoProtesis; }
    
    public String getTiempoProtesis() { return tiempoProtesis; }
    public void setTiempoProtesis(String tiempoProtesis) { this.tiempoProtesis = tiempoProtesis; }
    
    public LocalDate getFechaRevision() { return fechaRevision; }
    public void setFechaRevision(LocalDate fechaRevision) { this.fechaRevision = fechaRevision; }
    
    public Boolean getHabitoFuma() { return habitoFuma; }
    public void setHabitoFuma(Boolean habitoFuma) { this.habitoFuma = habitoFuma; }
    
    public Boolean getHabitoBebe() { return habitoBebe; }
    public void setHabitoBebe(Boolean habitoBebe) { this.habitoBebe = habitoBebe; }
    
    public String getOtrosHabitos() { return otrosHabitos; }
    public void setOtrosHabitos(String otrosHabitos) { this.otrosHabitos = otrosHabitos; }
    
    public Boolean getUtilizaCepilloDental() { return utilizaCepilloDental; }
    public void setUtilizaCepilloDental(Boolean utilizaCepilloDental) { this.utilizaCepilloDental = utilizaCepilloDental; }
    
    public Boolean getUtilizaHiloDental() { return utilizaHiloDental; }
    public void setUtilizaHiloDental(Boolean utilizaHiloDental) { this.utilizaHiloDental = utilizaHiloDental; }
    
    public Boolean getUtilizaEnjuagueBucal() { return utilizaEnjuagueBucal; }
    public void setUtilizaEnjuagueBucal(Boolean utilizaEnjuagueBucal) { this.utilizaEnjuagueBucal = utilizaEnjuagueBucal; }
    
    public String getFrecuenciaCepillo() { return frecuenciaCepillo; }
    public void setFrecuenciaCepillo(String frecuenciaCepillo) { this.frecuenciaCepillo = frecuenciaCepillo; }
    
    public Boolean getSangradoEncias() { return sangradoEncias; }
    public void setSangradoEncias(Boolean sangradoEncias) { this.sangradoEncias = sangradoEncias; }
    
    public String getHigieneBucal() { return higieneBucal; }
    public void setHigieneBucal(String higieneBucal) { this.higieneBucal = higieneBucal; }
    
    public String getObservacionesHigiene() { return observacionesHigiene; }
    public void setObservacionesHigiene(String observacionesHigiene) { this.observacionesHigiene = observacionesHigiene; }
}