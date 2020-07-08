package com.Galang.Galang10621.Action;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;
import com.Galang.Galang10621.Model.Obat;
import com.Galang.Galang10621.R;
import com.Galang.Galang10621.View.Stok;

public class UpdateObat extends AppCompatActivity {

    private static final String TAG = "UpdateObat";
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;

    private EditText kodeUpdate, namaUpdate, satuanUpdate, jumlahUpdate, expiredUpdate, hargaUpdate;
    private ImageView imgUpdate;

    private static final int PICK_IMAGE_REQUEST = 1;

    private Button btnPilihImg, btnSubmit, btnKembali;
    private ProgressBar mProgressBar;

    private Uri mImageUri;

    private StorageReference mStorageRef;
    private DatabaseReference mDatabaseRef;

    private StorageTask mSendTask;
    private FirebaseStorage mStorage;

    private String id, kode, nama, satuan, jumlah, expired, harga, gambar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_obat);

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
                }
            }
        };

        kodeUpdate = findViewById(R.id.editKodeUp);
        namaUpdate = findViewById(R.id.editNamaUp);
        satuanUpdate = findViewById(R.id.editSatuanUp);
        jumlahUpdate = findViewById(R.id.editJumlahUp);
        expiredUpdate = findViewById(R.id.editExpiredUp);
        hargaUpdate = findViewById(R.id.editHargaUp);
        imgUpdate = findViewById(R.id.previewImgUp);



        Intent i = this.getIntent();
        id = i.getExtras().getString("id");
        kode = i.getExtras().getString("kode");
        nama = i.getExtras().getString("nama");
        satuan = i.getExtras().getString("satuan");
        jumlah = i.getExtras().getString("jumlah");
        expired = i.getExtras().getString("expired");
        harga = i.getExtras().getString("harga");
        gambar = i.getExtras().getString("gambar");


        kodeUpdate.setText(kode);
        namaUpdate.setText(nama);
        satuanUpdate.setText(satuan);
        jumlahUpdate.setText(jumlah);
        expiredUpdate.setText(expired);
        hargaUpdate.setText(harga);
        Picasso.get().load(gambar).placeholder(R.mipmap.ic_launcher).fit().centerCrop().into(imgUpdate);


        // update
        btnPilihImg = findViewById(R.id.btnPilihUp);
        btnSubmit = findViewById(R.id.btnAddUp);
        btnKembali = findViewById(R.id.btnBackUp);

        mProgressBar = findViewById(R.id.progress_barUp);

        mStorage = FirebaseStorage.getInstance();
        mStorageRef = FirebaseStorage.getInstance().getReference("Barang");
        mDatabaseRef = FirebaseDatabase.getInstance().getReference("Barang");

        btnPilihImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openFileChooser();
            }
        });

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mSendTask != null && mSendTask.isInProgress()) {
                    Toast.makeText(UpdateObat.this, "Sedang diproses", Toast.LENGTH_SHORT).show();
                } else {
                    deleteFile();
                }
            }
        });

        btnKembali.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UpdateObat.this.startActivity(new Intent(UpdateObat.this, Stok.class));
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

    private void deleteFile() {
        StorageReference imageRef = mStorage.getReferenceFromUrl(gambar);
        imageRef.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                uploadFile();
            }
        });
    }

    private void openFileChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK
                && data != null && data.getData() != null) {
            mImageUri = data.getData();

            Picasso.get().load(mImageUri).into(imgUpdate);
        }
    }

    private String getFileExtension(Uri uri) {
        ContentResolver cR = getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cR.getType(uri));
    }

    private void uploadFile() {
        if (mImageUri != null) {
            StorageReference fileReference = mStorageRef.child(System.currentTimeMillis()
                    + "." + getFileExtension(mImageUri));

            mSendTask = fileReference.putFile(mImageUri)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            Handler handler = new Handler();
                            handler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    mProgressBar.setProgress(0);
                                }
                            }, 500);

                            Task<Uri> urlTask = taskSnapshot.getStorage().getDownloadUrl();
                            while (!urlTask.isSuccessful());
                            Uri downloadUrl = urlTask.getResult();

                            Obat obat = new Obat(
                                    kodeUpdate.getText().toString().trim(),
                                    namaUpdate.getText().toString().trim(),
                                    satuanUpdate.getText().toString().trim(),
                                    jumlahUpdate.getText().toString().trim(),
                                    expiredUpdate.getText().toString().trim(),
                                    hargaUpdate.getText().toString().trim(),
                                    downloadUrl.toString()
                            );

//                            String uploadId = mDatabaseRef.push().getKey();
                            mDatabaseRef.child(id).setValue(obat);
                            Toast.makeText(UpdateObat.this, "Sukses update data", Toast.LENGTH_LONG).show();

                            // set kosong
                            kodeUpdate.setText(null);
                            namaUpdate.setText(null);
                            satuanUpdate.setText(null);
                            jumlahUpdate.setText(null);
                            expiredUpdate.setText(null);
                            hargaUpdate.setText(null);
                            imgUpdate.setImageResource(0);


                            UpdateObat.this.startActivity(new Intent(UpdateObat.this, Stok.class));

                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(UpdateObat.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                            double progress = (100.0 * taskSnapshot.getBytesTransferred() / taskSnapshot.getTotalByteCount());
                            mProgressBar.setProgress((int) progress);
                        }
                    });
        } else {
            Toast.makeText(this, "No file selected", Toast.LENGTH_SHORT).show();
        }
    }
}
