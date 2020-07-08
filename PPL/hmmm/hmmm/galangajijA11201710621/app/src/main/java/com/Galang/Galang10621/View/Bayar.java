package com.Galang.Galang10621.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;
import com.Galang.Galang10621.Auth.Login;
import com.Galang.Galang10621.Model.Jual;
import com.Galang.Galang10621.R;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

public class Bayar extends AppCompatActivity {

    private static final String TAG = "Bayar";
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;

    private TextView noFak, tglBayar, kodeBayar, namaBayar, hargaBayar, hargaTotalBayar, jmlTotalBayar;
    private ImageView imgBayar;

    private DatabaseReference mDatabaseRef;

    private Button btnBayar;

    private String noFaktur, tglJual, kode, jmlTotal, harga, hargaTotal, nama, gambar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bayar);

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
                    Bayar.this.startActivity(new Intent(Bayar.this, Login.class));
                }
            }
        };

        btnBayar = findViewById(R.id.btnBayar);

        noFak = findViewById(R.id.noFak);
        tglBayar = findViewById(R.id.tglJual);
        kodeBayar = findViewById(R.id.kodeB);
        namaBayar = findViewById(R.id.namaObatB);
        hargaBayar = findViewById(R.id.hargaSatuanB);
        hargaTotalBayar = findViewById(R.id.totalHargaB);
        jmlTotalBayar = findViewById(R.id.jmlObatB);
        imgBayar = findViewById(R.id.gambarB);


        Intent i = this.getIntent();
        kode = i.getExtras().getString("kode");
        nama = i.getExtras().getString("nama");
        harga = i.getExtras().getString("harga");
        hargaTotal = i.getExtras().getString("totalharga");
        jmlTotal = i.getExtras().getString("totaljual");
        gambar = i.getExtras().getString("gambar");
        noFaktur = String.valueOf(noFaktur(7));
        tglJual = getDateToday();

        noFak.setText(noFaktur);
        tglBayar.setText(tglJual);
        kodeBayar.setText(kode);
        namaBayar.setText(nama);
        hargaBayar.setText(harga);
        jmlTotalBayar.setText(jmlTotal);
        hargaTotalBayar.setText("Total Bayar : Rp "+hargaTotal);
        Picasso.get().load(gambar).placeholder(R.mipmap.ic_launcher).fit().centerCrop().into(imgBayar);

        // Bayar
        mDatabaseRef = FirebaseDatabase.getInstance().getReference("Jual");

        btnBayar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Jual jual = new Jual(noFaktur, tglJual, kode, jmlTotal, harga, hargaTotal);

                mDatabaseRef.push().setValue(jual).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(Bayar.this, "Pembayaran sukses", Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(Bayar.this, RiwayatJual.class);
                        startActivity(intent);
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(Bayar.this, e.getMessage(), Toast.LENGTH_LONG).show();
                    }
                });
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

    private int noFaktur(int digits){
        int max = (int) Math.pow(10,(digits)) - 1;
        int min = (int) Math.pow(10, digits-1);
        int range = max-min;
        Random r = new Random();
        int x = r.nextInt(range);
        int nDigitRandomNo = x+min;
        return nDigitRandomNo;
    }

    private String getDateToday(){
        DateFormat dateFormat=new SimpleDateFormat("yyyy/MM/dd");
        Date date=new Date();
        String today= dateFormat.format(date);
        return today;
    }
}
