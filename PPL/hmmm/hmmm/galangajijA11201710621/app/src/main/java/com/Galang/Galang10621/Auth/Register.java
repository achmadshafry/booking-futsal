package com.Galang.Galang10621.Auth;


import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.Galang.Galang10621.BaseActivity;
import com.Galang.Galang10621.R;
import com.Galang.Galang10621.View.Dashboard;

public class Register extends BaseActivity  {

    private static final String TAG = "Register";
    private EditText mEdtEmail, mEdtPassword;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private ImageView mImageView;
    private TextView mTextError;
    private TextInputLayout mLayoutEmail, mLayoutPassword;
    private Button btnDaftar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mTextError = findViewById(R.id.textError);
        mEdtEmail = findViewById(R.id.editEmail);
        mEdtPassword = findViewById(R.id.editPassword);
        mLayoutEmail = findViewById(R.id.layoutEmail);
        mLayoutPassword = findViewById(R.id.layoutPassword);
        btnDaftar = findViewById(R.id.btnDaftar);

        mAuth = FirebaseAuth.getInstance();
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    Log.d(TAG, "onAuthStateChanged:signed_in:" + user.getUid());
                    Register.this.startActivity(new Intent(Register.this, Dashboard.class));
                } else {
                    Log.d(TAG, "onAuthStateChanged:signed_out");
                }
                mTextError.setText(null);
            }
        };

        btnDaftar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createAccount(mEdtEmail.getText().toString(), mEdtPassword.getText().toString());
            }
        });

    }

    @Override
    public void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }

    private void createAccount(String email, String password) {
        if (!validateForm()) {
            return;
        }
        showProgressDialog();
        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (!task.isSuccessful()) {
                    mTextError.setTextColor(Color.RED);
                    mTextError.setText(task.getException().getMessage());
                } else {
                    mTextError.setTextColor(Color.DKGRAY);
                    Register.this.startActivity(new Intent(Register.this, Login.class));
                }
                hideProgressDialog();
            }
        });
    }

    private boolean validateForm() {
        if (TextUtils.isEmpty(mEdtEmail.getText().toString())) {
            mLayoutEmail.setError("Required.");
            return false;
        } else if (TextUtils.isEmpty(mEdtPassword.getText().toString())) {
            mLayoutPassword.setError("Required.");
            return false;
        } else if (mEdtPassword.length() < 6) {
            mLayoutPassword.setError("Minimum 6 character");
            return false;
        } else {
            mLayoutEmail.setError(null);
            mLayoutPassword.setError(null);
            return true;
        }
    }

}
