package com.example.astromedics.adaptadores;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.astromedics.R;
import com.example.astromedics.modelo.ConsultaMedica;

public class AdapterConsultaMedica extends ArrayAdapter<ConsultaMedica>{
    public AdapterConsultaMedica(@NonNull Context context, int resource, int textViewResourceId, @NonNull ConsultaMedica[] objects) {
        super(context, resource, textViewResourceId, objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View view, @NonNull ViewGroup parent) {

        ConsultaMedica cm = getItem(position);

        if (cm != null)
        {
            ((TextView) view.findViewById(R.id.c_m_nombre))
                    .setText( cm.getNombre_terapeuta() );

            ((TextView) view.findViewById(R.id.c_m_especializacion))
                    .setText( cm.getEspecializacion_terapeuta() );

            ((TextView) view.findViewById(R.id.c_m_fecha))
                    .setText( cm.getFecha() );

            ((ImageView) view.findViewById(R.id.c_m_imagen))
                    .setImageResource(
                            view.getResources().getIdentifier(
                                    cursor.getString(INDEX_imagen).toLowerCase(),
                                    "drawable",
                                    context.getPackageName()
                                    )
                    );
        }

        return super.getView(position, convertView, parent);
    }

    //    public View newView(Context context, Cursor cursor, ViewGroup parent) {
//        return LayoutInflater.from(context).inflate(R.layout.consulta_medica, parent, false);
//    }


//    public void getView(View view, Context context, Cursor cursor) {
//        ((TextView) view.findViewById(R.id.c_m_nombre))
//                .setText( cursor.getString(INDEX_nombre) );
//
//        ((TextView) view.findViewById(R.id.c_m_especializacion))
//                .setText( cursor.getString(INDEX_especializacion) );
//
//        ((TextView) view.findViewById(R.id.c_m_fecha))
//                .setText( cursor.getString(INDEX_fecha) );
//
//        ((ImageView) view.findViewById(R.id.c_m_imagen))
//                .setImageResource(
//                        view.getResources().getIdentifier(
//                                cursor.getString(INDEX_imagen).toLowerCase(),
//                                "drawable",
//                                context.getPackageName()
//                                )
//                );
//    }
}
