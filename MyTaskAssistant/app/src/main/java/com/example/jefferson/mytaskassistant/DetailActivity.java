package com.example.jefferson.mytaskassistant;

import android.app.DatePickerDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.app.DatePickerDialog;
import android.support.v4.app.DialogFragment;

public class DetailActivity extends AppCompatActivity {

    private EditText taskTitulo ;
    private EditText taskFecha ;
    private EditText taskHora ;
    private EditText taskDetalle ;
    private CheckBox taskCompletado ;
    private String id;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        taskTitulo = findViewById(R.id.inputTitulo);
        taskFecha = findViewById(R.id.inputFecha);
        taskHora = findViewById(R.id.inputHora);
        taskDetalle = findViewById(R.id.inputDetail);
        taskCompletado = findViewById(R.id.completadoDetail);


        //obttener datos del intent
        taskTitulo.setText(getIntent().getStringExtra("title"));
        taskDetalle.setText(getIntent().getStringExtra("detalle"));
        taskCompletado.setChecked(getIntent().getBooleanExtra("completado",false));
        taskFecha.setText(getIntent().getStringExtra("fecha"));
        taskHora.setText(getIntent().getStringExtra("hora"));
        id = getIntent().getStringExtra("id");
    }


    public void showDatePickerDialog(View view) {
        DialogFragment newFragment = new DatePickerFragment();
        newFragment.show(getSupportFragmentManager(),getString(R.string.date_picker));
    }

    public void processDatePickerResult(int year, int month, int day) {
        String month_string = Integer.toString(month+1);
        String day_string = Integer.toString(day);
        String year_string = Integer.toString(year);
        String dateMessage = (day_string + "/" + month_string + "/" + year_string);

        taskFecha.setText(dateMessage);
    }
}
