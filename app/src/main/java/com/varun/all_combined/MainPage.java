package com.varun.all_combined;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

public class MainPage extends AppCompatActivity {

    TextView newUser,forgotPass;
    Button login;
    EditText lEmail,lPassword;

    FirebaseAuth auth;

    SignInButton signInButton;
    GoogleSignInClient mgoogleSignInClient;
    public static final int RC_SIGN_IN = 123;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_page);

        lEmail = findViewById(R.id.LoginEmail);
        lPassword = findViewById(R.id.LoginPassword);
        login = findViewById(R.id.Loginhomebutton);
        newUser = findViewById(R.id.RegistrationTextView);
        forgotPass = findViewById(R.id.ForgotPasswordTextView);
        signInButton = findViewById(R.id.googleSignIn);

        auth = FirebaseAuth.getInstance();

        requestGoogleSinnIn();

        forgotPass.setOnClickListener(view -> {
            startActivity(new Intent(MainPage.this,ForgotPasswordPage.class));
        });

        newUser.setOnClickListener(view -> {
            startActivity(new Intent(MainPage.this,RegisterPage.class));
        });

        login.setOnClickListener(view -> {
            loginUser();
        });

        signInButton.setOnClickListener(view -> {
            signIn();
        });

    }
    private void signIn() {
        Intent signIntent = mgoogleSignInClient.getSignInIntent();
        startActivityForResult(signIntent, RC_SIGN_IN);
    }

    private void requestGoogleSinnIn() {
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        mgoogleSignInClient = GoogleSignIn.getClient(this,gso);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RC_SIGN_IN){
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                GoogleSignInAccount account = task.getResult(ApiException.class);

                firebaseAuthWithGoogle(account.getIdToken());

            }catch (ApiException e){
                Toast.makeText(MainPage.this,"Failed"+ e.getMessage(), Toast.LENGTH_LONG).show();
            }
        }
    }

    private void firebaseAuthWithGoogle(String account) {

        AuthCredential authCredential = GoogleAuthProvider.getCredential(account,null);
        auth.signInWithCredential(authCredential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            Intent intent = new Intent(MainPage.this,HomePage.class);
                            startActivity(intent);
                        }
                        else{
                            Toast.makeText(MainPage.this, "Failed", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private void loginUser(){
        String email = lEmail.getText().toString();
        String pass = lPassword.getText().toString();

        if(TextUtils.isEmpty(email)||TextUtils.isEmpty(pass)){
            Toast.makeText(MainPage.this,"Enter Details", Toast.LENGTH_SHORT).show();
        }
        else{
            auth.signInWithEmailAndPassword(email,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful()){
                        if (auth.getCurrentUser().isEmailVerified()){
                            startActivity(new Intent(MainPage.this,HomePage.class));
                        }
                        else{
                            Toast.makeText(MainPage.this,"Un-Identified User",Toast.LENGTH_SHORT).show();
                        }
                    }
                    else
                    {
                        Toast.makeText(MainPage.this,"Login Failed "+task.getException()
                                .getMessage(),Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }

}