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

    public ContentValues toContentValues() {
        ContentValues values = new ContentValues();
        values.put(TaskContract.TaskEntry.TITULO, this.titulo);
        values.put(TaskContract.TaskEntry.DETALLE, detalle);
        if (this.completado)
            values.put(TaskContract.TaskEntry.COMPLETADO, 1);
        else
            values.put(TaskContract.TaskEntry.COMPLETADO, 0);

        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String date = df.format(fecha);
        values.put(TaskContract.TaskEntry.FECHA, date);

        return values;
    }

}
