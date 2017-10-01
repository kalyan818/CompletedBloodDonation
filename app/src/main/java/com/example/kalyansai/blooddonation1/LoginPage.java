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
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginPage extends AppCompatActivity  implements View.OnClickListener{
    private Button buttonSignIn;
    private TextView ForgetPass;
    private EditText editTextEmail;
    private EditText editTextPassword;
    private TextView textViewSignup;
    private ProgressDialog progressDialog;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_page1);
        // stop listening to touches
        //getWindow().addFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
        // remove the icon from App Drawer
        //PackageManager p = getPackageManager();
        //ComponentName componentName = new ComponentName(this, com.example.kalya.govtpolytechnic.LoginPage.class);
        //p.setComponentEnabledSetting(componentName, PackageManager.COMPONENT_ENABLED_STATE_DISABLED, PackageManager.DONT_KILL_APP);
        firebaseAuth = FirebaseAuth.getInstance();
        //to know weather the user is loged in into our app or not if he loged in then open another activity
        if (firebaseAuth.getCurrentUser() != null){
            finish();
            startActivity(new Intent(getApplicationContext(),Blood.class));
        }
        editTextEmail = (EditText) findViewById(R.id.editTextEmail);
        editTextPassword = (EditText) findViewById(R.id.editTextPassword);
        buttonSignIn = (Button) findViewById(R.id.buttonSignin);
        ForgetPass = (TextView) findViewById(R.id.ForgetPassword);
        progressDialog = new ProgressDialog(this);
        ForgetPass.setOnClickListener(this);
        buttonSignIn.setOnClickListener(this);
    }
    private void ForgetPassword(){
        String email = editTextEmail.getText().toString().trim();

        if (TextUtils.isEmpty(email)){
            //email is empty
            Toast.makeText(this, "please enter the email which you want to reset password", Toast.LENGTH_SHORT).show();
            return;
        }
        //loading dialog box with message loading
        progressDialog.setMessage("Loading....");
        progressDialog.show();
        //if touch outside or on it the loading dialog box should not disapper
        progressDialog.setCancelable(false);
        progressDialog.setCanceledOnTouchOutside(false);
        //check the internet connection
        ConnectivityManager connectivityManager = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        if(connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) {
            //get instance of firebase
            FirebaseAuth auth = FirebaseAuth.getInstance();
            //send a reset of password to his mail id
            auth.sendPasswordResetEmail(email)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                //if sucess
                                Toast.makeText(getApplicationContext(), "sucess", Toast.LENGTH_SHORT).show();
                                //below one is to dismiss or colapse the loading box
                                progressDialog.dismiss();
                            }
                            else
                            {
                                //if not sucess
                                Toast.makeText(getApplicationContext(), "unsucess", Toast.LENGTH_SHORT).show();
                                progressDialog.dismiss();
                            }
                        }
                    });
        }
        else {
            Toast.makeText(this, "sorry you are not connected to any network", Toast.LENGTH_SHORT).show();
            progressDialog.dismiss();
            return;
        }
        progressDialog.dismiss();

    }
    private void userLogin(){
        String email = editTextEmail.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();

        if (TextUtils.isEmpty(email)){
            //email is empty
            Toast.makeText(this, "please enter the email", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(password)){
            //password is empty 2
            Toast.makeText(this, "please enter the password", Toast.LENGTH_SHORT).show();
            return;
        }

        progressDialog.setMessage("Logining.......");
        progressDialog.show();
        progressDialog.setCancelable(false);
        progressDialog.setCanceledOnTouchOutside(false);
//to check weather he is right user or not (Authentication)
        firebaseAuth.signInWithEmailAndPassword(email,password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        progressDialog.dismiss();
                        if (task.isSuccessful()){
                            finish();
                            startActivity(new Intent(getApplicationContext(),Blood.class));
                        }else
                        {
                            Toast.makeText(LoginPage.this, "incorrect username & password", Toast.LENGTH_SHORT).show();
                            progressDialog.dismiss();
                        }
                    }
                });
    }

    @Override
    public void onClick(View view) {
        if (view == buttonSignIn){
            //when the login button is clicked
            //check internet connection
            ConnectivityManager connectivityManager = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
            if(connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                    connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) {
                //pass control to login method
                userLogin();
            }
            else {
                Toast.makeText(this, "sorry you are not connected to any network", Toast.LENGTH_SHORT).show();
            }
        }
        if (view == ForgetPass){
            //on click on forgetpassword
            //control transfer to forgetpassword method
            ForgetPassword();
        }
    }
}
