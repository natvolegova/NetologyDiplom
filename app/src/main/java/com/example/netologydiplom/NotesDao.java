package com.example.netologydiplom;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;
@Dao
public interface NotesDao {
    @Insert
    void insert(Note note);

    @Update
    void update(Note note);

    @Delete
    void delete(Note note);

    @Query("SELECT * FROM Note order by isDeadLine desc, dateDeadline asc, datePub desc")
    List<Note> getAllNotes();

    @Query("SELECT * FROM Note WHERE id = :id")
    Note getById(int id);
}
