package com.example.appprojekt.Activity.Database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

@Database(entities = {Category.class, Note.class}, version = 1)
@TypeConverters({Converters.class})
public abstract class NoteDatabase extends RoomDatabase {

    private static NoteDatabase instance;

    public static synchronized NoteDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(),
                            NoteDatabase.class, "notes_database")
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return instance;
    }

    public abstract CategoryDao categoryDao();

    public abstract NoteDao noteDao();


}
