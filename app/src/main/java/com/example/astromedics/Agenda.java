package com.example.astromedics;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.os.Bundle;
import android.widget.ListView;

import com.example.astromedics.adaptadores.AdapterConsultaMedica;
import com.example.astromedics.modelo.ConsultaMedica;

public class Agenda extends AppCompatActivity {

    String[] mProjection;
    Cursor mCursor;
    AdapterConsultaMedica acm;
    ListView lv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agenda);

        lv = findViewById(R.id.listViewAgenda);
        mProjection = new String[]{
                ConsultaMedica.Profile
        }

    }
}
