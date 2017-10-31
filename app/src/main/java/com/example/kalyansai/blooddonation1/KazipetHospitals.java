package com.example.kalyansai.blooddonation1;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class KazipetHospitals extends AppCompatActivity {
    private ListView list1;
    private ArrayList<String> items = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kazipet_hospitals);
        String hospital = getIntent().getStringExtra("place");
        Toast.makeText(this,hospital,Toast.LENGTH_LONG).show();
        list1 = (ListView)findViewById(R.id.KazipetList);
        list1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String item = adapterView.getItemAtPosition(i).toString();
                Intent intent = new Intent(KazipetHospitals.this,Hospitals.class);
                intent.putExtra("Hospital",item);
                startActivity(intent);
            }
        });
        List<String> categories1 = new ArrayList<String>();
        categories1.add("Jaya Laxmi Hospital");
        categories1.add("Jaya Sri Clinic");
        categories1.add("Karunya Poli Clinic");
        categories1.add("Laxmi Sai Hospital");
        categories1.add("Prasad's Hospital");
        categories1.add("Ravindra Poly Clinic");
        categories1.add("Shirnvasa Nursing Home");
        categories1.add("Velugu Eye Hospital");
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,categories1);
        list1.setAdapter(arrayAdapter);
    }
}
