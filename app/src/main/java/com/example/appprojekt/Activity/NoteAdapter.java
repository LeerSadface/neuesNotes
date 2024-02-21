package com.example.appprojekt.Activity;


import android.content.Intent;
import android.graphics.Color;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appprojekt.Activity.Database.Note;
import com.example.appprojekt.R;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

public class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.NoteViewHolder> {


    private List<Note> notes;


    public NoteAdapter(List<Note> notes) {

        this.notes = notes;
    }

    public void setNotes(List<Note> notes) {
        this.notes = notes;
        notifyDataSetChanged();
    }


    public Note getNoteAtPosition(int position) {
        return notes.get(position);
    }

    @NonNull
    @Override
    public NoteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.note_item, parent, false);
        return new NoteViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull NoteViewHolder holder, int position) {

        String category = notes.get(position).getCategory();
        holder.noteCategory.setText(category);
        int categoryColor = getCategoryColor(category);
        holder.noteCategory.setTextColor(categoryColor);

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy", Locale.getDefault());
        String creationDate = dateFormat.format(notes.get(position).getDateCreated().getTime());


        SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm", Locale.getDefault());
        String creationTime = timeFormat.format(notes.get(position).getDateCreated().getTime());

        holder.noteTitle.setText(notes.get(position).getTitle());
        holder.noteContent.setText(notes.get(position).getNoteText());
        holder.noteCategory.setText(notes.get(position).getCategory());
        holder.noteCreationDate.setText(creationDate + " " + creationTime);


    }

    private int getCategoryColor(String category) {
        int color;
        switch (category) {
            case "Arbeit":
                color = Color.parseColor("#990000");
                break;
            case "Persönlich":
                color = Color.parseColor("#009900");
                break;
            case "Einkaufsliste":
                color = Color.parseColor("#009999");
                break;
            case "Sport":
                color = Color.parseColor("#800080");
                break;
            case "Reisen":
                color = Color.parseColor("#ff9900");
                break;
            default:
                color = Color.BLACK; // Standardfarbe, falls keine Übereinstimmung gefunden wird
                break;
        }
        return color;
    }

    @Override
    public int getItemCount() {
        return notes != null ? notes.size() : 0;
    }

    class NoteViewHolder extends RecyclerView.ViewHolder implements View.OnCreateContextMenuListener, MenuItem.OnMenuItemClickListener {

        TextView noteTitle, noteContent, noteCreationDate, noteCategory;

        ConstraintLayout showNote;


        NoteViewHolder(@NonNull View itemView) {

            super(itemView);
            noteTitle = itemView.findViewById(R.id.noteTitleTextView);
            noteContent = itemView.findViewById(R.id.noteDescriptionTextView);
            noteCreationDate = itemView.findViewById(R.id.noteDateTextView);
            noteCategory = itemView.findViewById(R.id.noteCategory);
            showNote = itemView.findViewById(R.id.showNote);

            itemView.setOnCreateContextMenuListener(this);

        }

        @Override
        public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
            MenuItem edit = menu.add(Menu.NONE, 1, 1, "EDIT");
            MenuItem show = menu.add(Menu.NONE, 2, 2, "SHOW");
            MenuItem delete = menu.add(Menu.NONE, 3, 3, "DELETE");

            edit.setOnMenuItemClickListener(this);
            show.setOnMenuItemClickListener(this);
            delete.setOnMenuItemClickListener(this);
        }

        @Override
        public boolean onMenuItemClick(MenuItem item) {
            int position = getAdapterPosition();
            if (position != RecyclerView.NO_POSITION) {
                switch (item.getItemId()) {
                    case 1: // EDIT
                        Note noteToEdit = notes.get(position);
                        Intent editIntent = new Intent(itemView.getContext(), EditNoteActivity.class);
                        editIntent.putExtra("noteId", noteToEdit.getId());
                        itemView.getContext().startActivity(editIntent);
                        return true;
                    case 2: // SHOW
                        Note noteToShow = notes.get(position);
                        Intent ShowIntent = new Intent(itemView.getContext(), ShowNoteActivity.class);
                        ShowIntent.putExtra("noteId", noteToShow.getId());
                        itemView.getContext().startActivity(ShowIntent);
                        return true;
                    case 3: // DELETE
                        Note noteToDelete = notes.get(position);
                        Intent intent = new Intent(itemView.getContext(), DeleteNoteActivity.class);
                        intent.putExtra("noteId", noteToDelete.getId());
                        itemView.getContext().startActivity(intent);

                        return true;
                    default:
                        return false;
                }
            } else {
                return false;
            }
        }


        public void setNote(Note note) {

            noteTitle.setText(note.getTitle());
            if (note.getNoteText().trim().isEmpty()) {
                noteContent.setVisibility(View.GONE);

            } else {
                noteContent.setVisibility(View.VISIBLE);
                noteContent.setText(note.getNoteText());
            }

            noteCreationDate.setText(note.getDateCreated().toString());

        }


    }


}

