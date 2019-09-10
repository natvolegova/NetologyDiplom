package com.example.netologydiplom;

import android.app.Application;

import androidx.room.Room;

public class App extends Application {
    private static App instance;
    private static NotesRepository notesRepository;
    private DatabaseHelper db;

    public static App getInstance() {
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        db = Room.databaseBuilder(getApplicationContext(), DatabaseHelper.class, Constants.DB_NAME)
                .allowMainThreadQueries()
                .build();
        notesRepository = new dbNotesRepository(this);
    }

    public static NotesRepository getNotesRepository() {
        return notesRepository;
    }
    public DatabaseHelper getDatabaseInstance() {
        return db;
    }
}
