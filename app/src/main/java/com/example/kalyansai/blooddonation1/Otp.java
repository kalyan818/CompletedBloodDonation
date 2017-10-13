package com.example.kalyansai.blooddonation1;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

import java.util.concurrent.TimeUnit;

public class Otp extends AppCompatActivity {
    private EditText MobileNumber,OtpBox;
    private Button GetOtp,VerifyOtp;
    // [START declare_auth]
    private FirebaseAuth mAuth;
    private ProgressDialog progressDialog1;
    private FirebaseAuth firebaseAuth;
    // [END declare_auth]

    boolean mVerificationInProgress = false;
    String mVerificationId;
    private int job=0;
    PhoneAuthProvider.ForceResendingToken mResendToken;
    PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp);
        firebaseAuth = FirebaseAuth.getInstance();
        mAuth = FirebaseAuth.getInstance();
        MobileNumber= (EditText)findViewById(R.id.MobileNumberBox);
        OtpBox = (EditText)findViewById(R.id.OtpField);
        GetOtp = (Button)findViewById(R.id.SendOtp);
        VerifyOtp = (Button)findViewById(R.id.verify);
        VerifyOtp.setEnabled(false);
        final String mobileNumber = getIntent().getStringExtra("mobileNumber");
        String dateofbirth = getIntent().getStringExtra("dateofbirth");
        String address = getIntent().getStringExtra("address");
        progressDialog1 = new ProgressDialog(this);
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
                Toast.makeText(Otp.this,"this number is already registered",Toast.LENGTH_LONG).show();
                progressDialog1.dismiss();
                Intent intent = new Intent(Otp.this,Blood.class);
                startActivity(intent);
                finish();
            }

            @Override
            public void onVerificationFailed(FirebaseException e) {
                // This callback is invoked in an invalid request for verification is made,
                // for instance if the the phone number format is not valid.
                //Log.d(TAG, "onVerificationFailed", e);
                Toast.makeText(Otp.this,"verification failed",Toast.LENGTH_LONG).show();
                progressDialog1.dismiss();
                if (e instanceof FirebaseAuthInvalidCredentialsException) {
                    // Invalid request
                    // ...
                    Toast.makeText(Otp.this,"Invalid mobile number",Toast.LENGTH_LONG).show();
                    progressDialog1.dismiss();

                } else if (e instanceof FirebaseTooManyRequestsException) {
                    // The SMS quota for the project has been exceeded
                    // ...
                    Toast.makeText(Otp.this,"Quota Over",Toast.LENGTH_LONG).show();
                    progressDialog1.dismiss();

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
                progressDialog1.dismiss();
                Toast.makeText(Otp.this,"verification code sent to mobile number",Toast.LENGTH_LONG).show();
                VerifyOtp.setEnabled(true);

                // Save verification ID and resending token so we can use them later
                mVerificationId = verificationId;
                mResendToken = token;

                // ...
            }
        };

        GetOtp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ConnectivityManager connectivityManager = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
                if(connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                        connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) {
                    String otp1 = MobileNumber.getText().toString();
                    if (TextUtils.isEmpty(otp1)) {
                        Toast.makeText(Otp.this, "enter Mobile Number", Toast.LENGTH_LONG).show();
                    }else
                    {
                        if ((otp1.length()<=9)||(otp1.length()>=11))
                        {
                            Toast.makeText(Otp.this, "please enter 10 digit mobile number", Toast.LENGTH_LONG).show();
                        }else {
                            if (!otp1.equals(mobileNumber))
                            {
                                Toast.makeText(Otp.this,"previous you entered another number", Toast.LENGTH_LONG).show();
                                Toast.makeText(Otp.this,otp1, Toast.LENGTH_LONG).show();
                                Toast.makeText(Otp.this,mobileNumber, Toast.LENGTH_LONG).show();
                            }else {
                                progressDialog1.setMessage("Loading.....");
                                progressDialog1.show();
                                progressDialog1.setCancelable(false);
                                progressDialog1.setCanceledOnTouchOutside(false);
                                //pass control to login method
                                PhoneAuthProvider.getInstance().verifyPhoneNumber(
                                        "+91" + MobileNumber.getText().toString(),        // Phone number to verify
                                        60,                 // Timeout duration
                                        TimeUnit.SECONDS,   // Unit of timeout
                                        Otp.this,               // Activity (for callback binding)
                                        mCallbacks);
                            }
                        }
                    }
                }
                else {
                    Toast.makeText(Otp.this, "sorry you are not connected to any network", Toast.LENGTH_SHORT).show();
                }// OnVerificationStateChangedCallbacks
            }
        });
        VerifyOtp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //when the login button is clicked
                //check internet connection
                ConnectivityManager connectivityManager = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
                if(connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                        connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) {
                    //pass control to login method
                    String otp = OtpBox.getText().toString();
                    if (TextUtils.isEmpty(otp)) {
                        Toast.makeText(Otp.this, "enter otp", Toast.LENGTH_LONG).show();
                    }
                    else {
                        progressDialog1.show();
                        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(mVerificationId, otp);
                        Toast.makeText(Otp.this, "ok", Toast.LENGTH_LONG).show();
                        signInWithPhoneAuthCredential(credential);
                    }
                }
                     else {
                        Toast.makeText(Otp.this, "sorry you are not connected to any network", Toast.LENGTH_SHORT).show();
                    }
            }
        });
    }
    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            //Log.d(TAG, "signInWithCredential:success");
                            Toast.makeText(Otp.this,"verification done",Toast.LENGTH_LONG).show();
                            FirebaseUser user = task.getResult().getUser();
                            UserLogin();
                            // ...
                        } else {
                            // Sign in failed, display a message and update the UI
                            //Log.w(TAG, "signInWithCredential:failure", task.getException());
                            if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                                // The verification code entered was invalid
                                Toast.makeText(Otp.this,"verification failed code invalid",Toast.LENGTH_LONG).show();
                                progressDialog1.dismiss();
                            }
                        }
                    }
                });
    }

    private void UserLogin(){
        String email = getIntent().getStringExtra("email");
        String password = getIntent().getStringExtra("password");
        Toast.makeText(Otp.this,email,Toast.LENGTH_LONG).show();
        Toast.makeText(Otp.this,password,Toast.LENGTH_LONG).show();
        firebaseAuth.signInWithEmailAndPassword(email,password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            progressDialog1.dismiss();
                            Toast.makeText(Otp.this,"sucessfull",Toast.LENGTH_LONG).show();
                            Intent intent = new Intent(Otp.this,Blood.class);
                            startActivity(intent);
                            finish();
                        }else
                        {
                            Toast.makeText(Otp.this, "incorrect username & password", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}
