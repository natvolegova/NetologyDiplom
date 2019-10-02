package com.example.netologydiplom;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.InputFilter;
import android.text.InputType;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

public class SettingsActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private Keystore keyStore;
    private EditText etInputValue;
    private ImageButton btnShowHidePass;
    private Button btnSave;
    private TextView tvMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        keyStore = App.getInstance().getKeystore();
        initView();
    }

    private void initView() {
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(getString(R.string.settings_name));
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        etInputValue = findViewById(R.id.et_input_value);
        etInputValue.setFilters(new InputFilter[]{new InputFilter.LengthFilter(Constants.PASS_LENGTH)}); //задаем максимальную длину вводимого значения

        //показ и скрытие значений пароля
        btnShowHidePass = findViewById(R.id.btn_show_pass);
        btnShowHidePass.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                showHidePass(motionEvent);
                return true;
            }
        });

        //сохранение пароля
        btnSave = findViewById(R.id.btn_save);
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                savePassword();
            }
        });
    }

    private void showHidePass(MotionEvent motionEvent) {
        switch (motionEvent.getAction()) {
            case MotionEvent.ACTION_DOWN: // нажатие
                etInputValue.setInputType(InputType.TYPE_CLASS_NUMBER);
                btnShowHidePass.setImageResource(R.drawable.ic_visibility);
                break;
            default:
                etInputValue.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_VARIATION_PASSWORD);
                btnShowHidePass.setImageResource(R.drawable.ic_visibility_off);
                break;
        }
    }

    private void savePassword() {
        String passValue = etInputValue.getText().toString();
        //проверяем длину пароля
        if (passValue.length() < Constants.PASS_LENGTH || passValue == null) {
            showErrorMessage(R.string.msg_pass_error);
            etInputValue.setText("");
        } else {
            keyStore.saveNew(this, passValue);
            Intent intent = new Intent(SettingsActivity.this, MainActivity.class);
            startActivity(intent);
        }
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
