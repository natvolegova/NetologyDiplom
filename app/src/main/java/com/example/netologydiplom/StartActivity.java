package com.example.netologydiplom;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.ContextThemeWrapper;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class StartActivity extends AppCompatActivity {
    private Button btn00, btn01, btn02, btn03, btn04, btn05, btn06, btn07, btn08, btn09, btnBack;
    private TextView tvMessage;
    private LinearLayout llPassValue;
    private Keystore keyStore;
    private String resultText = "";
    private int currentInputValue = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
    }

    protected void onResume() {
        super.onResume();
        keyStore = App.getInstance().getKeystore();
        //первоначальная настройка, проверяем есть ли пароль
        if (keyStore.hasPin(this)) {
            initView();
            initVisualPass();
        } else {
            Intent intent = new Intent(StartActivity.this, SettingsActivity.class);
            startActivity(intent);
        }
    }

    private void initView() {
        btn00 = findViewById(R.id.btn_00);
        btn01 = findViewById(R.id.btn_01);
        btn02 = findViewById(R.id.btn_02);
        btn03 = findViewById(R.id.btn_03);
        btn04 = findViewById(R.id.btn_04);
        btn05 = findViewById(R.id.btn_05);
        btn06 = findViewById(R.id.btn_06);
        btn07 = findViewById(R.id.btn_07);
        btn08 = findViewById(R.id.btn_08);
        btn09 = findViewById(R.id.btn_09);
        btnBack = findViewById(R.id.btn_back);
        tvMessage = findViewById(R.id.tv_message);
        btn00.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addResult("0");
            }
        });
        btn01.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addResult("1");
            }
        });
        btn02.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addResult("2");
            }
        });
        btn03.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addResult("3");
            }
        });
        btn04.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addResult("4");
            }
        });
        btn05.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addResult("5");
            }
        });
        btn06.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addResult("6");
            }
        });
        btn07.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addResult("7");
            }
        });
        btn08.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addResult("8");
            }
        });
        btn09.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addResult("9");
            }
        });
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteLast();
            }
        });
    }

    // добавляем визуальные элементы по количеству символов в пароле
    private void initVisualPass() {
        llPassValue = findViewById(R.id.ll_pass_value);
        for (int i = 1; i <= Constants.PASS_LENGTH; i++) {
            View view_pass = new View(this);
            view_pass.setLayoutParams(new LinearLayout.LayoutParams((int) getResources().getDimension(R.dimen.corner_radius_lg), (int) getResources().getDimension(R.dimen.corner_radius_lg)));
            view_pass.setBackground(getResources().getDrawable(R.drawable.pass_round_dark));
            view_pass.setId(i);
            llPassValue.addView(view_pass);
        }
    }

    //добавляем цифры
    private void addResult(String value) {
        if (resultText.length() < Constants.PASS_LENGTH) {
            resultText += value;
            currentInputValue++;
            changeVisualPass(currentInputValue, "visualLight");
            checkResult();
        }
    }

    //удаляем цифры
    private void deleteLast() {
        if (resultText.length() > 0) {
            resultText = resultText.substring(0, resultText.length() - 1);
            changeVisualPass(currentInputValue, "visualDark");
            currentInputValue--;
            checkResult();
        }
    }

    //изменяем визуальное оформление данных пароля
    private void changeVisualPass(int currentId, String visualValue) {
        View view_pass = (View) findViewById(currentId);
        if (visualValue.equals("visualLight")) {
            view_pass.setBackground(getResources().getDrawable(R.drawable.pass_round_light));
        } else {
            view_pass.setBackground(getResources().getDrawable(R.drawable.pass_round_dark));
        }
    }

    //проверяем итоговое значение
    private void checkResult() {
        //длина строки пароля совпадает с количеством необходимых символов пароля
        if (resultText.length() == Constants.PASS_LENGTH) {
            if (keyStore.checkPin(this, resultText)) {
                Intent intent = new Intent(StartActivity.this, MainActivity.class);
                startActivity(intent);
            } else {
                showErrorMessage(R.string.msg_pass_error);
            }
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

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
        return;
    }
}
