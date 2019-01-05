package com.example.jefferson.mytaskassistant;

import java.util.Date;

public class Task {
    private String titulo;
    private String detalle;
    private Date fecha;
    private Boolean completado;

    public Task (String titulo, String detalle, Date fecha, Boolean completado){
        this.titulo = titulo;
        this.detalle = detalle;
        this.fecha = fecha;
        this.completado = completado;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo){ this.titulo = titulo; }

    public void setDetalle(String detalle){ this.detalle = detalle; }

    public String getDetalle() {
        return detalle;
    }

    public void setFecha(Date fecha){ this.fecha = fecha; }

    public Date getFecha() {
        return fecha;
    }

    public Boolean getCompletado() {
        return completado;
    }

    public void setCompletado(Boolean completado){ this.completado = completado; }

}
