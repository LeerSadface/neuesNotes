package com.example.appprojekt.Activity.Database;


import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Transaction;
import androidx.room.Update;

import java.util.List;

@Dao
public interface NoteDao {
    @Insert
    void insert(Note note);

    @Update
    void update(Note note);

    @Delete
    void delete(Note note);

    @Query("SELECT * FROM notes")
    List<Note> getAllNotes();

    @Query("DELETE FROM notes")
    void deleteAll();

    @Query("SELECT * FROM notes WHERE title LIKE :searchText")
    List<Note> searchNotes(String searchText);


    @Query("UPDATE notes SET title = :title, note_text = :text, category = :category, date_last_updated = :lastUpdatedDateTime WHERE id = :noteId")
    void updateNoteContentAndCategoryAndLastUpdated(int noteId, String title, String text, String category, String lastUpdatedDateTime);

    @Transaction
    @Query("SELECT * FROM notes")
    List<NoteandCategory> getNoteAndCategory();

    @Query("SELECT * FROM notes WHERE category = :category")
    List<Note> getNotesByCategory(String category);

    @Query("SELECT * FROM notes WHERE id = :noteId")
    Note getNoteById(int noteId);

    @Query("DELETE FROM notes WHERE id = :noteId")
    void deleteNoteById(int noteId);
}