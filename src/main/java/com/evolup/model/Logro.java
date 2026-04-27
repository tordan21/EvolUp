package com.evolup.model;

public class Logro {

    private int idLogro;
    private String nombre;
    private String descripcion;

    public Logro(int idLogro, String nombre, String descripcion) {
        this.idLogro = idLogro;
        this.nombre = nombre;
        this.descripcion = descripcion;
    }

    public int getIdLogro() { return idLogro; }
    public String getNombre() { return nombre; }
    public String getDescripcion() { return descripcion; }

    public void setIdLogro(int idLogro) { this.idLogro = idLogro; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }

    @Override
    public String toString() {
        return "Logro{id=" + idLogro + ", nombre='" + nombre + "'}";
    }
}
