package com.example.astromedics.old;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

public class ConsultaMedica {
    private Terapeuta terapeuta;
    private String ubicacion; // esto podria cambiarse por algo de los mapas
    private LocalDateTime fechaInicio;
    private int horas;
    private String imagen;

    public ConsultaMedica(Terapeuta terapeuta, String ubicacion, LocalDateTime fechaInicio, int horas, String imagen )
    {
        this.terapeuta = terapeuta;
        this.ubicacion = ubicacion;
        this.fechaInicio = fechaInicio;
        this.horas = horas;
        this.imagen = imagen;
    }

    public String getHora()
    {
        return this.fechaInicio.getHour() + " - " + this.fechaInicio.plus( this.horas , ChronoUnit.HOURS).getHour();
    }

    public String getImagen()
    {
        return this.imagen;
    }

    public String getNombre_terapeuta()
    {
        return this.terapeuta.getNombre();
    }

    public String getEspecializacion_terapeuta()
    {
        return this.terapeuta.getEspecializacion();
    }

    public String getUbicacion()
    {
        return this.ubicacion;
    }

    public String getFecha()
    {
        return this.fechaInicio.getDayOfWeek().toString() + " " + fechaInicio.getDayOfMonth() + " " + fechaInicio.getMonth();
    }
}
