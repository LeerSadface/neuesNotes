package com.example.appprojekt.Activity.Database;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Calendar;


@Entity(tableName = "notes")

public class Note {
    @PrimaryKey(autoGenerate = true)
    public int id;
    @ColumnInfo(name = "category")
    public String category;
    @ColumnInfo(name = "title")
    public String title;
    @ColumnInfo(name = "note_text")
    public String noteText;
    @ColumnInfo(name = "date_created")
    public Calendar dateCreated;
    @ColumnInfo(name = "date_last_updated")
    public Calendar dateLastUpdated;

    public Note(String category, String title, String noteText, Calendar dateCreated, Calendar dateLastUpdated) {
        this.category = category;
        this.title = title;
        this.noteText = noteText;
        this.dateCreated = dateCreated;
        this.dateLastUpdated = dateLastUpdated;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getNoteText() {
        return noteText;
    }

    public void setNoteText(String noteText) {
        this.noteText = noteText;
    }

    public Calendar getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Calendar dateCreated) {
        this.dateCreated = dateCreated;
    }

    public int getId() {
        return id;
    }

    public Calendar getDateLastUpdated() {
        return dateLastUpdated;
    }

    public void setDateLastUpdated(Calendar dateLastUpdated) {
        this.dateLastUpdated = dateLastUpdated;
    }
}