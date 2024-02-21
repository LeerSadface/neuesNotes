package com.example.appprojekt.Activity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.appprojekt.Activity.Database.Note;
import com.example.appprojekt.Activity.Database.NoteDatabase;
import com.example.appprojekt.R;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class EditNoteActivity extends AppCompatActivity {

    private EditText titleEditText, noteContentEditText;
    private Spinner categorySpinner; // Spinner hinzugefügt
    private Button cancelButton, okButton;
    private int noteId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_note);

        titleEditText = findViewById(R.id.titleEditText);
        noteContentEditText = findViewById(R.id.noteContentEditText);
        categorySpinner = findViewById(R.id.categorySpinnerEdit); // Spinner initialisieren
        cancelButton = findViewById(R.id.cancelButton);
        okButton = findViewById(R.id.okButton);

        List<String> categories = new ArrayList<>();

        categories.add("Arbeit");
        categories.add("Persönlich");
        categories.add("Einkaufsliste");
        categories.add("Sport");
        categories.add("Reisen");

        // Setzen Sie den Spinner-Adapter mit den Kategorien
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, categories);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        categorySpinner.setAdapter(adapter);

        // Get the noteId from the intent
        noteId = getIntent().getIntExtra("noteId", -1);

        if (noteId != -1) {
            // Load the note content
            loadNoteContent();
        } else {
            // Handle the case where noteId is not passed correctly
            finish();
        }

        // Set click listeners
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveNoteAndFinish();
            }
        });
    }

    private void loadNoteContent() {
        // Load the note content asynchronously
        new LoadNoteContentTask().execute();
    }

    private int getIndexForCategory(String category) {
        // Method to get the index of the category in the spinner
        // Implement this according to your category list
        // Return the index corresponding to the category
        return 0;
    }

    private void saveNoteAndFinish() {
        String updatedTitle = titleEditText.getText().toString().trim();
        String updatedContent = noteContentEditText.getText().toString().trim();
        String selectedCategory = categorySpinner.getSelectedItem().toString();

        if (!updatedTitle.isEmpty() && !updatedContent.isEmpty()) {

            new UpdateNoteContentTask().execute(updatedTitle, updatedContent, selectedCategory);
        } else {

            Toast.makeText(this, "Titel und beschreibung koennen nicht leer sein", Toast.LENGTH_SHORT).show();
        }
    }

    private class LoadNoteContentTask extends AsyncTask<Void, Void, Note> {

        @Override
        protected Note doInBackground(Void... voids) {
            // Retrieve the note from the database using noteId
            return NoteDatabase.getInstance(EditNoteActivity.this).noteDao().getNoteById(noteId);
        }

        @Override
        protected void onPostExecute(Note note) {
            super.onPostExecute(note);
            // Populate the EditText with the note content
            if (note != null) {
                titleEditText.setText(note.getTitle());
                noteContentEditText.setText(note.getNoteText());
                // Set the selected category in the spinner
                categorySpinner.setSelection(getIndexForCategory(note.getCategory()));
            } else {
                // Handle the case where the note is not found
                finish();
            }
        }
    }

    private class UpdateNoteContentTask extends AsyncTask<String, Void, Void> {

        @Override
        protected Void doInBackground(String... strings) {

            String updatedTitle = strings[0];
            String updatedContent = strings[1];
            String updatedCategory = strings[2];

            long currentTime = System.currentTimeMillis();
            String lastUpdatedDateTime = DateFormat.getDateTimeInstance().format(new Date(currentTime));


            NoteDatabase.getInstance(EditNoteActivity.this).noteDao().updateNoteContentAndCategoryAndLastUpdated(noteId, updatedTitle, updatedContent, updatedCategory, lastUpdatedDateTime);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            Intent intent = new Intent();
            setResult(RESULT_OK, intent);
            finish();
        }
    }
}
