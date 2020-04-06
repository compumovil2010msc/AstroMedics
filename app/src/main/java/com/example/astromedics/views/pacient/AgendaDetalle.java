package com.example.astromedics.views.pacient;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.astromedics.R;

public class AgendaDetalle extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agenda_detalle);

        Bundle bd = getIntent().getBundleExtra("bundle");

        ((TextView)findViewById(R.id.agenda_detalle_especializacion))
                .setText(bd.getString("especializacion"));

        ((TextView)findViewById(R.id.agenda_detalle_fecha))
                .setText(bd.getString("fecha"));

        ((TextView)findViewById(R.id.agenda_detalle_hora))
                .setText(bd.getString("hora"));

        ((TextView)findViewById(R.id.agenda_detalle_ubicacion))
                .setText(bd.getString("ubicacion"));

        ((ImageView)findViewById(R.id.agenda_detalle_imagen))
                .setImageResource(
                        getResources().getIdentifier(
                                bd.getString("imagen"),
                                "drawable",
                                getPackageName()
                        )
                );
    }
}
