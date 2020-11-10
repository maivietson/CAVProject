package com.schickenon.cavproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class SignInActivity extends AppCompatActivity {

    private static final String TAG = "LoginActivity";

    private ProgressDialog progressDialog;
    private AppCompatButton btnSignIn;
    private AppCompatButton btnSignUp;
    private EditText editTextEmail;
    private EditText editTextPassword;

    private FirebaseAuth firebaseAuth;

    private CheckBox cbRemember;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        // init view
        btnSignIn = (AppCompatButton) findViewById(R.id.btnSignIn);
        btnSignUp = (AppCompatButton) findViewById(R.id.btnSignUp);
        editTextEmail = (EditText) findViewById(R.id.editTextEmail);
        editTextPassword = (EditText) findViewById(R.id.editTextPassword);
        cbRemember = (CheckBox) findViewById(R.id.cbRemember);

        // init progressDialog
        progressDialog = new ProgressDialog(this);

        // init firebase auth object
        firebaseAuth = FirebaseAuth.getInstance();

        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String email = editTextEmail.getText().toString();
                String password = editTextPassword.getText().toString();

                if(!validate()) {
                    return;
                }

                progressDialog.setMessage("Logging please wait...");
                progressDialog.show();

                firebaseAuth.signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener(SignInActivity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if(task.isSuccessful()) {
                                    Intent intent = new Intent(SignInActivity.this, MainActivity.class);
                                    startActivity(intent);
                                } else {
                                    progressDialog.setMessage("Account not exist!!");
                                }
                                progressDialog.dismiss();
                            }
                        });
            }
        });

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SignInActivity.this, SignUpActivity.class));
            }
        });
    }

    private boolean validate() {
        boolean valid = true;

        String email = editTextEmail.getText().toString();
        String password = editTextPassword.getText().toString();

        if(email.isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            editTextEmail.setError("Enter a valid email address");
            valid = false;
        } else {
            editTextEmail.setError(null);
        }

        if(password.isEmpty() || password.length() < 4 || password.length() > 18) {
            editTextPassword.setError("Enter a valid password");
            valid = false;
        } else {
            editTextPassword.setError(null);
        }

        return valid;

    }

    @Override
    public void onBackPressed() {
        return;
    }
}