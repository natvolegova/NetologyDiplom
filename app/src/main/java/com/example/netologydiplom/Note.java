package com.example.netologydiplom;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import java.util.Date;

@Entity
public class Note {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private String noteTitle;
    @TypeConverters({DateConverter.class})
    private Date datePub;
    @TypeConverters({DateConverter.class})
    private Date dateDeadline;
    private int isDeadLine;
    private String noteDescription;

    public Note(String noteTitle, Date datePub, Date dateDeadline, int isDeadLine, String noteDescription) {
        this.noteTitle = noteTitle;
        this.datePub = datePub;
        this.dateDeadline = dateDeadline;
        this.isDeadLine = isDeadLine;
        this.noteDescription = noteDescription;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNoteTitle() {
        return noteTitle;
    }

    public void setNoteTitle(String noteTitle) {
        this.noteTitle = noteTitle;
    }

    public Date getDatePub() {
        return datePub;
    }

    public void setDatePub(Date datePub) {
        this.datePub = datePub;
    }

    public Date getDateDeadline() {
        return dateDeadline;
    }

    public void setDateDeadline(Date dateDeadline) {
        this.dateDeadline = dateDeadline;
    }

    public int getIsDeadLine() {
        return isDeadLine;
    }

    public void setIsDeadLine(int isDeadLine) {
        this.isDeadLine = isDeadLine;
    }

    public String getNoteDescription() {
        return noteDescription;
    }

    public void setNoteDescription(String noteDescription) {
        this.noteDescription = noteDescription;
    }
}
