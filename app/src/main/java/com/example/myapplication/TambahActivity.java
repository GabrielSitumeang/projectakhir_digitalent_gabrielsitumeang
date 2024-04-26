package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.myapplication.helper.DatabaseHelper;

public class TambahActivity extends AppCompatActivity {

    DatabaseHelper databaseHelper;
    private EditText etName, etLokasi, etHargaTiket, etDeskripsi;
    Button btnSave, btnBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tambah);

        databaseHelper = new DatabaseHelper(this);

        etName = findViewById(R.id.edt_name);
        etLokasi = findViewById(R.id.edt_lokasi);
        etHargaTiket = findViewById(R.id.edt_hargaTiket);
        etDeskripsi = findViewById(R.id.edt_deskripsi);
        btnSave = findViewById(R.id.btn_submit);
        btnBack = findViewById(R.id.btn_back);

        btnSave.setOnClickListener(v -> {
            if (etName.getText().toString().isEmpty()) {
                Toast.makeText(TambahActivity.this, "Error: Nama harus diisi!", Toast.LENGTH_SHORT).show();
            } else if (etLokasi.getText().toString().isEmpty()) {
                Toast.makeText(TambahActivity.this, "Error: Lokasi harus diisi!", Toast.LENGTH_SHORT).show();
            } else if (etHargaTiket.getText().toString().isEmpty()) {
                Toast.makeText(TambahActivity.this, "Error: Harga Tiket harus diisi!", Toast.LENGTH_SHORT).show();
            }else if (etDeskripsi.getText().toString().isEmpty()) {
                Toast.makeText(TambahActivity.this, "Error: Deskripsi harus diisi!", Toast.LENGTH_SHORT).show();
            }else {
                databaseHelper.insertData(etName.getText().toString(), etLokasi.getText().toString(), etHargaTiket.getText().toString(), etDeskripsi.getText().toString());
                etName.setText("");
                etLokasi.setText("");
                etHargaTiket.setText("");
                etDeskripsi.setText("");
                Toast.makeText(TambahActivity.this, "Simpan berhasil!", Toast.LENGTH_SHORT).show();
            }
        });

        btnBack.setOnClickListener(v -> {
            Intent intent = new Intent(TambahActivity.this, ListActivity.class);
            startActivity(intent);
        });

    }
}
