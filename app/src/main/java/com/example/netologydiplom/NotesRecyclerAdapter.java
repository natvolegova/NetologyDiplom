package com.example.netologydiplom;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class NotesRecyclerAdapter extends RecyclerView.Adapter<NotesRecyclerAdapter.ViewHolder> {
    private List<Note> notesModels = new ArrayList<>();
    private Context context;
    private OnNoteListener mOnNoteListener;

    //конструктор адаптера
    public NotesRecyclerAdapter(Context context, List<Note> notesModels, OnNoteListener mOnNoteListener) {
        this.context = context;
        this.notesModels = notesModels;
        this.mOnNoteListener = mOnNoteListener;
    }

    @NonNull
    @Override
    public NotesRecyclerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.note_item, parent, false);
        return new ViewHolder(view, mOnNoteListener);
    }

    @Override
    public void onBindViewHolder(@NonNull NotesRecyclerAdapter.ViewHolder holder, int position) {
        SimpleDateFormat formatter = new SimpleDateFormat(Constants.DATE_FORMAT);

        String noteTitle = notesModels.get(position).getNoteTitle();
        String noteDescription = notesModels.get(position).getNoteDescription();
        Date dateDeadline = notesModels.get(position).getDateDeadline();

        if (!noteTitle.equals(null)) {
            holder.tvTitle.setText(noteTitle);
            holder.tvTitle.setVisibility(View.VISIBLE);
        }
        if (!noteDescription.equals(null)) {
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

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {
        TextView tvTitle;
        TextView tvDescription;
        TextView tvDate;
        OnNoteListener onNoteListener;

        //конструктор, находим все доступные поля на itemView и определяем слушателя кнопок
        public ViewHolder(View itemView, OnNoteListener onNoteListener) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tv_title);
            tvDescription = itemView.findViewById(R.id.tv_desc);
            tvDate = itemView.findViewById(R.id.tv_date);
            this.onNoteListener = onNoteListener;
            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);
        }

        @Override
        public void onClick(View view) {
            onNoteListener.onNoteClick(getAdapterPosition());
        }

        @Override
        public boolean onLongClick(View view) {
            onNoteListener.onNoteLongClick(getAdapterPosition());
            return true;
        }
    }

    //интерфейс, переопределяем нажатия кнопок
    public interface OnNoteListener {
        void onNoteClick(int position);

        void onNoteLongClick(int position);
    }
}
