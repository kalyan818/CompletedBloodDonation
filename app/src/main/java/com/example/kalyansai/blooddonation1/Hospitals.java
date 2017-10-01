package com.example.kalyansai.blooddonation1;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class Hospitals extends AppCompatActivity {
    private ListView list1;
    private ArrayList<String> items = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hospitals);
        String hospital = getIntent().getStringExtra("place");
        Toast.makeText(this,hospital,Toast.LENGTH_LONG).show();
        list1 = (ListView)findViewById(R.id.HospitalsList);
        list1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

            }
        });
        List<String> categories1 = new ArrayList<String>();
        categories1.add("Kazipet");
        categories1.add("Hanamkonda");
        categories1.add("Warangal");
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,categories1);
        list1.setAdapter(arrayAdapter);
    }
}
