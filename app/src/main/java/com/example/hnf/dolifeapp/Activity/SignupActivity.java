package com.example.hnf.dolifeapp.Activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.hnf.dolifeapp.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SignupActivity extends AppCompatActivity {

    private EditText editTextEmail;
    private EditText editTextUsername;
    private EditText editTextPass;
    private EditText editTextConfirmPass;
    private ImageButton buttonRegister;

    private DatabaseReference mDatabase;
    private ProgressDialog progressDialog;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference().child("Users");

        progressDialog = new ProgressDialog(this);

        editTextEmail = (EditText) findViewById(R.id.etEmail);
        editTextUsername = (EditText) findViewById(R.id.etUser);
        editTextPass = (EditText) findViewById(R.id.etPass);
        editTextConfirmPass = (EditText) findViewById(R.id.etConfirm);

        buttonRegister = (ImageButton) findViewById(R.id.imgbtnSignup);
        buttonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (view == buttonRegister) {
                    registerUser();
                }
            }
        });
    }

    private void registerUser() {
        String email = editTextEmail.getText().toString().trim();
        final String username = editTextUsername.getText().toString().trim();
        String password = editTextPass.getText().toString().trim();
        String confirmPass = editTextConfirmPass.getText().toString().trim();

        if (email.isEmpty()) {
            editTextEmail.setError("Email is required");
            editTextEmail.requestFocus();
            return;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            editTextEmail.setError("Please enter a valid email");
            editTextEmail.requestFocus();
            return;
        }

        if (username.isEmpty()) {
            editTextUsername.setError("Username is required");
            editTextUsername.requestFocus();
            return;
        }

        if (password.isEmpty()) {
            editTextPass.setError("Password is required");
            editTextPass.requestFocus();
            return;
        }

        if (password.length() < 6) {
            editTextPass.setError("Minimum lenght of password should be 6");
            editTextPass.requestFocus();
            return;
        }

        if (confirmPass.isEmpty()) {
            editTextConfirmPass.setError("Confirm Password is required");
            editTextConfirmPass.requestFocus();
            return;
        }

        if (password == confirmPass) {
            editTextConfirmPass.setError("Confirm password incorrect");
            editTextConfirmPass.requestFocus();
            return;
        }

        progressDialog.setMessage("Registering User...");
        progressDialog.show();

        (mAuth.createUserWithEmailAndPassword(email, password)).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {

                    String user_id = mAuth.getCurrentUser().getUid();
                    DatabaseReference curent_user_db = mDatabase.child(user_id);

                    curent_user_db.child("username").setValue(username);

                    Intent mainIntent = new Intent(SignupActivity.this, SigninActivity.class);
                    mainIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(mainIntent);

                    Toast.makeText(SignupActivity.this, "Successfully Registered", Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();

                } else {
                    Toast.makeText(SignupActivity.this, "Email is already in use", Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();
                }
            }
        });
    }

}
