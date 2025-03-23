package com.example.pruebasautomatizadasspring.models;

public class Producto {
    private Long id;
    private String nombre;
    private double precio;

    public Producto(Long id, String nombre, double precio) {
        this.id = id;
        this.nombre = nombre;
        this.precio = precio;
    }

    public Long getId() { return id; }
    public String getNombre() { return nombre; }
    public double getPrecio() { return precio; }

    public void setId(Long id) { this.id = id; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public void setPrecio(double precio) { this.precio = precio; }
}
