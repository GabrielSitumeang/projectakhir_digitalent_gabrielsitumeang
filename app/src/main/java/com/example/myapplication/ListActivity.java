package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.myapplication.adapter.Adapter;
import com.example.myapplication.helper.DatabaseHelper;
import com.example.myapplication.model.Tempat;

import java.util.ArrayList;
import java.util.HashMap;

public class ListActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private Adapter adapter;
    private ArrayList<Tempat> listTempat;
    private DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        recyclerView = findViewById(R.id.rview);
        adapter = new Adapter(this);

        databaseHelper = new DatabaseHelper(this);
        ArrayList<HashMap<String, String>> tempatData = databaseHelper.getAllData();
        listTempat = new ArrayList<>();

        for (HashMap<String, String> data : tempatData) {
            Tempat tempat = new Tempat();
            tempat.setId(Integer.parseInt(data.get("id")));
            tempat.setName(data.get("name"));
            tempat.setLokasi(data.get("lokasi"));
            tempat.setHargaTiket(data.get("hargaTiket"));
            tempat.setDeskripsi(data.get("deskripsi"));
            // Tambahan setiap atribut Tempat sesuai kebutuhan

            listTempat.add(tempat);
        }

        adapter.setListTempat(listTempat);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(ListActivity.this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
    }
}