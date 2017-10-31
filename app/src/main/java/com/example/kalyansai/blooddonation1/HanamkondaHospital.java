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

public class HanamkondaHospital extends AppCompatActivity {
    private ListView list1;
    private ArrayList<String> items = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hanamkonda_hospital);
        String hospital = getIntent().getStringExtra("place");
        Toast.makeText(this,hospital,Toast.LENGTH_LONG).show();
        list1 = (ListView)findViewById(R.id.HanamkondaList);
        list1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String item = adapterView.getItemAtPosition(i).toString();
                Intent intent = new Intent(HanamkondaHospital.this,Hospitals.class);
                intent.putExtra("Hospital",item);
                startActivity(intent);
            }
        });
        List<String> categories1 = new ArrayList<String>();
        categories1.add("Rohini Super Speciality Hospital");
        categories1.add("Aarogya Multi Speciality Clinic");
        categories1.add("Apolo Clinic");
        categories1.add("Clinic1");
        categories1.add("Clinic2");
        categories1.add("Cutis Skin Care&Sexual Health Center");
        categories1.add("Derma Care");
        categories1.add("gouda ramesh clinic");
        categories1.add("Madhu Neuro Care Super Speciality Hospital");
        categories1.add("Medi Life Hospital");
        categories1.add("Rakshith Surgical & Chest Center");
        categories1.add("Sandeep Gastro & Liver Clinic");
        categories1.add("Sri Srinivas Super Speciality Dental Hospitals");
        categories1.add("Srinivas Neuro Care");
        categories1.add("Sri Balaji Neuro Care");
        categories1.add("Jaya Hospital");
        categories1.add("Vishwas Super Speciality Hospital");
        categories1.add("Ortho");
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,categories1);
        list1.setAdapter(arrayAdapter);
    }
}
