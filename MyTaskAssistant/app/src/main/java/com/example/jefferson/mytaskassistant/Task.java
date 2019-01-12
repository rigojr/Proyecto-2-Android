package com.example.jefferson.mytaskassistant;

import android.content.ContentValues;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Task {
    private String titulo;
    private String detalle;
    private Date fecha;
    private Boolean completado;

    public Task(){}

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

    /**
     * Método que convierte los atributos del Task en ContentValues
     * @return ContentValues
     */

    public ContentValues toContentValues() {
        ContentValues values = new ContentValues();
        values.put(TaskContract.TaskEntry.TITULO, this.titulo);
        values.put(TaskContract.TaskEntry.DETALLE, detalle);
        if (this.completado)
            values.put(TaskContract.TaskEntry.COMPLETADO, 1);
        else
            values.put(TaskContract.TaskEntry.COMPLETADO, 0);

        SimpleDateFormat format = new SimpleDateFormat(TaskContract.TaskEntry.DATE_FORMAT);
        values.put(TaskContract.TaskEntry.FECHA, format.format(fecha));

        return values;
    }

}
