package com.example.kalyansai.blooddonation1;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.StaticLayout;
import android.util.Log;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.Query;
import com.firebase.client.ValueEventListener;
import com.firebase.client.core.view.View;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class RetriveMultipleValues extends AppCompatActivity {
    private Firebase mRef,Mreference;
    private EditText text,text1;
    private Button submit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_retrive_multiple_values);
        FirebaseDatabase.getInstance().setPersistenceEnabled(true);

        text =  (EditText)findViewById(R.id.First);
        text1 = (EditText)findViewById(R.id.Second);
        submit  = (Button)findViewById(R.id.submit);
        submit.setOnClickListener(new android.view.View.OnClickListener() {
            @Override
            public void onClick(android.view.View view) {
                DatabaseReference ref = FirebaseDatabase.getInstance()
                        .getReferenceFromUrl("https://blooddonation1-3dcaa.firebaseio.com/Users");
                String  value = text.getText().toString();
                String key = text1.getText().toString();
                DatabaseReference childref = ref.child("sai123");
                DatabaseReference childref1 = childref.child("Emailgkghkh");
                childref1.setValue("hloghkhgh");
            }
        });
    }
}
