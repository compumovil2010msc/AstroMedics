package com.example.astromedics;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.astromedics.adapters.AdapterConsultaMedica;
import com.example.astromedics.model.ConsultaMedica;
import com.example.astromedics.model.Terapeuta;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Agenda extends AppCompatActivity {

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
                this, R.layout.consulta_medica, consultas
        );

        lv.setAdapter(customAdapter);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.i("prueba", "holaaaa");

                ConsultaMedica cm = consultas.get(position);

                Bundle bd = new Bundle();
                bd.putString("especializacion", cm.getEspecializacion_terapeuta());
                bd.putString("fecha", cm.getFecha());
                bd.putString("hora", cm.getHora() );
                bd.putString("ubicacion", cm.getUbicacion() );
                bd.putString("imagen", cm.getImagen() );

                Intent intent = new Intent(getBaseContext(), AgendaDetalle.class);
                intent.putExtra("bundle", bd);
                startActivity(intent);
            }
        });
    }

    void conseguirConsultas()
    {
        ConsultaMedica cm = new ConsultaMedica(
                new Terapeuta("john", "fonoaudiologo"),
                "esto es una direccion",
                LocalDateTime.now(),
                2,
                "harold"
        ) ;
        consultas.add( cm );
        consultas.add( cm );
        consultas.add( cm );
        consultas.add( cm );
        consultas.add( cm );
        consultas.add( cm );
        consultas.add( cm );
        consultas.add( cm );
        consultas.add( cm );
        consultas.add( cm );
        consultas.add( cm );
        consultas.add( cm );
        consultas.add( cm );
    }
}
