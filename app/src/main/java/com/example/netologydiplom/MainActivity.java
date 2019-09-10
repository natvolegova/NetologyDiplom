package com.example.netologydiplom;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity implements NotesRecyclerAdapter.OnNoteListener {
    private RecyclerView rvListNotes;
    private Toolbar toolbar;
    private NotesRepository noteRepository;
    private NotesRecyclerAdapter recyclerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        noteRepository = App.getInstance().getNotesRepository();
        initView();
    }

    //создание адаптера, вывод существующих данных
    @Override
    protected void onResume() {
        super.onResume();
        recyclerAdapter = new NotesRecyclerAdapter(this, noteRepository.getNotes(), this);
        rvListNotes.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
        rvListNotes.setAdapter(recyclerAdapter);
    }

    //открытие активити для просмотра и редактирования существующей записи
    @Override
    public void onNoteClick(int position) {
        Note note = recyclerAdapter.getById(position);
        Intent intent = new Intent(this, AddNoteActivity.class);
        intent.putExtra("id", note.getId());
        startActivity(intent);
    }

    //открытие dialog для подтверждения уделния записи
    @Override
    public void onNoteLongClick(int position) {
        Note note = recyclerAdapter.getById(position);
        noteRepository.deleteNote(note);
        recyclerAdapter.notifyDataSetChanged();
    }

    //открытие активити для добавления новой записи
    public void addNewNote(View view) {
        startActivity(new Intent(MainActivity.this, AddNoteActivity.class));
    }

    //определение всех начальных объектов
    private void initView() {
        rvListNotes = findViewById(R.id.rv_list_notes);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }
}
