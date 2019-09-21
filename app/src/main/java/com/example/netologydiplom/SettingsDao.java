package com.example.netologydiplom;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

@Dao
public interface SettingsDao {
    @Insert
    void addSettings(Settings settings);

    @Update
    void updateSettings(Settings settings);

    @Delete
    void deleteSettings(Settings settings);

    @Query("SELECT * FROM Settings")
    Settings getSettings();
}
