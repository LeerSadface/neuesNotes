package com.example.appprojekt.Activity.Database;

import androidx.room.Embedded;
import androidx.room.Relation;

import java.util.List;

public class NoteandCategory {
    @Embedded
    public Note note;
    @Relation(
            parentColumn = "id",
            entityColumn = "category"
    )
    public List<Note> notes;
}
