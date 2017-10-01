package com.example.kalyansai.blooddonation1;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;

public class BloodRequire extends AppCompatActivity implements View.OnClickListener{
    private RelativeLayout anegative,bnegative,onegative,abnegative,opositive,apositive,bpositive,abpositive;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blood_require);
        anegative = (RelativeLayout)findViewById(R.id.Anegative);
        bnegative = (RelativeLayout)findViewById(R.id.Bnegative);
        abnegative = (RelativeLayout)findViewById(R.id.ABnegative);
        onegative = (RelativeLayout)findViewById(R.id.Onegative);
        apositive = (RelativeLayout)findViewById(R.id.Apositive);
        bpositive = (RelativeLayout)findViewById(R.id.Bpositive);
        abpositive = (RelativeLayout)findViewById(R.id.ABpositive);
        opositive = (RelativeLayout)findViewById(R.id.Opositive);
        anegative.setOnClickListener(this);
        bnegative.setOnClickListener(this);
        abnegative.setOnClickListener(this);
        onegative.setOnClickListener(this);
        apositive.setOnClickListener(this);
        bpositive.setOnClickListener(this);
        abpositive.setOnClickListener(this);
        opositive.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view == anegative)
        {
            Intent intent = new Intent(this,Quantity.class);
            intent.putExtra("key","ANEGATIVE");
            startActivity(intent);
        }
        if (view == bnegative)
        {
            Intent intent = new Intent(this,Quantity.class);
            intent.putExtra("key","BNEGATIVE");
            startActivity(intent);
        }
        if (view == abnegative)
        {
            Intent intent = new Intent(this,Quantity.class);
            intent.putExtra("key","ABNEGATIVE");
            startActivity(intent);
        }
        if (view == onegative)
        {
            Intent intent = new Intent(this,Quantity.class);
            intent.putExtra("key","ONEGATIVE");
            startActivity(intent);
        }
        if (view == apositive)
        {
            Intent intent = new Intent(this,Quantity.class);
            intent.putExtra("key","APOSITIVE");
            startActivity(intent);
        }
        if (view == bpositive)
        {
            Intent intent = new Intent(this,Quantity.class);
            intent.putExtra("key","BPOSITIVE");
            startActivity(intent);
        }
        if (view == abpositive)
        {
            Intent intent = new Intent(this,Quantity.class);
            intent.putExtra("key","ABPOSITIVE");
            startActivity(intent);
        }
        if (view == opositive)
        {
            Intent intent = new Intent(this,Quantity.class);
            intent.putExtra("key","OPOSITIVE");
            startActivity(intent);
        }
    }
}
