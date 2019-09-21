package com.example.netologydiplom;

import android.app.Application;

import androidx.room.Room;


public class App extends Application {
    private static App instance;
    private static NotesRepository notesRepository;
    private static Keystore keyStore;
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
        keyStore = new SimpleKeystore(this);
    }

    public static NotesRepository getNotesRepository() {
        return notesRepository;
    }

    public static Keystore getKeystore() {
        return keyStore;
    }

    public DatabaseHelper getDatabaseInstance() {
        return db;
    }
}
