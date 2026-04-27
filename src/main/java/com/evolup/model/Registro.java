package com.evolup.model;

import java.time.LocalDate;

public class Registro {

    private int idRegistro;
    private LocalDate fecha;
    private boolean completado;
    private int idHabitoFk;

    public Registro(int idRegistro, LocalDate fecha, boolean completado, int idHabitoFk) {
        this.idRegistro = idRegistro;
        this.fecha = fecha;
        this.completado = completado;
        this.idHabitoFk = idHabitoFk;
    }

    public int getIdRegistro() { return idRegistro; }
    public LocalDate getFecha() { return fecha; }
    public boolean isCompletado() { return completado; }
    public int getIdHabitoFk() { return idHabitoFk; }

    public void setIdRegistro(int idRegistro) { this.idRegistro = idRegistro; }
    public void setFecha(LocalDate fecha) { this.fecha = fecha; }
    public void setCompletado(boolean completado) { this.completado = completado; }
    public void setIdHabitoFk(int idHabitoFk) { this.idHabitoFk = idHabitoFk; }

    @Override
    public String toString() {
        return "Registro{id=" + idRegistro + ", fecha=" + fecha + ", completado=" + completado + "}";
    }
}
