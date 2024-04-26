package com.example.myapplication.adapter;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.ListActivity;
import com.example.myapplication.R;
import com.example.myapplication.UpdateActivity;
import com.example.myapplication.helper.DatabaseHelper;
import com.example.myapplication.model.Tempat;

import java.io.Serializable;
import java.util.ArrayList;

public class Adapter extends RecyclerView.Adapter<Adapter.TempatViewHolder> {

    private ArrayList<Tempat> listTempat = new ArrayList<>();
    private Activity activity;
    private DatabaseHelper databaseHelper;

    public Adapter(Activity activity) {
        this.activity = activity;
        databaseHelper = new DatabaseHelper(activity);
    }

    public ArrayList<Tempat> getListTempat() {
        return listTempat;
    }

    public void setListTempat(ArrayList<Tempat> listNotes) {
        if (listNotes.size() > 0) {
            this.listTempat.clear();
        }
        this.listTempat.addAll(listNotes);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public TempatViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list, parent, false);
        return new TempatViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TempatViewHolder holder, int position) {
        holder.tvName.setText(listTempat.get(position).getName());
        holder.tvLokasi.setText(listTempat.get(position).getLokasi());
        holder.tvHargaTiket.setText(listTempat.get(position).getHargaTiket());
        holder.tvDeskripsi.setText(listTempat.get(position).getDeskripsi());
        holder.btnEdit.setOnClickListener((View v) -> {
            Intent intent = new Intent(activity, UpdateActivity.class);
            intent.putExtra("tempat", (Serializable) listTempat.get(position));
            activity.startActivity(intent);
        });

        holder.btnDelete.setOnClickListener((View v) -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(activity);

            builder.setTitle("Konfirmasi hapus");
            builder.setMessage("Apakah yakin akan dihapus?");

            builder.setPositiveButton("YA", (dialog, which) -> {
                databaseHelper.deleteData(listTempat.get(position).getId());
                Toast.makeText(activity, "Hapus berhasil!", Toast.LENGTH_SHORT).show();
                Intent myIntent = new Intent(activity, ListActivity.class);
                activity.startActivity(myIntent);
                activity.finish();
            });

            builder.setNegativeButton("TIDAK", (dialog, which) -> dialog.dismiss());

            AlertDialog alert = builder.create();
            alert.show();

        });
    }

    @Override
    public int getItemCount() {
        return listTempat.size();
    }

    public class TempatViewHolder extends RecyclerView.ViewHolder {

        final TextView tvName, tvLokasi, tvHargaTiket, tvDeskripsi;
        final Button btnEdit, btnDelete;

        public TempatViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tv_item_name);
            tvLokasi = itemView.findViewById(R.id.tv_item_lokasi);
            tvHargaTiket = itemView.findViewById(R.id.tv_item_hargaTiket);
            tvDeskripsi = itemView.findViewById(R.id.tv_item_deskripsi);
            btnEdit = itemView.findViewById(R.id.btn_edit);
            btnDelete = itemView.findViewById(R.id.btn_delete);
        }
    }
}