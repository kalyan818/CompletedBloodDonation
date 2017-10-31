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

public class WarangalHospital extends AppCompatActivity {
    private ListView list1;
    private ArrayList<String> items = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_warangal_hospital);
        String hospital = getIntent().getStringExtra("place");
        Toast.makeText(this,hospital,Toast.LENGTH_LONG).show();
        list1 = (ListView)findViewById(R.id.WarangalList);
        list1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String item = adapterView.getItemAtPosition(i).toString();
                Intent intent = new Intent(WarangalHospital.this,Hospitals.class);
                intent.putExtra("Hospital",item);
                startActivity(intent);
            }
        });
        List<String> categories1 = new ArrayList<String>();
        categories1.add("Apoorva PolyClinic");
        categories1.add("Aravindha Hospital");
        categories1.add("Ashwini.htm");
        categories1.add("Athidhi Clinic");
        categories1.add("Balaji Clinic");
        categories1.add("Balaji Neuro Clinic");
        categories1.add("Care Children Hospital");
        categories1.add("Chaitra Eye Hospital");
        categories1.add("Hiranmayi Medicare");
        categories1.add("Jagruthi");
        categories1.add("Jaya Laxmi Hospital");
        categories1.add("Lalitha Orthopedic Hospital");
        categories1.add("Life Care Hospital");
        categories1.add("Ortho Care Hospital");
        categories1.add("Raja Hospital");
        categories1.add("Sai Sree Dental Hospital");
        categories1.add("Sri Raj Chest Clinic");
        categories1.add("Sri Clinic");
        categories1.add("Veena Medicare Hospital");
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,categories1);
        list1.setAdapter(arrayAdapter);
    }
}
