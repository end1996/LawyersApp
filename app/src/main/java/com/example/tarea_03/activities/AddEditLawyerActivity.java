package com.example.tarea_03.activities;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.tarea_03.DB.LawyerDbHelper;
import com.example.tarea_03.R;
import com.example.tarea_03.model.Lawyer;

import java.util.UUID;

public class AddEditLawyerActivity extends AppCompatActivity {

    public static final String EXTRA_LAWYER_ID = "lawyerId";

    private EditText etName, etSpecialty, etPhone, etBio;
    private Button btnSave;
    private LawyerDbHelper dbHelper;
    private String currentLawyerId = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit_lawyer);

        etName = findViewById(R.id.etName);
        etSpecialty = findViewById(R.id.etSpecialty);
        etPhone = findViewById(R.id.etPhone);
        etBio = findViewById(R.id.etBio);
        btnSave = findViewById(R.id.btnSave);

        dbHelper = new LawyerDbHelper(this);

        Intent intent = getIntent();
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        TextView tvTitle = findViewById(R.id.tvToolbarTitle);
        if (intent != null && intent.hasExtra(EXTRA_LAWYER_ID)) {
            currentLawyerId = intent.getStringExtra(EXTRA_LAWYER_ID);
            loadLawyerData(currentLawyerId);
            tvTitle.setText("Editar abogado");
        } else {
            tvTitle.setText("Agregar abogado");
        }


        btnSave.setOnClickListener(view -> saveLawyer());

        // Volver a vista anterior (botón en layout)
        ImageButton btnBack = findViewById(R.id.btnBack);
        if (btnBack != null) {
            btnBack.setOnClickListener(v -> finish());
        }
    }

    private void loadLawyerData(String lawyerId) {
        if (lawyerId == null) return;

        Lawyer lawyer = dbHelper.getLawyerById(lawyerId);
        if (lawyer != null) {
            etName.setText(lawyer.getName());
            etSpecialty.setText(lawyer.getSpecialty());
            etPhone.setText(lawyer.getPhone());
            etBio.setText(lawyer.getBio());
        }
    }

    private void saveLawyer() {
        String name = etName.getText().toString().trim();
        String specialty = etSpecialty.getText().toString().trim();
        String phone = etPhone.getText().toString().trim();
        String bio = etBio.getText().toString().trim();

        if (TextUtils.isEmpty(name) || TextUtils.isEmpty(specialty) || TextUtils.isEmpty(phone)) {
            Toast.makeText(this, "Por favor completa los campos obligatorios", Toast.LENGTH_SHORT).show();
            return;
        }

        if (currentLawyerId == null) {
            // Nuevo abogado
            String newId = UUID.randomUUID().toString();
            long rowId = dbHelper.addLawyer(newId, name, specialty, phone, bio, null);
            if (rowId != -1) {
                Toast.makeText(this, "Abogado registrado", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Error al registrar abogado", Toast.LENGTH_SHORT).show();
            }
        } else {
            // Actualizar abogado existente
            int rows = dbHelper.updateLawyer(currentLawyerId, name, specialty, phone, bio, null);
            if (rows > 0) {
                Toast.makeText(this, "Abogado actualizado", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "No se actualizó (error)", Toast.LENGTH_SHORT).show();
            }
        }

        // Volver a la lista (mejor usar finish para mantener pila)
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (dbHelper != null) dbHelper.close();
    }
}
