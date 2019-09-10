package com.example.netologydiplom;

import java.util.List;

public class dbNotesRepository implements NotesRepository {
    private DatabaseHelper databaseHelper;

    public dbNotesRepository(App app) {
    }

    @Override
    public Note getNoteById(int id) {
        databaseHelper = App.getInstance().getDatabaseInstance();
        return databaseHelper.NotesDao().getById(id);
    }

    @Override
    public List<Note> getNotes() {
        databaseHelper = App.getInstance().getDatabaseInstance();
        return databaseHelper.NotesDao().getAllNotes();
    }

    @Override
    public void addNote(Note note) {
        databaseHelper = App.getInstance().getDatabaseInstance();
        databaseHelper.NotesDao().insert(note);
    }

    @Override
    public void deleteNote(Note note) {
        databaseHelper = App.getInstance().getDatabaseInstance();
        databaseHelper.NotesDao().delete(note);
    }

    @Override
    public void updateNote(Note note) {
        databaseHelper = App.getInstance().getDatabaseInstance();
        databaseHelper.NotesDao().update(note);
    }
}
