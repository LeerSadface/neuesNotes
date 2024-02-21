package com.example.appprojekt.Activity.Database;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "categories")
public class Category {
    @PrimaryKey
    @NonNull
    public String category;

    public Category(String category) {
        this.category = category;
    }
}
