package com.Galang.Galang10621.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;
import com.Galang.Galang10621.Auth.Login;
import com.Galang.Galang10621.Model.Obat;
import com.Galang.Galang10621.R;

import java.util.ArrayList;
import java.util.List;

public class DetailTransaksi extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "DetailTransaksi";
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;

    private TextView kodeDetail, namaDetail, satuanDetail, jumlahDetail, expiredDetail, hargaDetail;
    private ImageView imgDetail;

    private ElegantNumberButton numberButton;

    private int jmlStok, hargaSatuan, posisi;

    private String kode, nama, satuan, jumlah, expired, harga, gambar;

    private DatabaseReference mDatabaseRef;
    private List<Obat> mObats;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_transaksi);

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
                    DetailTransaksi.this.startActivity(new Intent(DetailTransaksi.this, Login.class));
                }
            }
        };

        kodeDetail = findViewById(R.id.kodeDetailT);
        namaDetail = findViewById(R.id.namaDetailT);
        satuanDetail = findViewById(R.id.satuanDetailT);
        jumlahDetail = findViewById(R.id.jmlDetailT);
        expiredDetail = findViewById(R.id.expiredDetailT);
        hargaDetail = findViewById(R.id.hargaDetailT);
        imgDetail = findViewById(R.id.imgDetailT);


        Intent i = this.getIntent();
        kode = i.getExtras().getString("kode");
        nama = i.getExtras().getString("nama");
        satuan = i.getExtras().getString("satuan");
        jumlah = i.getExtras().getString("jumlah");
        expired = i.getExtras().getString("expired");
        harga = i.getExtras().getString("harga");
        gambar = i.getExtras().getString("gambar");


        hargaSatuan = Integer.parseInt(i.getExtras().getString("harga"));
        jmlStok = Integer.parseInt(i.getExtras().getString("jumlah"));
        posisi = Integer.parseInt(i.getExtras().getString("position"));

        kodeDetail.setText(kode);
        namaDetail.setText(nama);
        satuanDetail.setText("Satuan : "+satuan);
        jumlahDetail.setText("Stok : "+jumlah);
        expiredDetail.setText("Expired : "+expired);
        hargaDetail.setText(harga);
        Picasso.get().load(gambar).placeholder(R.mipmap.ic_launcher).fit().centerCrop().into(imgDetail);

        // jual
        mObats = new ArrayList<>();

        mDatabaseRef = FirebaseDatabase.getInstance().getReference("Barang");

        mDatabaseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    Obat obat = postSnapshot.getValue(Obat.class);
                    obat.setKey(postSnapshot.getKey());
                    mObats.add(obat);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(DetailTransaksi.this, databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        numberButton = (ElegantNumberButton) findViewById(R.id.number_btn);
        findViewById(R.id.btnJual).setOnClickListener(this);
        findViewById(R.id.btnBatal).setOnClickListener(this);
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
        switch (v.getId()){
            case R.id.btnJual:
                final int jmlJual = Integer.parseInt(numberButton.getNumber());
                int stok = jmlStok - jmlJual;
                final int totalHarga = jmlJual * hargaSatuan;

                Obat posObat = mObats.get(posisi);

                Obat obat = new Obat(kode, nama, satuan, String.valueOf(stok), expired, harga, gambar);

                mDatabaseRef.child(posObat.getKey()).setValue(obat).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(DetailTransaksi.this, "Sedang diproses", Toast.LENGTH_LONG).show();

                        Intent intent = new Intent(DetailTransaksi.this, Bayar.class);
                        intent.putExtra("kode", kode);
                        intent.putExtra("nama",nama);
                        intent.putExtra("satuan",satuan);
                        intent.putExtra("jumlah",jumlah);
                        intent.putExtra("expired",expired);
                        intent.putExtra("harga",harga);
                        intent.putExtra("gambar",gambar);
                        intent.putExtra("position",posisi);
                        intent.putExtra("totalharga",String.valueOf(totalHarga));
                        intent.putExtra("totaljual",String.valueOf(jmlJual));
                        startActivity(intent);
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(DetailTransaksi.this, e.getMessage(), Toast.LENGTH_LONG).show();
                    }
                });

                break;
            case R.id.btnBatal:
                DetailTransaksi.this.startActivity(new Intent(DetailTransaksi.this, Transaksi.class));
                break;
        }
    }

}
