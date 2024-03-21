package com.example.lab10_progresstest2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;


public class CreateNoteActivity extends AppCompatActivity {
    EditText edtTitle, edtContent;
    ImageView btnBack, btnSave;
    DBHandler dbHandler;
    String action;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_note);

        edtTitle = findViewById(R.id.txtTitle);
        edtContent = findViewById(R.id.txtContent);
        btnBack = findViewById(R.id.iBtnBack);
        btnSave = findViewById(R.id.iBtnSave);

        dbHandler = new DBHandler(CreateNoteActivity.this);
        Intent intent = getIntent();

        if (intent != null && intent.hasExtra("ACTION")) {
            action = intent.getStringExtra("ACTION");
        }

        if(action.equals("ADD")) {
            btnSave.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String title = edtTitle.getText().toString();
                    String content = edtContent.getText().toString();

                    if(title.isEmpty() || content.isEmpty()) {
                        Toast.makeText(CreateNoteActivity.this, "Please fulfill all data", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    dbHandler.addNote(title, content, getCurrentDate(), getCurrentTime());
                    Toast.makeText(CreateNoteActivity.this, "Note added successfully", Toast.LENGTH_SHORT).show();

                }
            });
        } else if (action.equals("EDIT")) {
            String title = intent.getStringExtra("title");
            String content = intent.getStringExtra("content");
            String id = intent.getStringExtra("id");

            edtTitle.setText(title);
            edtContent.setText(content);

            btnSave.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String title = edtTitle.getText().toString();
                    String description = edtContent.getText().toString();

                    if (title.isEmpty() || description.isEmpty()) {
                        Toast.makeText(CreateNoteActivity.this, "Please fulfill all data", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    dbHandler.updateNote(id, title, description, getCurrentDate(), getCurrentTime());
                    Toast.makeText(CreateNoteActivity.this, "Note update successfully", Toast.LENGTH_SHORT).show();

                }
            });
        }

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CreateNoteActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }

    private String getCurrentDate() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        return sdf.format(new Date());
    }

    private String getCurrentTime() {
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm", Locale.getDefault());
        return sdf.format(new Date());
    }

}
