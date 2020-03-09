package com.example.astromedics.modelo;

import java.text.SimpleDateFormat;
import java.util.Date;

public class ConsultaMedica {
    private Terapeuta terapeuta;
    private String ubicacion; // esto podria cambiarse por algo de los mapas
    private Date fechaInicio;
    private Date fechaFinal;
    private ConsultaMedica(Terapeuta terapeuta, String ubicacion, Date fechaInicio, Date fechaFinal )
    {
        this.terapeuta = terapeuta;
        this.ubicacion = ubicacion;
        this.fechaInicio = fechaInicio;
        this.fechaFinal = fechaFinal;
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
        SimpleDateFormat ft = new SimpleDateFormat ("E dd M");
        return ft.format(this.fechaInicio);
    }
}
