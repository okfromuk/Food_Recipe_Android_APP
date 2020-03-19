package com.example.newstart;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Handler;
import android.os.Bundle;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;
;import java.text.DateFormat;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    private static final int CHOOSE_IMAGE = 1;

    private Button chooseImage, btnUploadImage;
    private ImageView imgPreview;
    private EditText imgDescription,despription,Ingredients;
    ProgressDialog progressDialog ;


    private Uri imgUrl;

    private StorageReference mStorageRef;
    private DatabaseReference mDatabaseRef;

    private StorageTask mUploadTask;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().setTitle("Upload Recipe");
        chooseImage = findViewById(R.id.chooseImage);
        btnUploadImage = findViewById(R.id.btnUploadImage);
        despription=findViewById(R.id.DescriptionID);
        Ingredients=findViewById(R.id.Ingredients);
        imgDescription = findViewById(R.id.imgDescription);
        imgPreview = findViewById(R.id.imgPreview);
        progressDialog = new ProgressDialog(MainActivity.this);


        mStorageRef = FirebaseStorage.getInstance().getReference("All_Image_Uploads/");
        mDatabaseRef = FirebaseDatabase.getInstance().getReference("All_Image_Uploads_Database");



        btnUploadImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                if (mUploadTask != null && mUploadTask.isInProgress()) {
                    Toast.makeText(MainActivity.this, "Upload in progress", Toast.LENGTH_LONG).show();

                }
                else {
                    Toast.makeText(MainActivity.this, "Please Enter All Fields", Toast.LENGTH_SHORT).show();
                }
                if (imgDescription.getText().toString().isEmpty() ) {
                    imgDescription.setError("Please enter Recipe Name");
                    imgDescription.requestFocus();}
                else if (despription.getText().toString().isEmpty()){
                    despription.setError("Please enter Recipe Description");
                    despription.requestFocus();
                }
                else if (Ingredients.getText().toString().isEmpty() ) {
                    Ingredients.setError("Please enter Recipe Ingredients");
                    Ingredients.requestFocus();}
                else if(imgDescription.length()<2){
                    Toast.makeText(MainActivity.this, "Recipe Name Too Short !", Toast.LENGTH_SHORT).show();
                }
                else if(despription.length()<10){
                    Toast.makeText(MainActivity.this, "Recipe despription Too Short !", Toast.LENGTH_SHORT).show();

                } else if(Ingredients.length()<10){
                    Toast.makeText(MainActivity.this, "Recipe Ingredients Too Less !", Toast.LENGTH_SHORT).show();

                }
               else {
                    uploadImage();

                }
            }
        });


        chooseImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showFileChoose();
            }
        });
    }

    private void showFileChoose() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, CHOOSE_IMAGE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CHOOSE_IMAGE && resultCode == RESULT_OK && data != null && data.getData() != null) {
            imgUrl = data.getData();

            Picasso.with(this).load(imgUrl).into(imgPreview);
        }

    }

    private String getFileExtension(Uri uri) {
        ContentResolver contentResolver = getContentResolver();
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        return mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(uri));
    }

    private void uploadImage() {
        if (imgUrl != null) {
            progressDialog.setTitle("Uploading Your Recipe...");
            // Showing progressDialog.
            progressDialog.show();
            final StorageReference fileReference = mStorageRef.child(System.currentTimeMillis() + "." + getFileExtension(imgUrl));

            mUploadTask = fileReference.putFile(imgUrl)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            fileReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    Upload upload = new Upload(imgDescription.getText().toString().trim(),despription.getText().toString().trim(),Ingredients.getText().toString().trim(), uri.toString());
                                   // String uploadID = mDatabaseRef.push().getKey();
                                    String myCurrentDateTime= DateFormat.getDateTimeInstance().format(Calendar.getInstance().getTime());
                                    mDatabaseRef.child(myCurrentDateTime).setValue(upload);
                                    //mDatabaseRef.child(uploadID).setValue(upload);
                                    progressDialog.dismiss();
                                   // Toast.makeText(MainActivity.this, "Your Recipe is Uploaded successfully", Toast.LENGTH_LONG).show();
                                    Intent i = new Intent(MainActivity.this, RecipeUplaodedSuccesfully.class);
                                    startActivity(i);
                                    finish();
                                    imgPreview.setImageResource(R.drawable.images);
                                    imgDescription.setText("");
                                    despription.setText("");
                                    Ingredients.setText("");
                                }
                            });


                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            progressDialog.dismiss();
                            Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
                            progressDialog.dismiss();

                        }
                    })
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                       }
                    });
        }
    }

    @Override
    public void onBackPressed() {
        Intent i = new Intent(MainActivity.this, StartPage.class);
        startActivity(i);
        finish();
    }
}

