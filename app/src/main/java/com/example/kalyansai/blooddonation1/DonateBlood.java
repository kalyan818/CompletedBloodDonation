package com.example.kalyansai.blooddonation1;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.firebase.client.Firebase;

public class DonateBlood extends AppCompatActivity {
    private EditText first,second;
    private Button submit;
    private Firebase mRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_donate_blood);
        Firebase.setAndroidContext(this);
        first = (EditText)findViewById(R.id.First);
        second = (EditText)findViewById(R.id.Second);
        submit = (Button)findViewById(R.id.submit);
        mRef = new Firebase("https://blooddonation1-3dcaa.firebaseio.com/");
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String  value = first.getText().toString();
                Firebase childRef = mRef.child("name");
                childRef.setValue(value);
            }
        });
    }
}
