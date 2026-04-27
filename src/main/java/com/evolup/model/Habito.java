package com.evolup.model;

public class Habito {

    private int idHabito;
    private String nombre;
    private String frecuencia;
    private int idObjetivoFk;

    public Habito(int idHabito, String nombre, String frecuencia, int idObjetivoFk) {
        this.idHabito = idHabito;
        this.nombre = nombre;
        this.frecuencia = frecuencia;
        this.idObjetivoFk = idObjetivoFk;
    }

    public int getIdHabito() { return idHabito; }
    public String getNombre() { return nombre; }
    public String getFrecuencia() { return frecuencia; }
    public int getIdObjetivoFk() { return idObjetivoFk; }

    public void setIdHabito(int idHabito) { this.idHabito = idHabito; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public void setFrecuencia(String frecuencia) { this.frecuencia = frecuencia; }
    public void setIdObjetivoFk(int idObjetivoFk) { this.idObjetivoFk = idObjetivoFk; }

    @Override
    public String toString() {
        return "Habito{id=" + idHabito + ", nombre='" + nombre + "', frecuencia='" + frecuencia + "'}";
    }
}
