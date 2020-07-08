package com.Galang.Galang10621.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.squareup.picasso.Picasso;
import com.Galang.Galang10621.Auth.Login;
import com.Galang.Galang10621.R;

public class DetailObat extends AppCompatActivity {

    private static final String TAG = "DetailObat";
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;

    private TextView kodeDetail, namaDetail, satuanDetail, jumlahDetail, expiredDetail, hargaDetail;
    private ImageView imgDetail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_obat);

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
                    DetailObat.this.startActivity(new Intent(DetailObat.this, Login.class));
                }
            }
        };

        kodeDetail = findViewById(R.id.kodeDetail);
        namaDetail = findViewById(R.id.namaDetail);
        satuanDetail = findViewById(R.id.satuanDetail);
        jumlahDetail = findViewById(R.id.jmlDetail);
        expiredDetail = findViewById(R.id.expiredDetail);
        hargaDetail = findViewById(R.id.hargaDetail);
        imgDetail = findViewById(R.id.imgDetail);


        Intent i = this.getIntent();
        String kode = i.getExtras().getString("kode");
        String nama = i.getExtras().getString("nama");
        String satuan = i.getExtras().getString("satuan");
        String jumlah = i.getExtras().getString("jumlah");
        String expired = i.getExtras().getString("expired");
        String harga = i.getExtras().getString("harga");
        String gambar = i.getExtras().getString("gambar");


        kodeDetail.setText(kode);
        namaDetail.setText(nama);
        satuanDetail.setText("Satuan : "+satuan);
        jumlahDetail.setText("Stok : "+jumlah);
        expiredDetail.setText("Expired : "+expired);
        hargaDetail.setText("Rp "+harga);
        Picasso.get().load(gambar).placeholder(R.mipmap.ic_launcher).fit().centerCrop().into(imgDetail);

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
