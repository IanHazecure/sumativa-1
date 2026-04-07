package com.citas.model;

import jakarta.persistence.*;

@Entity
@Table(name = "CITAS")
public class Cita {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private int id;
    
    @Column(name = "PACIENTE")
    private String paciente;
    
    @Column(name = "MEDICO")
    private String medico;
    
    @Column(name = "FECHA")
    private String fecha;
    
    @Column(name = "HORA")
    private String hora;
    
    @Column(name = "ESTADO")
    private String estado;

    public Cita() {
    }
    
    public Cita(int id, String paciente, String medico, String fecha, String hora, String estado) {
        this.id = id;
        this.paciente = paciente;
        this.medico = medico;
        this.fecha = fecha;
        this.hora = hora;
        this.estado = estado;

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPaciente() {
        return paciente;
    }

    public void setPaciente(String paciente) {
        this.paciente = paciente;
}

    public String getMedico() {
        return medico;
    }

    public void setMedico(String medico) {
        this.medico = medico;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getHora() 
    {

        return hora;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
}
