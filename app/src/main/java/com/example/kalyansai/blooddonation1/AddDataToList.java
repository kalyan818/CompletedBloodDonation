package com.example.kalyansai.blooddonation1;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Objects;

public class AddDataToList extends AppCompatActivity {
    private ListView list;
    private ArrayList<String> mUsernames = new ArrayList<>();
    private ArrayList<String> mUsernames1 = new ArrayList<>();
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_data_to_list);
        final DatabaseReference ref = FirebaseDatabase.getInstance()
                .getReferenceFromUrl("https://blooddonation1-3dcaa.firebaseio.com/Doners/");
        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,mUsernames);
        list = (ListView)findViewById(R.id.ListPanel);
        Toast.makeText(this,mUsernames.get(1),Toast.LENGTH_LONG).show();
        progressDialog = new ProgressDialog(this);
        list.setAdapter(arrayAdapter);
        progressDialog.setMessage("Loading.......");
        progressDialog.show();
        progressDialog.setCancelable(false);
        progressDialog.setCanceledOnTouchOutside(false);
        ref.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                String child = dataSnapshot.getKey().toString();
                String values = dataSnapshot.getValue().toString();
                String fina =  child + " " + values + " \n ";
                //Toast.makeText(AddDataToList.this,values,Toast.LENGTH_LONG).show();
                mUsernames.add(fina);
                mUsernames1.add(values);
                arrayAdapter.notifyDataSetChanged();
                progressDialog.dismiss();
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
