package com.example.appprojekt.Activity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.appprojekt.Activity.Database.NoteDatabase;
import com.example.appprojekt.R;

public class DeleteNoteActivity extends AppCompatActivity {

    private boolean confirmDelete = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_note);

        TextView textViewDelete = findViewById(R.id.textViewDelete);
        textViewDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (confirmDelete) {
                    deleteNote();
                } else {
                    Toast.makeText(DeleteNoteActivity.this, "Bitte erneut klicken, um zu bestätigen", Toast.LENGTH_SHORT).show();
                    confirmDelete = true;
                }
            }
        });

        TextView textViewCancel = findViewById(R.id.textViewCancel);
        textViewCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void deleteNote() {
        int noteId = getIntent().getIntExtra("noteId", -1);

        if (noteId != -1) {
            new DeleteNoteTask().execute(noteId);
        } else {
            finish();
        }
    }

    private class DeleteNoteTask extends AsyncTask<Integer, Void, Void> {

        @Override
        protected Void doInBackground(Integer... noteIds) {
            int noteId = noteIds[0];
            NoteDatabase.getInstance(DeleteNoteActivity.this).noteDao().deleteNoteById(noteId);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            Toast.makeText(getApplicationContext(), "Notiz gelöscht", Toast.LENGTH_SHORT).show();
            Log.d("DeleteNoteActivity", "Note deleted successfully");
            Intent intent = new Intent();
            setResult(RESULT_OK, intent);
            finish();
        }
    }
}
