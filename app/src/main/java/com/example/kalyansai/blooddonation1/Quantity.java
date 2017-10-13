package com.example.kalyansai.blooddonation1;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.List;


public class Quantity extends AppCompatActivity implements AdapterView.OnItemSelectedListener{
    private ListView list;
    private ArrayList<String> items = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quantity);
        String s = getIntent().getStringExtra("key");
        list = (ListView)findViewById(R.id.Locations);
        final Spinner spinner = (Spinner) findViewById(R.id.spinner);
        spinner.setOnItemSelectedListener(this);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if (spinner.getSelectedItem() == "None")
                {
                    Toast.makeText(Quantity.this,"Select BloodGroup",Toast.LENGTH_LONG).show();
                }
                else {
                    if (i == 0) {
                        Intent intent = new Intent(Quantity.this, KazipetHospitals.class);
                        intent.putExtra("place", "Kazipet");
                        startActivity(intent);
                    }
                    if (i == 1) {
                        Intent intent = new Intent(Quantity.this, HanamkondaHospital.class);
                        intent.putExtra("place", "Hanamkonda");
                        startActivity(intent);
                    }
                    if (i == 2) {
                        Intent intent = new Intent(Quantity.this, WarangalHospital.class);
                        intent.putExtra("place", "Warangal");
                        startActivity(intent);
                    }
                }
            }
        });
        List<String> categories = new ArrayList<String>();
        List<String> categories1 = new ArrayList<String>();
        categories1.add("Kazipet");
        categories1.add("Hanamkonda");
        categories1.add("Warangal");
        categories.add("None");
        categories.add("100ml");
        categories.add("200ml");
        categories.add("300ml");
        categories.add("400ml");
        categories.add("500ml");
        categories.add("600ml");
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, categories);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(dataAdapter);
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,categories1);
        list.setAdapter(arrayAdapter);
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        String item = adapterView.getItemAtPosition(i).toString();

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

}
