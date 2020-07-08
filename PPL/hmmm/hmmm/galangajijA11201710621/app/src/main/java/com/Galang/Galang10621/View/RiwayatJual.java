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

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.Galang.Galang10621.Adapter.RiwayatAdapter;
import com.Galang.Galang10621.Auth.Login;
import com.Galang.Galang10621.Model.Jual;
import com.Galang.Galang10621.R;

import java.util.ArrayList;
import java.util.List;

public class RiwayatJual extends AppCompatActivity implements RiwayatAdapter.OnItemClickListener{

    private static final String TAG = "RiwayatJual";
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;

    private RecyclerView mRecyclerView;
    private RiwayatAdapter mAdapter;

    private ProgressBar mProgressCircle;

    private ValueEventListener mDBListener;
    private FirebaseStorage mStorage;
    private DatabaseReference mDatabaseRef;
    private List<Jual> mJuals;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_riwayat_jual);

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
                    RiwayatJual.this.startActivity(new Intent(RiwayatJual.this, Login.class));
                }
            }
        };

        mRecyclerView = findViewById(R.id.listRiwayat);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mProgressCircle = findViewById(R.id.progress_circle);

        mJuals = new ArrayList<>();

        mStorage = FirebaseStorage.getInstance();
        mDatabaseRef = FirebaseDatabase.getInstance().getReference("Jual");

        mDBListener = mDatabaseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    Jual jual = postSnapshot.getValue(Jual.class);
                    jual.setKey(postSnapshot.getKey());
                    mJuals.add(jual);
                }

                mAdapter = new RiwayatAdapter(RiwayatJual.this, mJuals);

                mRecyclerView.setAdapter(mAdapter);
                mAdapter.setOnItemClickListener(RiwayatJual.this);

                mProgressCircle.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(RiwayatJual.this, databaseError.getMessage(), Toast.LENGTH_SHORT).show();
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
        Intent intent = new Intent(this, DetailRiwayat.class);
        intent.putExtra("nofak",data[0]);
        intent.putExtra("tgl",data[1]);
        intent.putExtra("kode",data[2]);
        intent.putExtra("jml",data[3]);
        intent.putExtra("harga",data[4]);
        intent.putExtra("total",data[5]);
        startActivity(intent);
    }

    @Override
    public void onItemClick(int position) {
        Jual clickedJual = mJuals.get(position);
        String[] riwayatData = {
                clickedJual.getNoFak(),
                clickedJual.getTglJual(),
                clickedJual.getKode(),
                clickedJual.getJmlJual(),
                clickedJual.getHargaJual(),
                clickedJual.getTotalJual(),
        };
        openDetailActivity(riwayatData);
    }
}
