package com.example.jefferson.mytaskassistant;

import java.util.Date;

public class Task {
    private String titulo;
    private String detalle;
    private Date fecha;
    private Boolean completado;

    Task (String titulo, String detalle, Date fecha, Boolean completado){
        this.titulo = titulo;
        this.detalle = detalle;
        this.fecha = fecha;
        this.completado = completado;
    }

    public String getTitulo() {
        return titulo;
    }

    public String getDetalle() {
        return detalle;
    }

    public Date getFecha() {
        return fecha;
    }

    public Boolean getCompletado() {
        return completado;
    }
}
