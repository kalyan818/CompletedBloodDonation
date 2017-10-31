package com.example.kalyansai.blooddonation1;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Blood extends AppCompatActivity implements View.OnClickListener {
    private FirebaseAuth mAuth;
    private RelativeLayout requireblood,donateblood;
    Button logut;
    String mailid;
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
            Quit();
        }
        if (view == logut)
        {
            mAuth.signOut();
            startActivity(new Intent(getApplicationContext(),MainActivity.class));
            finish();
        }
    }
    /*@Override
    public boolean onKeyDown(int keyCode, KeyEvent event)
    {
        if ((keyCode == KeyEvent.KEYCODE_BACK))
        {

        }
        return super.onKeyDown(keyCode, event);
    }*/

    private void Quit() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Your details will be added to donars list make sure that your medically fit to donate blood.")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        UpdateDatabase();
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        //  Action for 'NO' Button
                        dialog.dismiss();
                    }
                });

        //Creating dialog box
        AlertDialog alert = builder.create();
        //Setting the title manually
        alert.setTitle("Donate Blood");
        alert.show();
    }

    private void UpdateDatabase() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        String uid = user.getEmail();
        String reverse = new StringBuffer(uid).reverse().toString();
        String reverse1 = "";
        for(int i = 10;i<reverse.length();i++)
        {
            reverse1 = reverse.charAt(i) + reverse1 ;
        }
        mailid = reverse1;
        Toast.makeText(getApplicationContext(),reverse1,Toast.LENGTH_LONG).show();
        DatabaseReference ref = FirebaseDatabase.getInstance()
                .getReferenceFromUrl("https://blooddonation1-3dcaa.firebaseio.com/Users/"+reverse1);
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                    String bloodGroup = dataSnapshot.child("BloodGroup").getValue(String.class);
                    String mobileNumber = dataSnapshot.child("MobileNumber").getValue(String.class);
                    Toast.makeText(getApplicationContext(),"Thanku your details are added to donars list",Toast.LENGTH_LONG).show();
                LoadintoDoners(bloodGroup,mobileNumber);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void LoadintoDoners(String bld,String number) {
        String bloodGroup = bld;
        String mobileNumber = number;
        DatabaseReference ref = FirebaseDatabase.getInstance()
                .getReferenceFromUrl("https://blooddonation1-3dcaa.firebaseio.com/Doners");
        DatabaseReference childref = ref.child(mailid);
        DatabaseReference childref4 = childref.child("BloodGroup");
        DatabaseReference childref5 = childref.child("MobileNumber");
        childref4.setValue(bloodGroup);
        childref5.setValue(mobileNumber);
    }
}