package com.example.astromedics.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.astromedics.R;
import com.example.astromedics.model.old.ConsultaMedica;

import java.util.List;

public class AdapterConsultaMedica extends ArrayAdapter<ConsultaMedica>{
    private int resourceLayout;

    public AdapterConsultaMedica(Context context, int resource, List<ConsultaMedica> items) {
        super(context, resource, items);
        this.resourceLayout = resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View view, @NonNull ViewGroup parent) {
        if (view == null) {
            LayoutInflater vi;
            vi = LayoutInflater.from(getContext());
            view = vi.inflate( this.resourceLayout, null);
        }
        ConsultaMedica cm = getItem(position);

        if (cm != null)
        {
            ((TextView) view.findViewById(R.id.c_m_nombre))
                    .setText( cm.getNombre_terapeuta().toLowerCase() );

            ((TextView) view.findViewById(R.id.c_m_especializacion))
                    .setText( cm.getEspecializacion_terapeuta().toLowerCase() );

            ((TextView) view.findViewById(R.id.c_m_fecha))
                    .setText( cm.getFecha().toLowerCase() );

            ((ImageView) view.findViewById(R.id.c_m_imagen))
                    .setImageResource(
                            view.getResources().getIdentifier(
                                    cm.getImagen().toLowerCase(),
                                    "drawable",
                                    this.getContext().getPackageName()
                                    )
                    );
        }
        return view;
    }
}
