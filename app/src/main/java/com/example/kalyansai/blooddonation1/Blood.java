package com.example.kalyansai.blooddonation1;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;

import com.google.firebase.auth.FirebaseAuth;

public class Blood extends AppCompatActivity implements View.OnClickListener {
    private FirebaseAuth mAuth;
    private RelativeLayout requireblood,donateblood;
    Button logut;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blood_required);
        requireblood = (RelativeLayout)findViewById(R.id.BloodRequired);
        donateblood = (RelativeLayout)findViewById(R.id.DonateBlood);
        logut = (Button)findViewById(R.id.logut);
        mAuth = FirebaseAuth.getInstance();
        requireblood.setOnClickListener(this);
        donateblood.setOnClickListener(this);
        logut.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view == requireblood)
        {
            Intent intent = new Intent(this,BloodRequire.class);
            startActivity(intent);
        }
        if (view == donateblood)
        {
            Intent intent = new Intent(this,RetriveMultipleValues.class);
            startActivity(intent);
        }
        if (view == logut)
        {
            mAuth.signOut();
            startActivity(new Intent(getApplicationContext(),MainActivity.class));
            finish();
        }
    }
}