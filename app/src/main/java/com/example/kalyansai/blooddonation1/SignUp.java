package com.example.kalyansai.blooddonation1;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.ArrayList;
import java.util.List;

public class SignUp extends AppCompatActivity  implements AdapterView.OnItemSelectedListener{
    private static EditText DateOfBirth,Email,Password,Address,ConformPassword;
    private Button submit;
    private ProgressDialog progressDialog;
    // [START declare_auth]
    private FirebaseAuth mAuth;
    // [END declare_auth]
    private FirebaseAuth.AuthStateListener mAuthListener;
    boolean mVerificationInProgress = false;
    String mVerificationId;
    private Spinner BloodGroup;
    private int job;
    PhoneAuthProvider.ForceResendingToken mResendToken;
    PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        BloodGroup = (Spinner)findViewById(R.id.bloodGroup);
        mAuth = FirebaseAuth.getInstance();
        submit = (Button)findViewById(R.id.submitinreg);
        Email = (EditText)findViewById(R.id.editTextEmail);
        Password = (EditText)findViewById(R.id.editTextPassword);
        ConformPassword = (EditText)findViewById(R.id.conformPassword);
        DateOfBirth= (EditText) findViewById(R.id.DOB);
        Address = (EditText)findViewById(R.id.AddressBox);
        progressDialog = new ProgressDialog(this);
        BloodGroup.setOnItemSelectedListener(this);
        List<String> categories = new ArrayList<String>();
        categories.add("A+");
        categories.add("A-");
        categories.add("B+");
        categories.add("B-");
        categories.add("AB+");
        categories.add("AB-");
        categories.add("O+");
        categories.add("O-");
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, categories);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        BloodGroup.setAdapter(dataAdapter);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String  email = Email.getText().toString();
                final String  password = Password.getText().toString();
                final String  conformpassword = ConformPassword.getText().toString();
                final String  dateofbirth = DateOfBirth.getText().toString();
                final String  address = Address.getText().toString();
                if (TextUtils.isEmpty(email))
                {
                    Toast.makeText(SignUp.this,"please enter email",Toast.LENGTH_LONG).show();
                }
                else {
                    if (TextUtils.isEmpty(password))
                    {
                        Toast.makeText(SignUp.this,"please enter password",Toast.LENGTH_LONG).show();
                    }
                    else {
                        if (TextUtils.isEmpty(conformpassword))
                        {
                            Toast.makeText(SignUp.this,"please enter conformpassword",Toast.LENGTH_LONG).show();
                        }
                        else {
                            if (password.equals(conformpassword))
                            {
                                    if (TextUtils.isEmpty(dateofbirth))
                                    {
                                        Toast.makeText(SignUp.this,"please enter date of birth",Toast.LENGTH_LONG).show();
                                    }else {
                                         if (TextUtils.isEmpty(address))
                                         {
                                            Toast.makeText(SignUp.this, "please enter address", Toast.LENGTH_LONG).show();
                                         }
                                         else {
                                             userLogin();
                                         }
                                    }
                            }
                            else {
                                Toast.makeText(SignUp.this,"enter correct password",Toast.LENGTH_LONG).show();
                            }
                        }
                    }
                }
            }
        });
    }

    private void userLogin(){

        progressDialog.setMessage("Logining.......");
        progressDialog.show();
        progressDialog.setCancelable(false);
        progressDialog.setCanceledOnTouchOutside(false);
//to check weather he is right user or not (Authentication)
        final String email = Email.getText().toString().trim();
        final String password = Password.getText().toString().trim();
        Toast.makeText(this,email,Toast.LENGTH_LONG).show();
        Toast.makeText(this,password,Toast.LENGTH_LONG).show();
        mAuth.createUserWithEmailAndPassword(email,password)
                .addOnCompleteListener(SignUp.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        //Log.d("FirebaseAuth", "onComplete" + task.getException().getMessage());
                        if (task.isSuccessful()){
                            Toast.makeText(SignUp.this,"Created user with crendientials",Toast.LENGTH_LONG).show();
                            progressDialog.dismiss();
                            Intent intent = new Intent(SignUp.this,Otp.class);
                            intent.putExtra("email",email);
                            intent.putExtra("password",password);
                            startActivity(intent);
                            finish();
                        }else
                        {
                            Toast.makeText(SignUp.this, "Could not register.. please try again", Toast.LENGTH_SHORT).show();
                            progressDialog.dismiss();
                        }
                    }
                });
    }


    public void setDate(View view)
    {
PickerDialogs pickerDialogs = new PickerDialogs();
        pickerDialogs.show(getSupportFragmentManager(),"date_picker");
    }
    public static void Abc(int a, int b, int c,Context context){
        Toast.makeText(context,+a+"/"+b+"/"+c,Toast.LENGTH_LONG).show();
        DateOfBirth.setText(a+"/"+b+"/"+c);
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
