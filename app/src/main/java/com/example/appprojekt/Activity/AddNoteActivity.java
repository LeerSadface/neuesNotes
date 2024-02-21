package com.example.appprojekt.Activity;

import android.annotation.SuppressLint;
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

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class AddNoteActivity extends AppCompatActivity {

    private Spinner categorySpinner;
    private EditText titleEditText, noteTextEditText;
    private Button cancelButton, addButton;
    private NoteDatabase noteDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_note);

        categorySpinner = findViewById(R.id.categorySpinner);
        titleEditText = findViewById(R.id.titleEditText);
        noteTextEditText = findViewById(R.id.noteTextEditText);
        cancelButton = findViewById(R.id.cancelButton);
        addButton = findViewById(R.id.addNote);

        noteDatabase = NoteDatabase.getInstance(this);

        List<String> categories = new ArrayList<>();
        categories.add("Arbeit");
        categories.add("Persönlich");
        categories.add("Einkaufsliste");
        categories.add("Sport");
        categories.add("Reisen");

        // Adapter für den Spinner erstellen
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, categories);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // Adapter an den Spinner anhängen
        categorySpinner.setAdapter(adapter);

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveNoteToDatabase();
            }
        });
    }

    private void saveNoteToDatabase() {
        String category = categorySpinner.getSelectedItem().toString();
        String title = titleEditText.getText().toString().trim();
        String noteText = noteTextEditText.getText().toString().trim();
        Calendar currentTime = Calendar.getInstance();

        if (title.isEmpty() || noteText.isEmpty()) {
            Toast.makeText(this, "Bitte füllen Sie alle Felder aus", Toast.LENGTH_SHORT).show();
            return;
        }

        final Note note = new Note(category, title, noteText, currentTime, currentTime);


        @SuppressLint("StaticFieldLeak")
        class SaveNoteTask extends AsyncTask<Void, Void, Void> {

            @Override
            protected Void doInBackground(Void... voids) {
                NoteDatabase.getInstance(getApplicationContext()).noteDao().insert(note);

                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                Intent intent = new Intent();
                Toast.makeText(getApplicationContext(), "Notiz hinzugefügt", Toast.LENGTH_SHORT).show();
                setResult(RESULT_OK, intent);
                finish();
            }
        }
        new SaveNoteTask().execute();
    }

}

