package com.Galang.Galang10621.View;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.Galang.Galang10621.Action.AddObat;
import com.Galang.Galang10621.Action.UpdateObat;
import com.Galang.Galang10621.Adapter.StokAdapter;
import com.Galang.Galang10621.Auth.Login;
import com.Galang.Galang10621.Model.Obat;
import com.Galang.Galang10621.R;

import java.util.ArrayList;
import java.util.List;

public class Stok extends AppCompatActivity implements StokAdapter.OnItemClickListener{

    private static final String TAG = "Stok";
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;

    private RecyclerView mRecyclerView;
    private StokAdapter mAdapter;

    private ProgressBar mProgressCircle;

    private ValueEventListener mDBListener;
    private FirebaseStorage mStorage;
    private DatabaseReference mDatabaseRef;
    private List<Obat> mObats;

    private FloatingActionButton fabAdd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stok);

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
                    Stok.this.startActivity(new Intent(Stok.this, Login.class));
                }
            }
        };


        mRecyclerView = findViewById(R.id.listStok);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mProgressCircle = findViewById(R.id.progress_circle);
        fabAdd = (FloatingActionButton) findViewById(R.id.fabAdd);

        fabAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Stok.this, AddObat.class);
                startActivity(intent);
            }
        });


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

                mAdapter = new StokAdapter(Stok.this, mObats);

                mRecyclerView.setAdapter(mAdapter);
                mAdapter.setOnItemClickListener(Stok.this);

                mProgressCircle.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(Stok.this, databaseError.getMessage(), Toast.LENGTH_SHORT).show();
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
        Intent intent = new Intent(this, DetailObat.class);
        intent.putExtra("kode",data[0]);
        intent.putExtra("nama",data[1]);
        intent.putExtra("satuan",data[2]);
        intent.putExtra("jumlah",data[3]);
        intent.putExtra("expired",data[4]);
        intent.putExtra("harga",data[5]);
        intent.putExtra("gambar",data[6]);

        startActivity(intent);
    }

    private void openUpdateActivity(String[] data){
        Intent intent = new Intent(this, UpdateObat.class);
        intent.putExtra("kode",data[0]);
        intent.putExtra("nama",data[1]);
        intent.putExtra("satuan",data[2]);
        intent.putExtra("jumlah",data[3]);
        intent.putExtra("expired",data[4]);
        intent.putExtra("harga",data[5]);
        intent.putExtra("gambar",data[6]);
        intent.putExtra("id",data[7]);

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
                clickedObat.getGambar()

        };
        openDetailActivity(obatData);
    }

    @Override
    public void onUpdateItemClick(int position) {
        Obat clickedObat = mObats.get(position);
        String[] obatData = {
                clickedObat.getKode(),
                clickedObat.getNama(),
                clickedObat.getSatuan(),
                clickedObat.getJumlah(),
                clickedObat.getExpired(),
                clickedObat.getHarga(),
                clickedObat.getGambar(),
                clickedObat.getKey()
        };
        openUpdateActivity(obatData);
    }

    @Override
    public void onDeleteItemClick(int position) {
        Obat selectedItem = mObats.get(position);
        final String selectedKey = selectedItem.getKey();

        StorageReference imageRef = mStorage.getReferenceFromUrl(selectedItem.getGambar());
        imageRef.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                mDatabaseRef.child(selectedKey).removeValue();
                Toast.makeText(Stok.this, "Data berhasil dihapus", Toast.LENGTH_SHORT).show();
                finish();
                startActivity(getIntent());
            }
        });
    }

    protected void onDestroy() {
        super.onDestroy();
        mDatabaseRef.removeEventListener(mDBListener);
    }
}
