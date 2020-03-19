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

public class signup extends AppCompatActivity {
    EditText user_nameID, Email_ID,PassID,Con_ID,Phnum;
    Button Sign_button_ID,Already_ID;
    FirebaseAuth mFirebaseAuth;

    ProgressDialog progressDialog ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        getSupportActionBar().setTitle("Ok From Uk Signup form");

        mFirebaseAuth = FirebaseAuth.getInstance();
        progressDialog = new ProgressDialog(signup.this);

        user_nameID = findViewById(R.id.user_nameID);
        Email_ID = (EditText) findViewById(R.id.Email_ID);
        PassID = (EditText) findViewById(R.id.PassID);
        Con_ID = (EditText) findViewById(R.id.Con_ID);
        Phnum=(EditText)findViewById(R.id.PhoneID);
        Sign_button_ID = (Button) findViewById(R.id.Sign_button_ID);
        Already_ID = (Button) findViewById(R.id.Already_ID);
        Sign_button_ID.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String email = Email_ID.getText().toString();
                String pwd = PassID.getText().toString();
                String confirm= Con_ID.getText().toString();
                String Username=  user_nameID.getText().toString();
                String Phno=  Phnum.getText().toString();

                if (email.isEmpty()) {
                    Email_ID.setError("Please enter email id");
                    Email_ID.requestFocus();
                } else if (pwd.isEmpty()) {
                    PassID.setError("Please enter your password");
                    PassID.requestFocus();
                }
                else if (confirm.isEmpty()) {
                    Con_ID.setError("Please enter Confirm password");
                    Con_ID.requestFocus();
                }
                else if (Username.isEmpty()) {
                    user_nameID.setError("Please enter your user name");
                    user_nameID.requestFocus();
                }
                else if(pwd.length()<5){
                    Toast.makeText(signup.this, "Password Too Short !", Toast.LENGTH_SHORT).show();

                }
                else if (Phno.isEmpty()) {
                    Phnum.setError("Please enter your Phone Number");
                    Phnum.requestFocus();
                }
                else if(!confirm.equals(pwd)){
                    Toast.makeText(signup.this, "Password and Confirm Password are not same", Toast.LENGTH_SHORT).show();

                }

                else if (email.isEmpty() && pwd.isEmpty()) {
                    Toast.makeText(signup.this, "Fields Are Empty!", Toast.LENGTH_SHORT).show();
                } else if (!(email.isEmpty() && pwd.isEmpty())) {
                    progressDialog.setTitle("Signing In ...");
                    // Showing progressDialog.
                    progressDialog.show();
                    mFirebaseAuth.createUserWithEmailAndPassword(email, pwd).addOnCompleteListener(signup.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (!task.isSuccessful()) {
                                progressDialog.dismiss();
                                Toast.makeText(signup.this, "SignUp Unsuccessful, Please Try Again", Toast.LENGTH_SHORT).show();
                            } else {
                                progressDialog.dismiss();
                                startActivity(new Intent(signup.this, StartPage.class));
                                finish();
                            }
                        }
                    });
                } else {
                    progressDialog.dismiss();
                    Toast.makeText(signup.this, "Error Occurred!", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
    public void Btn_Login_form (View view){
        Intent i = new Intent(signup.this, Login.class);
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

