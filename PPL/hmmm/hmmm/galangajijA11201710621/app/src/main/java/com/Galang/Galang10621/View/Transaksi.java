package com.Galang.Galang10621.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.Galang.Galang10621.Adapter.TransaksiAdapter;
import com.Galang.Galang10621.Auth.Login;
import com.Galang.Galang10621.Model.Obat;
import com.Galang.Galang10621.R;

import java.util.ArrayList;
import java.util.List;

public class Transaksi extends AppCompatActivity implements TransaksiAdapter.OnItemClickListener{

    private static final String TAG = "Transaksi";
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;

    private RecyclerView mRecyclerView;
    private TransaksiAdapter mAdapter;

    private ProgressBar mProgressCircle;

    private ValueEventListener mDBListener;
    private FirebaseStorage mStorage;
    private DatabaseReference mDatabaseRef;
    private List<Obat> mObats;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaksi);

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
                    Transaksi.this.startActivity(new Intent(Transaksi.this, Login.class));
                }
            }
        };

        mRecyclerView = findViewById(R.id.listTransaksi);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new GridLayoutManager(this,2));
        mProgressCircle = findViewById(R.id.progress_circle);

        mObats = new ArrayList<>();

        mStorage = FirebaseStorage.getInstance();
        mDatabaseRef = FirebaseDatabase.getInstance().getReference("Barang");

        mDBListener = mDatabaseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    Obat obat = postSnapshot.getValue(Obat.class);
                    obat.setKey(postSnapshot.getKey());
                    mObats.add(obat);
                }

                mAdapter = new TransaksiAdapter(Transaksi.this, mObats);

                mRecyclerView.setAdapter(mAdapter);
                mAdapter.setOnItemClickListener(Transaksi.this);

                mProgressCircle.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(Transaksi.this, databaseError.getMessage(), Toast.LENGTH_SHORT).show();
                mProgressCircle.setVisibility(View.INVISIBLE);
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

    private void openDetailActivity(String[] data){
        Intent intent = new Intent(this, DetailTransaksi.class);
        intent.putExtra("kode",data[0]);
        intent.putExtra("nama",data[1]);
        intent.putExtra("satuan",data[2]);
        intent.putExtra("jumlah",data[3]);
        intent.putExtra("expired",data[4]);
        intent.putExtra("harga",data[5]);
        intent.putExtra("gambar",data[6]);
        intent.putExtra("position",data[7]);
        startActivity(intent);
    }

    @Override
    public void onItemClick(int position) {
        Obat clickedObat = mObats.get(position);
        String[] obatData = {
                clickedObat.getKode(),
                clickedObat.getNama(),
                clickedObat.getSatuan(),
                clickedObat.getJumlah(),
                clickedObat.getExpired(),
                clickedObat.getHarga(),
                clickedObat.getGambar(),
                String.valueOf(position)
        };
        openDetailActivity(obatData);
    }
}
