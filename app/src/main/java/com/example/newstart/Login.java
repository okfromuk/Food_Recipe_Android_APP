package com.example.newstart;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Login extends AppCompatActivity {
    EditText emailId, password;
    Button btnSignIn;
    Button tvSignUp;
    FirebaseAuth mFirebaseAuth;
    ProgressDialog progressDialog ;

    private FirebaseAuth.AuthStateListener mAuthStateListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getSupportActionBar().setTitle("OK From UK Login Form");
        progressDialog = new ProgressDialog(Login.this);

        mFirebaseAuth = FirebaseAuth.getInstance();
        emailId = findViewById(R.id.login_Email_ID);
        password = findViewById(R.id.Login_Pass_ID);
        btnSignIn = findViewById(R.id.Login_button_id);
        tvSignUp = findViewById(R.id.Register_ID);
        mAuthStateListener=new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser mFirebaseUser = mFirebaseAuth.getCurrentUser();
                if( mFirebaseUser != null ){
                    progressDialog.dismiss();
                    Toast.makeText(Login.this,"You are logged in",Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(Login.this, StartPage.class);
                    startActivity(i);
                }
                else{
                    progressDialog.dismiss();
                    Toast.makeText(Login.this,"Please Login",Toast.LENGTH_SHORT).show();
                }
            }
        };

        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = emailId.getText().toString();
                String pwd = password.getText().toString();
                if(email.isEmpty()){
                    emailId.setError("Please enter email id");
                    emailId.requestFocus();
                }
                else  if(pwd.isEmpty()){
                    password.setError("Please enter your password");
                    password.requestFocus();
                }
                else  if(email.isEmpty() && pwd.isEmpty()){
                    progressDialog.dismiss();
                    Toast.makeText(Login.this,"Fields Are Empty!",Toast.LENGTH_SHORT).show();
                }
                else  if(!(email.isEmpty() && pwd.isEmpty())){
                    progressDialog.setTitle("Loging In ...");
                    // Showing progressDialog.
                    progressDialog.show();
                    mFirebaseAuth.signInWithEmailAndPassword(email,pwd).addOnCompleteListener(Login.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(!task.isSuccessful()){
                                progressDialog.dismiss();
                                Toast.makeText(Login.this,"Login Error, Please Login Again",Toast.LENGTH_SHORT).show();
                            }
                            else{
                                progressDialog.dismiss();
                                Intent intToHome = new Intent(Login.this,StartPage.class);
                                startActivity(intToHome);
                                finish();
                            }
                        }
                    });
                }
                else{
                    progressDialog.dismiss();
                    Toast.makeText(Login.this,"Error Occurred!",Toast.LENGTH_SHORT).show();

                }

            }
        });
    }

    public void Btn_signup_form(View view) {
        Intent i = new Intent(Login.this, signup.class);
        startActivity(i);
        finish();
    }
    @Override
    public void onBackPressed() {
        AlertDialog.Builder aleartDialog= new AlertDialog.Builder(this);
        aleartDialog.setTitle("Confirm Exit");
        aleartDialog.setMessage("Are You Sure You Want To Exit ?");
        aleartDialog.setCancelable(false);

        aleartDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                moveTaskToBack(true);
                android.os.Process.killProcess(android.os.Process.myPid());
                onBackPressed();
                finish();

            }
        });
        aleartDialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        AlertDialog alertDialog=aleartDialog.create();
        alertDialog.show();
}
}
