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
        Toast.makeText(this,s,Toast.LENGTH_LONG).show();
        list = (ListView)findViewById(R.id.Locations);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if (i == 0){
                    Intent intent = new Intent(Quantity.this,Hospitals.class);
                    intent.putExtra("place","0");
                    startActivity(intent);
                }
                if (i == 1){
                    Intent intent = new Intent(Quantity.this,Hospitals.class);
                    intent.putExtra("place","1");
                    startActivity(intent);
                }
                if (i == 2){
                    Intent intent = new Intent(Quantity.this,Hospitals.class);
                    intent.putExtra("place","2");
                    startActivity(intent);
                }
            }
        });
        Spinner spinner = (Spinner) findViewById(R.id.spinner);
        spinner.setOnItemSelectedListener(this);
        List<String> categories = new ArrayList<String>();
        List<String> categories1 = new ArrayList<String>();
        categories1.add("Kazipet");
        categories1.add("Hanamkonda");
        categories1.add("Warangal");
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

        // Showing selected spinner item
        Toast.makeText(adapterView.getContext(), "Selected: " + item, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

}
