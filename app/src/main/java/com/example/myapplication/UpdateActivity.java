package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.myapplication.helper.DatabaseHelper;
import com.example.myapplication.model.Tempat;

public class UpdateActivity extends AppCompatActivity {

    private DatabaseHelper databaseHelper;
    private EditText etName, etLokasi, etHargaTiket, etDeskripsi;
    private Button btnSave;
    private Tempat tempat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);

        databaseHelper = new DatabaseHelper(this);

        etName = findViewById(R.id.edt_name);
        etLokasi = findViewById(R.id.edt_lokasi);
        etHargaTiket = findViewById(R.id.edt_hargaTiket);
        etDeskripsi = findViewById(R.id.edt_deskripsi);
        btnSave = findViewById(R.id.btn_submit);

        Intent intent = getIntent();
        tempat = (Tempat) intent.getSerializableExtra("tempat");

        etName.setText(tempat.getName());
        etLokasi.setText(tempat.getLokasi());
        etHargaTiket.setText(tempat.getHargaTiket());
        etDeskripsi.setText(tempat.getDeskripsi());

        btnSave.setOnClickListener((View v) -> {
            databaseHelper.updateData(tempat.getId(), etName.getText().toString(), etLokasi.getText().toString(), etHargaTiket.getText().toString(), etDeskripsi.getText().toString());
            Toast.makeText(UpdateActivity.this, "Updated berhasil!", Toast.LENGTH_SHORT).show();
            finish();
        });

    }
}
