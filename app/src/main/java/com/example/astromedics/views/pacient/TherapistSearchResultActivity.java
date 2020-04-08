package com.example.astromedics.views.pacient;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.astromedics.R;
import com.example.astromedics.adapters.TherapistAdapter;
import com.example.astromedics.model.Localization;
import com.example.astromedics.model.Therapist;
import com.example.astromedics.repository.Repository;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TherapistSearchResultActivity extends AppCompatActivity {
    public static String LOCATION = "localization";
    public static String EMPHASIS = "emphasis";
    public static String START_DATE = "start_date";
    public static String END_DATE = "end_date";

    private Localization localization;
    private Therapist.Emphasis emphasis;
    private Date startDate, endDate;

    LinearLayout noTherapistLinearLayout;
    ListView listView;
    List<Therapist> therapists;
    private TherapistAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_therapist_search_result);
        obtainObjects();
        inflateViews();
        getAvailableTherapist();
        addTherapistToList();
        addListeners();
    }

    private void obtainObjects() {
        localization = (Localization) getIntent().getSerializableExtra(LOCATION);
        startDate = (Date) getIntent().getSerializableExtra(START_DATE);
        endDate = (Date) getIntent().getSerializableExtra(END_DATE);
        emphasis = (Therapist.Emphasis) getIntent().getSerializableExtra(EMPHASIS);
    }

    private void inflateViews() {
        noTherapistLinearLayout = findViewById(R.id.therapist_searh_result_no_terapists);
        listView = (ListView) findViewById(R.id.therapist_search_result_list_view);
    }

    private void getAvailableTherapist() {
        try {
            therapists = Repository.getInstance()
                                   .getTherapistRepository()
                                   .finAvailableTherapists(emphasis,
                                                           startDate,
                                                           endDate);
        } catch (Exception ex) {
            therapists = new ArrayList<>();
            Toast.makeText(getApplicationContext(),
                           ex.getMessage(),
                           Toast.LENGTH_SHORT)
                 .show();
        }

        if (therapists == null || therapists.size() == 0) {
            noTherapistLinearLayout.setVisibility(View.VISIBLE);
        }
    }

    private void addTherapistToList() {
        mAdapter = new TherapistAdapter(this,
                                        therapists);
        listView.setAdapter(mAdapter);
    }

    private void addListeners() {
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(view.getContext(),
                                           TherapistAvailableAppointmentsActivity.class);
                intent.putExtra(TherapistAvailableAppointmentsActivity.THERAPIST,
                                therapists.get(position));
                intent.putExtra(TherapistAvailableAppointmentsActivity.LOCATION,
                                localization);
                intent.putExtra(TherapistAvailableAppointmentsActivity.EMPHASIS,
                                emphasis);
                intent.putExtra(TherapistAvailableAppointmentsActivity.START_DATE,
                                startDate);
                intent.putExtra(TherapistAvailableAppointmentsActivity.END_DATE,
                                endDate);
                startActivity(intent);
            }
        });
    }
}
