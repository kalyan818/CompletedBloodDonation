package com.example.kalyansai.blooddonation1;

import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
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

import com.firebase.client.Firebase;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Hospitals extends AppCompatActivity implements AdapterView.OnItemSelectedListener{
    private Button capture,okbutton;
    private ImageView imageView1;
    private ListView list1;
    int result = 0;
    Uri ura;
    String valu;
    StorageReference storageReference;
    private ArrayList<String> items = new ArrayList<>();
    private Uri mImageCaptureUri;
    private static final int SELECTED_PICTURE=1;
    private static final int CAMERA_REQUEST = 1888;
    final String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    String uid = user.getEmail();
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
        imageView1= (ImageView)findViewById(R.id.imageView);
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
        imageView1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(cameraIntent, CAMERA_REQUEST);
                /*Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                mImageCaptureUri = Uri.fromFile(new File(Environment.getExternalStorageDirectory(),
                        "tmp_avatar_" + String.valueOf(System.currentTimeMillis()) + ".jpg"));

                intent.putExtra(android.provider.MediaStore.EXTRA_OUTPUT, mImageCaptureUri);
                startActivity(intent);*/
            }
        });
        okbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (spinner1.getSelectedItem().toString() == "Select")
                {
                    Toast.makeText(getApplicationContext(),"Select the relationship with the patient",Toast.LENGTH_LONG).show();
                }
                else {
                    UploadImage();
                }
            }
        });
        capture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i =new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(i,SELECTED_PICTURE);
            }
        });

    }

    private void UploadImage() {
        if (result == 1)
        {
            Toast.makeText(getApplicationContext(), "Uploading ", Toast.LENGTH_LONG).show();
            Uri file = Uri.fromFile(new File("/sdcard/camera_app/" + valu + ".jpg"));
            if (file != null) {
                final ProgressDialog prograssdialog = new ProgressDialog(this);
                prograssdialog.setTitle("Uploading");
                prograssdialog.show();
                StorageReference riversRef = storageReference.child("images/"+ valu + ".jpg");
                riversRef.putFile(file)
                        .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                // Get a URL to the uploaded content
                                prograssdialog.dismiss();
                                Toast.makeText(getApplicationContext(),"Image Uploaded Successfully ",Toast.LENGTH_LONG).show();
                                Intent  i = new Intent(getApplicationContext(),AddDataToList.class);
                                startActivity(i);
                                finish();
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

        }if (result == 2)
        {
            Toast.makeText(getApplicationContext(), "Uploading ", Toast.LENGTH_LONG).show();
            Uri file = ura;
            if (file != null) {
                final ProgressDialog prograssdialog = new ProgressDialog(this);
                prograssdialog.setTitle("Uploading");
                prograssdialog.show();
                valu = uid + timestamp;
                StorageReference riversRef = storageReference.child("images/" + valu +".jpg");
                riversRef.putFile(file)
                        .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                // Get a URL to the uploaded content
                                prograssdialog.dismiss();
                                Toast.makeText(getApplicationContext(),"Image Uploaded Successfully ",Toast.LENGTH_LONG).show();
                                Intent  i = new Intent(getApplicationContext(),AddDataToList.class);
                                startActivity(i);
                                finish();
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
        }if (result == 0){
            Toast.makeText(getApplicationContext(),"Select image first",Toast.LENGTH_LONG).show();
        }

    }
    private File getFile(){
        File folder = new File("sdcard/camera_app");

        if (!folder.exists())
        {
            folder.mkdir();
        }
        valu = uid + timestamp;
        File image_file = new File(folder, valu + ".jpg");
        return image_file;
    }
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == SELECTED_PICTURE)
        {
            switch (requestCode){
                case SELECTED_PICTURE:
                    if (resultCode==RESULT_OK){
                        Uri uri = data.getData();
                        ura = uri;
                        String[]projection = {MediaStore.Images.Media.DATA};

                        Cursor cursor = getContentResolver().query(uri, projection, null,null,null);
                        cursor.moveToFirst();


                        int columnIndex=cursor.getColumnIndex(projection[0]);
                        String filepath = cursor.getString(columnIndex);
                        cursor.close();

                        Bitmap yourselectedimage = BitmapFactory.decodeFile(filepath);
                        Drawable d = new BitmapDrawable(yourselectedimage);

                        imageView1.setImageDrawable(d);
                        result = 2;
                    }
                    break;
                default:
                    break;
            }
        }
        if (requestCode == CAMERA_REQUEST) {
            switch (requestCode){
                case  CAMERA_REQUEST:
                    if (resultCode == RESULT_OK){
                        Bitmap photo = (Bitmap) data.getExtras().get("data");
                        imageView1.setImageBitmap(photo);
                        File file  = getFile();
                        try{
                            OutputStream stream = null;
                            stream = new FileOutputStream(file);
                            photo.compress(Bitmap.CompressFormat.JPEG,100,stream);
                            stream.flush();
                            stream.close();
                            result = 1;
                        }catch (IOException e)
                        {
                            e.printStackTrace();
                        }
                    }
                    break;
                default:
                    break;
            }
        }
    }



    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}

