package com.Galang.Galang10621.Auth;

import androidx.annotation.NonNull;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.Galang.Galang10621.BaseActivity;
import com.Galang.Galang10621.R;
import com.Galang.Galang10621.View.Dashboard;

public class Login extends BaseActivity implements View.OnClickListener {

    private static final String TAG = "Login";
    private EditText mEdtEmail, mEdtPassword;
    private FirebaseAuth mAuth;
    private TextView mTextError;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private TextInputLayout mLayoutEmail, mLayoutPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mTextError = findViewById(R.id.textError);
        mEdtEmail = findViewById(R.id.editEmail);
        mEdtPassword = findViewById(R.id.editPassword);
        mLayoutEmail = findViewById(R.id.layoutEmail);
        mLayoutPassword = findViewById(R.id.layoutPassword);

        findViewById(R.id.btnMasuk).setOnClickListener(this);
        findViewById(R.id.buatAkun).setOnClickListener(this);

        mAuth = FirebaseAuth.getInstance();
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    Log.d(TAG, "onAuthStateChanged:signed_in:" + user.getUid());
                    Login.this.startActivity(new Intent(Login.this, Dashboard.class));
                } else {
                    Log.d(TAG, "onAuthStateChanged:signed_out");
                }
                mTextError.setText(null);
            }
        };
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

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnMasuk:
                signIn(mEdtEmail.getText().toString(), mEdtPassword.getText().toString());
                break;

            case R.id.buatAkun:
                Login.this.startActivity(new Intent(Login.this, Register.class));
                break;
        }
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }

    private void signIn(String email, String password) {
        if (!validateForm()) {
            return;
        }
        showProgressDialog();
        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                Log.d(TAG, "signInWithEmail:onComplete:" + task.isSuccessful());
                if (!task.isSuccessful()) {
                    mTextError.setTextColor(Color.RED);
                    mTextError.setText(task.getException().getMessage());
                } else {
                    mTextError.setTextColor(Color.DKGRAY);
                    Login.this.startActivity(new Intent(Login.this, Dashboard.class));
                    Login.this.finish();
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
