package com.example.astromedics.model;

public class Terapeuta {
    String nombre;
    String especializacion;

    public Terapeuta( String nombre, String esp)
    {
        this.nombre = nombre;
        this.especializacion = esp;
    }

    String getNombre()
    {
        return this.nombre;
    }

    String getEspecializacion()
    {
        return this.especializacion;
    }
}
