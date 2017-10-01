package com.example.kalyansai.blooddonation1;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.FirebaseTooManyRequestsException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

public class OtpVerification extends AppCompatActivity {
    private TextView MobileNumberField,WrongNumber;
    private EditText OtpField;
    private Button verify;
    private FirebaseAuth mAuth;
    // [END declare_auth]
    private ProgressDialog progressDialog;
    boolean mVerificationInProgress = false;
    String mVerificationId;
    PhoneAuthProvider.ForceResendingToken mResendToken;
    PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp_verification);
        mAuth = FirebaseAuth.getInstance();
        MobileNumberField =  (TextView)findViewById(R.id.MobileNumberField);
        WrongNumber = (TextView)findViewById(R.id.wrongnumber);
        OtpField = (EditText)findViewById(R.id.OtpField);
        verify = (Button)findViewById(R.id.verify);
        progressDialog = new ProgressDialog(this);
        WrongNumber.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(OtpVerification.this,SignUp.class);
                startActivity(intent);
                finish();
            }
        });
        verify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressDialog.setMessage("please wait....");
                progressDialog.show();
                progressDialog.setCancelable(false);
                progressDialog.setCanceledOnTouchOutside(false);
                Verify();
            }
        });
        String email = getIntent().getStringExtra("email");
        String password = getIntent().getStringExtra("password");
        String conformpassword = getIntent().getStringExtra("conformpassword");
        String mobilenumber = getIntent().getStringExtra("mobilenumber");
        String dateofbirth = getIntent().getStringExtra("dateofbirth");
        String address = getIntent().getStringExtra("address");
        Toast.makeText(this,email,Toast.LENGTH_LONG).show();
        Toast.makeText(this,password,Toast.LENGTH_LONG).show();
        Toast.makeText(this,conformpassword,Toast.LENGTH_LONG).show();
        Toast.makeText(this,mobilenumber,Toast.LENGTH_LONG).show();
        Toast.makeText(this,dateofbirth,Toast.LENGTH_LONG).show();
        Toast.makeText(this,address,Toast.LENGTH_LONG).show();
        MobileNumberField.setText("enter otp sent to : "+mobilenumber);
        mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

            @Override
            public void onVerificationCompleted(PhoneAuthCredential credential) {
                // This callback will be invoked in two situations:
                // 1 - Instant verification. In some cases the phone number can be instantly
                //     verified without needing to send or enter a verification code.
                // 2 - Auto-retrieval. On some devices Google Play services can automatically
                //     detect the incoming verification SMS and perform verificaiton without
                //     user action.
                // Log.d(TAG, "onVerificationCompleted:" + credential);
                Toast.makeText(OtpVerification.this,"verification done",Toast.LENGTH_LONG).show();
                progressDialog.dismiss();
            }

            @Override
            public void onVerificationFailed(FirebaseException e) {
                // This callback is invoked in an invalid request for verification is made,
                // for instance if the the phone number format is not valid.
                //Log.d(TAG, "onVerificationFailed", e);
                Toast.makeText(OtpVerification.this,"verification failed",Toast.LENGTH_LONG).show();
                if (e instanceof FirebaseAuthInvalidCredentialsException) {
                    // Invalid request
                    // ...
                    Toast.makeText(OtpVerification.this,"Invalid mobile number",Toast.LENGTH_LONG).show();
                    progressDialog.dismiss();
                } else if (e instanceof FirebaseTooManyRequestsException) {
                    // The SMS quota for the project has been exceeded
                    // ...
                    Toast.makeText(OtpVerification.this,"Quota Over",Toast.LENGTH_LONG).show();
                    progressDialog.dismiss();
                }

                // Show a message and update the UI
                // ...
            }

            @Override
            public void onCodeSent(String verificationId,
                                   PhoneAuthProvider.ForceResendingToken token) {
                // The SMS verification code has been sent to the provided phone number, we
                // now need to ask the user to enter the code and then construct a credential
                // by combining the code with a verification ID.
                // Log.d(TAG, "onCodeSent:" + verificationId);
                Toast.makeText(OtpVerification.this,"verification code sent to mobile number",Toast.LENGTH_LONG).show();
                progressDialog.dismiss();
                // Save verification ID and resending token so we can use them later
                mVerificationId = verificationId;
                mResendToken = token;

                // ...
            }
        };

    }

    private void Verify() {
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(mVerificationId, OtpField.getText().toString());
        Toast.makeText(OtpVerification.this,"ok",Toast.LENGTH_LONG).show();
        signInWithPhoneAuthCredential(credential);
    }
    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            //Log.d(TAG, "signInWithCredential:success");
                            Toast.makeText(OtpVerification.this,"verification done",Toast.LENGTH_LONG).show();
                            FirebaseUser user = task.getResult().getUser();
                            //progressDialog.dismiss();
                            //Intent intent = new Intent(OtpVerification.this,Blood.class);
                            //startActivity(intent);
                            //finish();
                            // ...
                        } else {
                            // Sign in failed, display a message and update the UI
                            //Log.w(TAG, "signInWithCredential:failure", task.getException());
                            if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                                // The verification code entered was invalid
                                Toast.makeText(OtpVerification.this,"verification failed code invalid",Toast.LENGTH_LONG).show();
                                progressDialog.dismiss();
                            }
                        }
                    }
                });
    }
}
