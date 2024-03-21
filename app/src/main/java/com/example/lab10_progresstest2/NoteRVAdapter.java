package com.example.lab10_progresstest2;

import android.content.Context;
import android.content.Intent;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class NoteRVAdapter extends RecyclerView.Adapter<NoteRVAdapter.ViewHolder> {
    private ArrayList<NoteModal> noteModalArrayList;
    private Context context;
    private DBHandler dbHandler;

    public NoteRVAdapter(ArrayList<NoteModal> noteModalArrayList, Context context) {
        this.noteModalArrayList = noteModalArrayList;
        this.context = context;
        dbHandler = new DBHandler(context);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.note_cell, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NoteRVAdapter.ViewHolder holder, int position) {
        NoteModal note = noteModalArrayList.get(position);

        holder.txtNoteTitle.setText(note.getTitle());
        holder.txtNoteContent.setText(note.getContent());
        holder.txtNoteDate.setText(note.getDate());
        holder.txtNoteTime.setText(note.getTime());

        holder.id = noteModalArrayList.get(position).getId();

        // below line is to add on click listener for our recycler view item.
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // on below line we are calling an intent.
                Intent i = new Intent(context, CreateNoteActivity.class);

                // below we are passing all our values.
                i.putExtra("id", note.getId());
                i.putExtra("title", note.getTitle());
                i.putExtra("content", note.getContent());
                i.putExtra("ACTION", "EDIT");

                // starting our activity.
                context.startActivity(i);
            }
        });    }

    @Override
    public int getItemCount() {
        return noteModalArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnCreateContextMenuListener {

        private TextView txtNoteTitle, txtNoteContent, txtNoteDate, txtNoteTime;
        private String id;

        private LinearLayout linearLayout;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            txtNoteTitle = itemView.findViewById(R.id.cellTitle);
            txtNoteContent = itemView.findViewById(R.id.cellContent);
            txtNoteDate = itemView.findViewById(R.id.cellDate);
            txtNoteTime = itemView.findViewById(R.id.cellTime);
            linearLayout = itemView.findViewById(R.id.layoutNote);
            linearLayout.setOnCreateContextMenuListener(this);
        }

        @Override
        public void onCreateContextMenu(ContextMenu contextMenu, View view, ContextMenu.ContextMenuInfo contextMenuInfo) {
            contextMenu.setHeaderTitle("Select one");
            MenuItem deleteMenuItem = contextMenu.add(getAdapterPosition(), 101, 0, "Edit");
            MenuItem editMenuItem = contextMenu.add(getAdapterPosition(), 102, 0, "Delete");
            deleteMenuItem.setOnMenuItemClickListener(onChangedItem);
            editMenuItem.setOnMenuItemClickListener(onChangedItem);
        }

        private final MenuItem.OnMenuItemClickListener onChangedItem = new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case 101:
                        Intent intent = new Intent(context, CreateNoteActivity.class);

                        intent.putExtra("id", id);
                        intent.putExtra("title", txtNoteTitle.getText().toString());
                        intent.putExtra("content", txtNoteContent.getText().toString());
                        intent.putExtra("ACTION", "EDIT");
                        context.startActivity(intent);

                        return true;
                    case 102:
                        dbHandler.deleteNote(id);
                        noteModalArrayList.remove(getAdapterPosition());
                        notifyDataSetChanged();
                        Toast.makeText(context, "Deleted successfully", Toast.LENGTH_SHORT).show();
                        return true;
                    default:
                        return false;
                }
            }
        };
    }}
