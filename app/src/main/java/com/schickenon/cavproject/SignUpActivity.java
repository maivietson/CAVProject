package com.schickenon.cavproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class SignUpActivity extends AppCompatActivity {

    private AppCompatButton btnRegister;
    private EditText editTextEmail;
    private EditText editTextPassword;

    private TextView hasAccount;

    private ProgressDialog progressDialog;

    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        // init view
        editTextEmail = (EditText) findViewById(R.id.editTextEmail);
        editTextPassword = (EditText) findViewById(R.id.editTextPassword);
        btnRegister = (AppCompatButton) findViewById(R.id.btnRegister);
        hasAccount = (TextView) findViewById(R.id.tvHasAccount);

        // init progressDialog
        progressDialog = new ProgressDialog(this);

        // init firebase auth object
        firebaseAuth = FirebaseAuth.getInstance();

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = editTextEmail.getText().toString();
                String password = editTextPassword.getText().toString();

                if(!validate()) {
                    return;
                }

                progressDialog.setMessage("Registering please wait...");
                progressDialog.show();

                // Create a new user
                firebaseAuth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(SignUpActivity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if(task.isSuccessful()) {
                                    //Toast.makeText(NetflixSignUp.this, "Successfully registered", Toast.LENGTH_LONG).show();
                                    Intent intent = new Intent(SignUpActivity.this, MovieActivity.class);
                                    startActivity(intent);
                                } else {
                                    Toast.makeText(SignUpActivity.this, "Registration Error", Toast.LENGTH_LONG).show();
                                }
                                progressDialog.dismiss();
                            }
                        });
            }
        });

        hasAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SignUpActivity.this, SignInActivity.class));
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