package com.example.newstart;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

public class StartPage extends AppCompatActivity {
    Button btnLogout;
    private Button viewGallery;

    FirebaseAuth mFirebaseAuth;
    private FirebaseAuth.AuthStateListener mAuthStateListener;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_page);
        getSupportActionBar().setTitle("Ok From UK App");
        btnLogout  = (Button)findViewById(R.id.button3);
        viewGallery = findViewById(R.id.viewGallery);
        viewGallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(StartPage.this, ViewImageActivity.class);
                startActivity(intent);
            }
        });


        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mFirebaseAuth.getInstance().signOut();
                Intent intToMain = new Intent(StartPage.this, Login.class);
                startActivity(intToMain);
                finish();
            }
        });

    }

    public void UploadRecipe(View view) {
        Intent i = new Intent(StartPage.this, MainActivity.class);
        startActivity(i);
        finish();
    }
    @Override
    public void onBackPressed() {
        Toast.makeText(StartPage.this, "Please Log Out", Toast.LENGTH_SHORT).show();

    }
}
