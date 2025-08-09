package com.example.tarea_03.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.tarea_03.DB.LawyerDbHelper;
import com.example.tarea_03.R;
import com.example.tarea_03.model.Lawyer;

public class LawyerDetailActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lawyer_detail);

        String lawyerId = getIntent().getStringExtra("lawyerId");

        LawyerDbHelper dbHelper = new LawyerDbHelper(this);
        Lawyer lawyer = dbHelper.getLawyerById(lawyerId);

        if (lawyer != null) {
            ((TextView) findViewById(R.id.detail_name)).setText(lawyer.getName());
            ((TextView) findViewById(R.id.detail_specialty)).setText(lawyer.getSpecialty());
            ((TextView) findViewById(R.id.detail_phone)).setText(lawyer.getPhone());
            ((TextView) findViewById(R.id.detail_bio)).setText(lawyer.getBio());
        }

        ImageButton btnBack = findViewById(R.id.btnBack);
        btnBack.setOnClickListener(v -> {
            Intent intent = new Intent(LawyerDetailActivity.this, LawyersActivity.class);
            startActivity(intent);
        });

        ImageButton btnEdit = findViewById(R.id.btnEdit);
        btnEdit.setOnClickListener(v -> {
            Intent intent = new Intent(LawyerDetailActivity.this, AddEditLawyerActivity.class);
            intent.putExtra(AddEditLawyerActivity.EXTRA_LAWYER_ID, lawyerId);
            startActivity(intent);
        });

        // Eliminar abogado en contexto
        ImageButton btnDelete = findViewById(R.id.btnDelete);
        btnDelete.setOnClickListener(v -> {
            if (lawyerId != null) {
                new androidx.appcompat.app.AlertDialog.Builder(LawyerDetailActivity.this)
                        .setTitle("Confirmar eliminación")
                        .setMessage("¿Estás seguro de que deseas eliminar este abogado?")
                        .setPositiveButton("Eliminar", (dialog, which) -> {
                            int rowsDeleted = dbHelper.deleteLawyer(lawyerId);

                            if (rowsDeleted > 0) {
                                Toast.makeText(LawyerDetailActivity.this, "Abogado eliminado", Toast.LENGTH_SHORT).show();

                                // Redirigir a la lista de abogados
                                Intent intent = new Intent(LawyerDetailActivity.this, LawyersActivity.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(intent);
                                finish();
                            } else {
                                Toast.makeText(LawyerDetailActivity.this, "Error al eliminar", Toast.LENGTH_SHORT).show();
                            }

                            dbHelper.close();
                        })
                        .setNegativeButton("Cancelar", (dialog, which) -> dialog.dismiss())
                        .show();
            } else {
                Toast.makeText(LawyerDetailActivity.this, "No se encontró el abogado", Toast.LENGTH_SHORT).show();
            }
        });


    }
}


