package com.varun.all_combined;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class RegisterPage extends AppCompatActivity {

    TextView LoginButton;
    Button RegistrationButton;
    EditText rEmail,rPassword;

    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_page);

        LoginButton = findViewById(R.id.LoginHereRegister);
        RegistrationButton = findViewById(R.id.RegistrationButton);
        rEmail = findViewById(R.id.LoginEmail);
        rPassword = findViewById(R.id.LoginPassword);

        auth = FirebaseAuth.getInstance();

        RegistrationButton.setOnClickListener(view -> {
            createUser();
        });

        LoginButton.setOnClickListener(view -> {
            startActivity(new Intent(RegisterPage.this,RegistrationSplashImage.class));
        });
    }
    private void createUser() {
        String email = rEmail.getText().toString();
        String pass = rPassword.getText().toString();

        if (TextUtils.isEmpty(email) || TextUtils.isEmpty(pass)) {
            Toast.makeText(RegisterPage.this,"Enter Details",Toast.LENGTH_SHORT).show();
        }
        else{
            auth.createUserWithEmailAndPassword(email,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful()){
                        FirebaseUser user = auth.getCurrentUser();
                        user.sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                Toast.makeText(RegisterPage.this,"Registration Verification link send",Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(RegisterPage.this, RegistrationSplashImage.class));
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(RegisterPage.this,"Mail sending fail",Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                    else{
                        Toast.makeText(RegisterPage.this,"Registration Failed" + task.getException().getMessage(),Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }
}