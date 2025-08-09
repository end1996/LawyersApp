package com.example.tarea_03.activities;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.tarea_03.DB.LawyerDbHelper;
import com.example.tarea_03.utils.LawyerAdapter;
import com.example.tarea_03.R;
import com.example.tarea_03.model.Lawyer;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class LawyersActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lawyers);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitleTextColor(getResources().getColor(R.color.white, getTheme()));
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(view -> {
            Intent intent = new Intent(this, AddEditLawyerActivity.class);
            startActivity(intent);
        });
        loadLawyersList();
    }
    private void loadLawyersList() {
        ListView listView = findViewById(R.id.lawyers_list);

        LawyerDbHelper dbHelper = new LawyerDbHelper(this);
        Cursor cursor = dbHelper.getAllLawyers();

        List<Lawyer> lawyerList = new ArrayList<>();

        if (cursor != null && cursor.moveToFirst()) {
            do {
                String id = cursor.getString(cursor.getColumnIndexOrThrow("id"));
                String name = cursor.getString(cursor.getColumnIndexOrThrow("name"));
                String specialty = cursor.getString(cursor.getColumnIndexOrThrow("specialty"));
                String phone = cursor.getString(cursor.getColumnIndexOrThrow("phone"));
                String bio = cursor.getString(cursor.getColumnIndexOrThrow("bio"));
                String avatarUri = cursor.getString(cursor.getColumnIndexOrThrow("avatarUri"));

                lawyerList.add(new Lawyer(id, name, specialty, phone, bio, avatarUri));
            } while (cursor.moveToNext());
            cursor.close();
        }

        LawyerAdapter adapter = new LawyerAdapter(this, lawyerList);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener((parent, view, position, id) -> {
            Lawyer selected = lawyerList.get(position);
            Intent intent = new Intent(this, LawyerDetailActivity.class);
            intent.putExtra("lawyerId", selected.getId());
            startActivity(intent);
        });
    }

}