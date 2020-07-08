package com.Galang.Galang10621.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.Galang.Galang10621.Auth.Login;
import com.Galang.Galang10621.R;

public class DetailRiwayat extends AppCompatActivity {

    private static final String TAG = "DetailRiwayat";
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;

    private TextView noFak, tglJual, kode, jmlJual, hargaJual, totalJual;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_riwayat);

        // session
        mAuth = FirebaseAuth.getInstance();
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    Log.d(TAG, "onAuthStateChanged:signed_in:" + user.getUid());
                } else {
                    Log.d(TAG, "onAuthStateChanged:signed_out");
                    DetailRiwayat.this.startActivity(new Intent(DetailRiwayat.this, Login.class));
                }
            }
        };

        noFak = findViewById(R.id.noFakR);
        tglJual = findViewById(R.id.tglJualR);
        kode = findViewById(R.id.kodeR);
        jmlJual = findViewById(R.id.jmlObatR);
        hargaJual = findViewById(R.id.hargaSatuanR);
        totalJual = findViewById(R.id.totalJualR);

        Intent i = this.getIntent();
        String nofak = i.getExtras().getString("nofak");
        String tgl = i.getExtras().getString("tgl");
        String kd = i.getExtras().getString("kode");
        String jml = i.getExtras().getString("jml");
        String harga = i.getExtras().getString("harga");
        String total = i.getExtras().getString("total");

        noFak.setText(nofak);
        tglJual.setText(tgl);
        kode.setText(kd);
        jmlJual.setText(jml);
        hargaJual.setText(harga);
        totalJual.setText(total);

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
}