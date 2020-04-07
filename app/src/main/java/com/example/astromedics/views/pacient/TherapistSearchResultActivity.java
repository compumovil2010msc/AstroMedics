package com.example.astromedics.views.pacient;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.astromedics.R;
import com.example.astromedics.adapters.TherapistAdapter;
import com.example.astromedics.model.Localization;
import com.example.astromedics.model.Therapist;
import com.example.astromedics.repository.Repository;

import java.util.Date;
import java.util.List;

public class TherapistSearchResultActivity extends AppCompatActivity {
    public static String LOCATION = "localization";
    public static String EMPHASIS = "emphasis";
    public static String START_DATE = "start_date";
    public static String END_DATE = "end_date";

    private Localization localization;
    private String emphasis;
    private Date startDate, endDate;

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
        emphasis = (String) getIntent().getSerializableExtra(EMPHASIS);
    }

    private void inflateViews() {
        listView = (ListView) findViewById(R.id.therapist_search_result_list_view);
    }

    private void getAvailableTherapist() {
        therapists = Repository.getInstance()
                               .getTherapistRepository()
                               .finAvailableTherapists(startDate,
                                                       endDate);

    }

    private void addTherapistToList(){
        mAdapter = new TherapistAdapter(this,
                                        therapists);
        listView.setAdapter(mAdapter);
    }

    private void addListeners(){
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(view.getContext(),
                                           TherapistDetailsActivity.class);
                startActivity(intent);
            }
        });
    }
}
