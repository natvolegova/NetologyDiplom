package com.example.netologydiplom;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class NotesRecyclerAdapter extends RecyclerView.Adapter<NotesRecyclerAdapter.ViewHolder> {
    private List<Note> notesModels;
    private Context context;

    //конструктор адаптера
    public NotesRecyclerAdapter(Context context, List<Note> notesModels) {
        this.context = context;
        this.notesModels = notesModels;
    }

    @NonNull
    @Override
    public NotesRecyclerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.note_item, parent, false);
        return new ViewHolder(view);
    }

    //заполняем элемент списка данными
    @Override
    public void onBindViewHolder(@NonNull NotesRecyclerAdapter.ViewHolder holder, int position) {
        SimpleDateFormat formatter = new SimpleDateFormat(Constants.DATE_FORMAT);

        String noteTitle = notesModels.get(position).getNoteTitle();
        String noteDescription = notesModels.get(position).getNoteDescription();
        Date dateDeadline = notesModels.get(position).getDateDeadline();

        if (!noteTitle.equals("")) {
            holder.tvTitle.setText(noteTitle);
            holder.tvTitle.setVisibility(View.VISIBLE);
        }
        if (!noteDescription.equals("")) {
            holder.tvDescription.setText(noteDescription);
            holder.tvDescription.setVisibility(View.VISIBLE);
        }
        if (dateDeadline != null) {
            holder.tvDate.setText(formatter.format(dateDeadline));
            holder.tvDate.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public int getItemCount() {
        return notesModels.size();
    }

    public Note getById(int position) {
        return notesModels.get(position);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvTitle;
        TextView tvDescription;
        TextView tvDate;

        //конструктор, находим все доступные поля на itemView
        public ViewHolder(View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tv_title);
            tvDescription = itemView.findViewById(R.id.tv_desc);
            tvDate = itemView.findViewById(R.id.tv_date);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    editNote(getAdapterPosition());
                }
            });
            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    removeNote(getAdapterPosition());
                    return true;
                }
            });
        }
    }

    //открытие активити для редактирования существующей записи
    private void editNote(int position) {
        Note note = notesModels.get(position);
        Intent intent = new Intent(context, AddNoteActivity.class);
        intent.putExtra("id", note.getId());
        context.startActivity(intent);
    }

    //удаление выделенной записи
    private void removeNote(final int position) {
        final NotesRepository noteRepository = App.getInstance().getNotesRepository();
        final Note note = notesModels.get(position);

        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(context);
        dialogBuilder.setMessage(R.string.dialog_note_message_delete);
        dialogBuilder.setTitle(R.string.dialog_note_title_delete);
        dialogBuilder.setIcon(R.drawable.ic_delete);
        dialogBuilder.setPositiveButton(R.string.delete, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                noteRepository.deleteNote(note); //удаляем данные из базы
                notesModels.remove(position); //удаляем данны из текущего списка
                notifyDataSetChanged(); //уведомляем адаптер об изменениях
            }
        });
        dialogBuilder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
            }
        });
        AlertDialog dialog = dialogBuilder.create();
        dialog.show();
    }
}
