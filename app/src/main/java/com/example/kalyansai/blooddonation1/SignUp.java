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
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class SignUp extends AppCompatActivity  implements AdapterView.OnItemSelectedListener{
    private static EditText DateOfBirth,Email,Password,Address,ConformPassword,Mobile;
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
        //to know weather the user is loged in into our app or not if he loged in then open another activity
        if (mAuth.getCurrentUser() != null){
            mAuth.signOut();
        }
        submit = (Button)findViewById(R.id.submitinreg);
        Email = (EditText)findViewById(R.id.editTextEmail);
        Password = (EditText)findViewById(R.id.editTextPassword);
        ConformPassword = (EditText)findViewById(R.id.conformPassword);
        DateOfBirth= (EditText) findViewById(R.id.DOB);
        Address = (EditText)findViewById(R.id.AddressBox);
        Mobile = (EditText)findViewById(R.id.Name);
        progressDialog = new ProgressDialog(this);
        BloodGroup.setOnItemSelectedListener(this);
        List<String> categories = new ArrayList<String>();
        categories.add("None");
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
                final String  mobile = Mobile.getText().toString();
                final String bloodgroup = BloodGroup.getSelectedItem().toString();
                if (TextUtils.isEmpty(mobile)) {
                    Toast.makeText(SignUp.this,"please enter Mobile Number",Toast.LENGTH_LONG).show();
                } else {
                    if (TextUtils.isEmpty(email)) {
                        Toast.makeText(SignUp.this, "please enter email", Toast.LENGTH_LONG).show();
                    } else {
                        if (TextUtils.isEmpty(password)) {
                            Toast.makeText(SignUp.this, "please enter password", Toast.LENGTH_LONG).show();
                        } else {
                            if (TextUtils.isEmpty(conformpassword)) {
                                Toast.makeText(SignUp.this, "please enter conformpassword", Toast.LENGTH_LONG).show();
                            } else {
                                if (password.equals(conformpassword) && (Password.length() >= 6)) {
                                    if (TextUtils.isEmpty(dateofbirth)) {
                                        Toast.makeText(SignUp.this, "please enter date of birth", Toast.LENGTH_LONG).show();
                                    } else {
                                        if (bloodgroup.equals("None")){
                                            Toast.makeText(SignUp.this, "please select blood group", Toast.LENGTH_LONG).show();
                                        }else {
                                            if (TextUtils.isEmpty(address)) {
                                                Toast.makeText(SignUp.this, "please enter address", Toast.LENGTH_LONG).show();
                                            } else {
                                                userLogin();
                                            }
                                        }
                                    }
                                } else {
                                    Toast.makeText(SignUp.this, "enter correct password and password must be atleast 6 characters", Toast.LENGTH_LONG).show();
                                }
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
        String email = Email.getText().toString().trim();
        String password = Password.getText().toString().trim();
        Toast.makeText(this,email,Toast.LENGTH_LONG).show();
        Toast.makeText(this,password,Toast.LENGTH_LONG).show();
        mAuth.createUserWithEmailAndPassword(email,password)
                .addOnCompleteListener(SignUp.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        //Log.d("FirebaseAuth", "onComplete" + task.getException().getMessage());
                        if (task.isSuccessful()){
                            Toast.makeText(SignUp.this,"Created user with crendientials",Toast.LENGTH_LONG).show();
                            UpdateDatabase();
                        }else
                        {
                            Toast.makeText(SignUp.this, "Could not register.. please try again", Toast.LENGTH_SHORT).show();
                            progressDialog.dismiss();
                        }
                    }
                });
    }
    private void UpdateDatabase() {
        FirebaseDatabase.getInstance().setPersistenceEnabled(true);
        String mobile = Mobile.getText().toString().trim();
        String email = Email.getText().toString().trim();
        String reverse = new StringBuffer(email).reverse().toString();
        String reverse1 = "";
        for(int i = 10;i<reverse.length();i++)
        {
            reverse1 = reverse.charAt(i) + reverse1;
        }
        DatabaseReference ref = FirebaseDatabase.getInstance()
                .getReferenceFromUrl("https://blooddonation1-3dcaa.firebaseio.com/Users");
        String password = Password.getText().toString().trim();
        String dateofbirth = DateOfBirth.getText().toString();
        String mobileNumber = Mobile.getText().toString();
        String address = Address.getText().toString();
        String bloodgroup = BloodGroup.getSelectedItem().toString();
        DatabaseReference childref = ref.child(reverse1);
        DatabaseReference childref0 = childref.child("Email");
        DatabaseReference childref1 = childref.child("password");
        DatabaseReference childref2 = childref.child("DateOfBirth");
        DatabaseReference childref3 = childref.child("Address");
        DatabaseReference childref4 = childref.child("BloodGroup");
        DatabaseReference childref5 = childref.child("MobileNumber");
        childref0.setValue(email);
        childref1.setValue(password);
        childref2.setValue(dateofbirth);
        childref3.setValue(address);
        childref4.setValue(bloodgroup);
        childref5.setValue(mobile);
        progressDialog.dismiss();
        Intent intent = new Intent(SignUp.this,Otp.class);
        intent.putExtra("email",email);
        intent.putExtra("password",password);
        intent.putExtra("mobileNumber",mobileNumber);
        startActivity(intent);
        finish();
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
        
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}
