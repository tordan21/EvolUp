package com.evolup.model;

import java.time.LocalDate;

public class Objetivo {

    private int idObjetivo;
    private String nombre;
    private String descripcion;
    private LocalDate fechaInicio;
    private LocalDate fechaObjetivo;
    private String estado;
    private int idUsuarioFk;

    public Objetivo(int idObjetivo, String nombre, String descripcion,
                    LocalDate fechaInicio, LocalDate fechaObjetivo,
                    String estado, int idUsuarioFk) {
        this.idObjetivo = idObjetivo;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.fechaInicio = fechaInicio;
        this.fechaObjetivo = fechaObjetivo;
        this.estado = estado;
        this.idUsuarioFk = idUsuarioFk;
    }

    public int getIdObjetivo() { return idObjetivo; }
    public String getNombre() { return nombre; }
    public String getDescripcion() { return descripcion; }
    public LocalDate getFechaInicio() { return fechaInicio; }
    public LocalDate getFechaObjetivo() { return fechaObjetivo; }
    public String getEstado() { return estado; }
    public int getIdUsuarioFk() { return idUsuarioFk; }

    public void setIdObjetivo(int idObjetivo) { this.idObjetivo = idObjetivo; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }
    public void setFechaInicio(LocalDate fechaInicio) { this.fechaInicio = fechaInicio; }
    public void setFechaObjetivo(LocalDate fechaObjetivo) { this.fechaObjetivo = fechaObjetivo; }
    public void setEstado(String estado) { this.estado = estado; }
    public void setIdUsuarioFk(int idUsuarioFk) { this.idUsuarioFk = idUsuarioFk; }

    @Override
    public String toString() {
        return "Objetivo{id=" + idObjetivo + ", nombre='" + nombre + "', estado='" + estado + "'}";
    }
}
