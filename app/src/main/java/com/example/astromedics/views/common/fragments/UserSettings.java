package com.example.astromedics.views.common.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.astromedics.R;
import com.example.astromedics.helpers.GenericListViewItem;
import com.example.astromedics.views.MainActivity;
import com.example.astromedics.views.common.ChangePasswordActivity;

import java.util.ArrayList;
import java.util.List;

public class UserSettings extends Fragment {

    ListView userSettingsListView;
    List<GenericListViewItem> genericListViewItems;

    public UserSettings() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_user_settings,
                                     container,
                                     false);
        inflateViews(view);
        setInitialValues();
        addListeners();
        return view;
    }

    public void inflateViews(View view) {
        userSettingsListView = view.findViewById(R.id.user_settings_list_view);
    }

    public void setInitialValues() {
        genericListViewItems = new ArrayList<>();

        GenericListViewItem auxiliar = new GenericListViewItem("Cuenta");
        GenericListViewItem auxiliar2 = new GenericListViewItem("Cambiar Contraseña");
        GenericListViewItem auxiliar3 = new GenericListViewItem("Cerrar sesión");

        auxiliar.setOnClickListener(new GenericListViewItem.GenericOnClickListener() {
            @Override
            public void onClickPressed() {
                Toast.makeText(getContext(),
                               "Showing my account details",
                               Toast.LENGTH_SHORT)
                     .show();
            }
        });

        auxiliar2.setOnClickListener(new GenericListViewItem.GenericOnClickListener() {
            @Override
            public void onClickPressed() {
                Intent setIntent = new Intent(getContext(),
                                              ChangePasswordActivity.class);
                startActivity(setIntent);
            }
        });

        auxiliar3.setOnClickListener(new GenericListViewItem.GenericOnClickListener() {
            @Override
            public void onClickPressed() {
                Intent setIntent = new Intent(getContext(),
                                              MainActivity.class);
                startActivity(setIntent);
            }
        });

        genericListViewItems.add(auxiliar);
        genericListViewItems.add(auxiliar2);
        genericListViewItems.add(auxiliar3);

        ArrayAdapter<GenericListViewItem> itemsAdapter = new ArrayAdapter<GenericListViewItem>(getContext(),
                                                                                               android.R.layout.simple_list_item_1,
                                                                                               genericListViewItems);
        userSettingsListView.setAdapter(itemsAdapter);
    }

    private void addListeners() {
        userSettingsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                genericListViewItems.get(i)
                                    .onClickPressed();
            }
        });
    }
}
