package com.example.netologydiplom;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class AddNoteActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private TextView tvMessage;
    private EditText etTitle, etDescription, etDeadlineDate;
    private LinearLayout llDeadlineBox;
    private CheckBox chbDeadLine;

    private NotesRepository noteRepository;
    private int noteid;
    private Note currentNote;
    private SimpleDateFormat formatter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_note);
        formatter = new SimpleDateFormat(Constants.DATE_FORMAT);
        noteRepository = App.getInstance().getNotesRepository();
        initView();
        //получение данных из intent
        Intent intent = getIntent();
        noteid = intent.getIntExtra("id", -1);
        if (noteid >= 0) {
            initNoteValue();
        }

    }

    //заполнение полей данными существующей записи
    private void initNoteValue() {
        currentNote = noteRepository.getNoteById(noteid);
        etTitle.setText(currentNote.getNoteTitle());
        etDescription.setText(currentNote.getNoteDescription());
        Date dateDeadline = currentNote.getDateDeadline();
        if (dateDeadline != null) {
            chbDeadLine.setChecked(true);
            llDeadlineBox.setVisibility(View.VISIBLE);
            etDeadlineDate.setText(formatter.format(dateDeadline));
        }
    }
    //сохраняем запись
    public void onSaveNoteClick() {
        String nameStr = etTitle.getText().toString();
        String descStr = etDescription.getText().toString();
        String deadlineDateStr = etDeadlineDate.getText().toString();
        Date date = Calendar.getInstance().getTime();

        Date dateDeadline = null;
        int isDeadline = 0;
        try {
            dateDeadline = formatter.parse(deadlineDateStr);
            isDeadline = 1;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        //проверка на пустые данные
        if (nameStr.equals("") && descStr.equals("") && deadlineDateStr.equals("")) {
            //все данные пустые, показывем сообщение об ошибке
            showErrorMessage(R.string.msg_note_add_error);
        } else {
            //проверяем данные получены из intent
            if (noteid >= 0) {
                // обновляем существующие данные
                currentNote.setNoteTitle(nameStr);
                currentNote.setNoteDescription(descStr);
                currentNote.setDatePub(date);
                currentNote.setDateDeadline(dateDeadline);
                currentNote.setIsDeadLine(isDeadline);
                noteRepository.updateNote(currentNote);
            } else {
                // добавляем новые данные
                Note note = new Note(nameStr, date, dateDeadline, isDeadline, descStr);
                noteRepository.addNote(note);
            }
            finish();
        }
    }

    //показ диалогового окна с выбором даты
    public void onAddDateDeadlineClick(View view) throws ParseException {
        showCalendarDialog();
    }

    private void showCalendarDialog() throws ParseException {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = this.getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.calendar_dialog, null);
        dialogBuilder.setView(dialogView);

        final DatePicker datePicker = dialogView.findViewById(R.id.datePicker);
        Calendar currentDeteOfDeadline = Calendar.getInstance();
        //проверяем, если текущее значение даты не пустое, присваиваем календарю существующую дату
        if (!etDeadlineDate.getText().toString().equals("")) {
            Date currentDete = formatter.parse(etDeadlineDate.getText().toString());
            currentDeteOfDeadline.setTime(currentDete);
        }
        datePicker.init(
                currentDeteOfDeadline.get(Calendar.YEAR),
                currentDeteOfDeadline.get(Calendar.MONTH),
                currentDeteOfDeadline.get(Calendar.DATE),
                null);
        //обработка нажатий диалогового окна
        dialogBuilder.setPositiveButton(R.string.Ok, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                etDeadlineDate.setText(new StringBuilder()
                        .append(datePicker.getYear()).append("-")
                        .append(datePicker.getMonth() + 1).append("-")
                        .append(datePicker.getDayOfMonth())
                );
            }
        });
        dialogBuilder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                finish();
            }
        });
        AlertDialog calendarDialog = dialogBuilder.create();
        calendarDialog.show();
    }


    private void initView() {
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        etTitle = findViewById(R.id.et_title);
        etDescription = findViewById(R.id.et_description);
        etDeadlineDate = findViewById(R.id.et_deadlinedate);
        llDeadlineBox = findViewById(R.id.ll_deadline_box);
        chbDeadLine = findViewById(R.id.chb_deadline);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AddNoteActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
        chbDeadLine.setOnCheckedChangeListener (new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (compoundButton.isChecked()) {
                        Date date = Calendar.getInstance().getTime();
                        etDeadlineDate.setText(formatter.format(date));
                    llDeadlineBox.setVisibility(View.VISIBLE);
                } else {
                    llDeadlineBox.setVisibility(View.GONE);
                    etDeadlineDate.setText("");
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        MenuItem settingsMenuItem = menu.findItem(R.id.settings);
        settingsMenuItem.setVisible(false);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.save:
                onSaveNoteClick();
        }
        return super.onOptionsItemSelected(item);
    }

    private void showErrorMessage(final int message) {
        tvMessage = findViewById(R.id.tv_message);
        CountDownTimer timer = new CountDownTimer(3000, 1000) {
            public void onTick(long millisUntilFinished) {
                tvMessage.setVisibility(View.VISIBLE);
                tvMessage.setText(getResources().getText(message));
            }
            public void onFinish() {
                tvMessage.setVisibility(View.GONE);
            }
        };
        timer.start();
    }
}
