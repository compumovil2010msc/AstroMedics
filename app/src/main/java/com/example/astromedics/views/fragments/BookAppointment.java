package com.example.astromedics.views.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.astromedics.AgendaDetalle;
import com.example.astromedics.R;
import com.example.astromedics.adapters.AdapterConsultaMedica;
import com.example.astromedics.model.ConsultaMedica;
import com.example.astromedics.model.Terapeuta;
import com.example.astromedics.views.BookAppointmentLocationActivity;
import com.example.astromedics.views.TherapistDetailsActivity;
import com.example.astromedics.views.TherapistFilterActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class BookAppointment extends Fragment {
    FloatingActionButton floatingActionButton;

    ListView lv;
    List<ConsultaMedica> consultas;

    public BookAppointment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_book_appointment, container, false);
        floatingActionButton = view.findViewById(R.id.addAppointment);

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(view.getContext(), BookAppointmentLocationActivity.class);
                startActivity(intent);
            }
        });

        consultas = new ArrayList<ConsultaMedica>();

        conseguirConsultas();

        lv = view.findViewById(R.id.listViewAgenda);

        AdapterConsultaMedica customAdapter = new AdapterConsultaMedica(
                view.getContext(), R.layout.consulta_medica, consultas
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

                Intent intent = new Intent( view.getContext(), AgendaDetalle.class);
                intent.putExtra("bundle", bd);
                startActivity(intent);
            }
        });
        return view;
    }

    void conseguirConsultas()
    {
        ConsultaMedica cm = new ConsultaMedica(
                new Terapeuta("Nombre1 Apellido1", "Fonoaudiologo"),
                "Carrera 1 # 1-1",
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
