package com.example.astromedics.views.pacient;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.astromedics.R;
import com.example.astromedics.adapters.old.TherapistSearchResultAdapter;
import com.example.astromedics.model.old.TherapistSearchResult;

import java.util.ArrayList;

public class TherapistSearchResultActivity extends AppCompatActivity {

    private ListView listView;
    private TherapistSearchResultAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_therapist_search_result);

        listView = (ListView) findViewById(R.id.therapist_search_result);

        ArrayList<TherapistSearchResult> therapists = new ArrayList<>();
        therapists.add(new TherapistSearchResult("Nombre1 Apellido1", 150, 4.5));
        therapists.add(new TherapistSearchResult("Nombre2 Apellido2", 150, 4.5));
        therapists.add(new TherapistSearchResult("Nombre3 Apellido3", 150, 4.5));
        therapists.add(new TherapistSearchResult("Nombre4 Apellido4", 150, 4.5));
        therapists.add(new TherapistSearchResult("Nombre5 Apellido5", 150, 4.5));
        therapists.add(new TherapistSearchResult("Nombre6 Apellido6", 150, 4.5));
        therapists.add(new TherapistSearchResult("Nombre7 Apellido7", 150, 4.5));
        therapists.add(new TherapistSearchResult("Nombre8 Apellido8", 150, 4.5));
        therapists.add(new TherapistSearchResult("Nombre9 Apellido9", 150, 4.5));
        therapists.add(new TherapistSearchResult("Nombre10 Apellido10", 150, 4.5));
        therapists.add(new TherapistSearchResult("Nombre11 Apellido11", 150, 4.5));

        mAdapter = new TherapistSearchResultAdapter(this, therapists);
        listView.setAdapter(mAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent=new Intent(view.getContext(), TherapistDetailsActivity.class);
                startActivity(intent);
            }
        });
    }
}
