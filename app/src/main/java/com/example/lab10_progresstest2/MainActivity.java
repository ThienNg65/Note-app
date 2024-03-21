package com.example.lab10_progresstest2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private ArrayList<NoteModal> noteModalArrayList;
    private Button btnAdd;
    private NoteRVAdapter noteRVAdapter;
    private DBHandler dbHandler;
    private RecyclerView noteRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        noteModalArrayList = new ArrayList<>();
        dbHandler = new DBHandler(MainActivity.this);

        noteModalArrayList = dbHandler.readNotes();
        noteRVAdapter = new NoteRVAdapter(noteModalArrayList, MainActivity.this);
        noteRecyclerView = findViewById(R.id.notesRecyclerView);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(MainActivity.this, RecyclerView.VERTICAL, false);
        noteRecyclerView.setLayoutManager(linearLayoutManager);

        noteRecyclerView.setAdapter(noteRVAdapter);

        btnAdd = findViewById(R.id.btnAdd);

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, CreateNoteActivity.class);
                intent.putExtra("ACTION", "ADD");
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        noteModalArrayList.clear();
        noteModalArrayList.addAll(dbHandler.readNotes());
        noteRVAdapter.notifyDataSetChanged();
    }

}