package com.example.astromedics;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.astromedics.adapters.AdapterConsultaMedica;
import com.example.astromedics.model.ConsultaMedica;
import com.example.astromedics.model.Terapeuta;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Agenda extends AppCompatActivity {

    String[] mProjection;
    Cursor mCursor;
    AdapterConsultaMedica acm;
    ListView lv;
    List<ConsultaMedica> consultas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agenda);
        consultas = new ArrayList<ConsultaMedica>();
        conseguirConsultas();

        lv = findViewById(R.id.listViewAgenda);

        AdapterConsultaMedica customAdapter = new AdapterConsultaMedica(
                this, R.layout.consulta_medica, consultas);

        lv.setAdapter(customAdapter);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ConsultaMedica cm = consultas.get(position);

            }
        });
    }

    void conseguirConsultas()
    {
        ConsultaMedica cm = new ConsultaMedica(
                new Terapeuta("john", "fonoaudiologo"),
                "esto es una direccion",
                new Date(),
                new Date(),
                "harold"
        ) ;
        consultas.add( cm );
        consultas.add( cm );
        consultas.add( cm );
        consultas.add( cm );
    }
}
