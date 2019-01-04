package com.example.jefferson.mytaskassistant;

import android.app.DatePickerDialog;
import android.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

public class DetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        EditText taskTitulo = findViewById(R.id.inputTitulo);
        EditText taskfecha = findViewById(R.id.inputFecha);
        EditText taskDetalle = findViewById(R.id.inputDetail);
        CheckBox taskCompletado = findViewById(R.id.completadoDetail);


        //obttener datos del intent
        taskTitulo.setText(getIntent().getStringExtra("title"));
        taskCompletado.setChecked(getIntent().getBooleanExtra("completado",false));
    }


    public void showDatePickerDialog(View view) {

    }
}
