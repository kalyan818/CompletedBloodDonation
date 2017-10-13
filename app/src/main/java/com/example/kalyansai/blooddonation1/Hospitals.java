package com.example.kalyansai.blooddonation1;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class Hospitals extends AppCompatActivity implements AdapterView.OnItemSelectedListener{
    private Button capture,okbutton;
    private ImageView imageView;
    private ListView list1;
    StorageReference storageReference;
    private ArrayList<String> items = new ArrayList<>();
    private Uri mImageCaptureUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hospitals);
        FirebaseStorage storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();
        String hospital = getIntent().getStringExtra("Hospital");
        Toast.makeText(Hospitals.this,hospital,Toast.LENGTH_LONG).show();
        capture = (Button)findViewById(R.id.CapureButton);
        okbutton = (Button)findViewById(R.id.OkSubmit);
        imageView= (ImageView)findViewById(R.id.imageView);
        final Spinner spinner1 = (Spinner) findViewById(R.id.BloodRequiredFor);
        spinner1.setOnItemSelectedListener(this);
        List<String> categories = new ArrayList<String>();
        categories.add("Select");
        categories.add("Mother");
        categories.add("Father");
        categories.add("Wife");
        categories.add("Son");
        categories.add("Daughter");
        categories.add("Friend");
        categories.add("Other");
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, categories);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner1.setAdapter(dataAdapter);
        capture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                mImageCaptureUri = Uri.fromFile(new File(Environment.getExternalStorageDirectory(),
                        "tmp_avatar_" + String.valueOf(System.currentTimeMillis()) + ".jpg"));

                intent.putExtra(android.provider.MediaStore.EXTRA_OUTPUT, mImageCaptureUri);
                startActivity(intent);
            }
        });
        okbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UploadImage();
            }
        });

    }

    private void UploadImage() {
        Toast.makeText(getApplicationContext(), "Uploading ", Toast.LENGTH_LONG).show();
        Uri file = Uri.fromFile(new File("/sdcard/DCIM/ABC.jpg"));
        if (file != null) {
            final ProgressDialog prograssdialog = new ProgressDialog(this);
            prograssdialog.setTitle("Uploading");
            prograssdialog.show();
            StorageReference riversRef = storageReference.child("images/ABC.jpg");
            riversRef.putFile(file)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            // Get a URL to the uploaded content
                            prograssdialog.dismiss();
                            Toast.makeText(getApplicationContext(),"Image Uploaded Successfully ",Toast.LENGTH_LONG).show();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception exception) {
                            // Handle unsuccessful uploads
                            Toast.makeText(getApplicationContext(), exception.getMessage(), Toast.LENGTH_LONG).show();
                            // ...
                        }
                    })
            .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                    double prograss =  (100.0 * taskSnapshot.getBytesTransferred())/taskSnapshot.getTotalByteCount();
                    prograssdialog.setMessage(((int) prograss) + "% Uploaded....");
                }
            });
        }
    }


    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}

