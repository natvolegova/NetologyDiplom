package com.example.netologydiplom;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private RecyclerView rvListNotes;
    private Toolbar toolbar;
    private FloatingActionButton fab;
    private NotesRepository noteRepository;
    private NotesRecyclerAdapter recyclerAdapter;
    private List<Note> noteList = new ArrayList<>();
    private long backPressedTime;
    private Toast toastBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    //создание адаптера, вывод существующих данных
    @Override
    protected void onResume() {
        super.onResume();
        noteRepository = App.getInstance().getNotesRepository();
        noteList = noteRepository.getNotes();
        recyclerAdapter = new NotesRecyclerAdapter(this, noteRepository.getNotes());
        rvListNotes.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
        rvListNotes.setAdapter(recyclerAdapter);
    }

    //определение всех начальных объектов
    private void initView() {
        rvListNotes = findViewById(R.id.rv_list_notes);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addNewNote(view);
            }
        });
    }

    //открытие активити для добавления новой записи
    public void addNewNote(View view) {
        startActivity(new Intent(MainActivity.this, AddNoteActivity.class));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        MenuItem saveMenuItem = menu.findItem(R.id.save);
        saveMenuItem.setVisible(false);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        Intent intent = null;
        switch (item.getItemId()) {
            case R.id.settings:
                intent = new Intent(MainActivity.this, SettingsActivity.class);
                break;
        }
        startActivity(intent);
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        if (backPressedTime + 2000 > System.currentTimeMillis()) {
            toastBack.cancel();
            finishAffinity();
        } else {
            toastBack = Toast.makeText(this, getResources().getText(R.string.msg_back_pressed), Toast.LENGTH_SHORT);
            toastBack.show();
        }
        backPressedTime = System.currentTimeMillis();
    }
}
