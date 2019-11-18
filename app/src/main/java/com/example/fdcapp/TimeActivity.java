package com.example.fdcapp;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import java.util.Calendar;
import java.util.Date;

public class TimeActivity extends AppCompatActivity {


    LinearLayout time, day;
    Button btnPOS, btnNEG;
    int selectYear, selectMonth, selectDay, selectHour, selectMin;
    TextView tvYear, tvMonth, tvDay, tvHour, tvMinute;

    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.time);

        tvYear = (TextView) findViewById(R.id.tvYear);
        tvMonth = (TextView) findViewById(R.id.tvMonth);
        tvDay = (TextView) findViewById(R.id.tvDay);
        tvHour = (TextView) findViewById(R.id.tvHour);
        tvMinute = (TextView) findViewById(R.id.tvMinute);

        btnPOS = ( Button)findViewById(R.id.btnPOS);
        btnNEG = findViewById(R.id.btnNEG);

        init();



    }


    @RequiresApi(api = Build.VERSION_CODES.N)
    private void init() {

        //Calendar를 이용하여 년, 월, 일, 시간, 분을 PICKER에 넣어준다.
        final Calendar cal = Calendar.getInstance();


        //DATE PICKER DIALOG
        findViewById(R.id.day).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                DatePickerDialog dialog = new DatePickerDialog(TimeActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int date) {

                        String msg = String.format("%d 년 %d 월 %d 일", year, month + 1, date);
                        Toast.makeText(TimeActivity.this, msg, Toast.LENGTH_SHORT).show();
                        selectYear = year;
                        selectMonth = month + 1;
                        selectDay = date;

                        tvYear.setText(Integer.toString(selectYear));
                        tvMonth.setText(Integer.toString(selectMonth));
                        tvDay.setText(Integer.toString(selectDay));


                    }
                }, cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DATE));

//                dialog.getDatePicker().setMaxDate(new Date().getTime());    //입력한 날짜 이후로 클릭 안되게 옵션
                dialog.show();

            }

        });
        //TIME PICKER DIALOG
        findViewById(R.id.time).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                TimePickerDialog dialog = new TimePickerDialog(TimeActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int hour, int min) {

                        String msg = String.format(" %d시 %d분", hour, min);
                        Toast.makeText(TimeActivity.this, msg, Toast.LENGTH_SHORT).show();
                        selectHour = hour;
                        selectMin = min;

                        tvHour.setText(Integer.toString(timePicker.getCurrentHour()));
                        tvMinute.setText(Integer.toString(timePicker.getCurrentMinute()));

                    }
                }, cal.get(Calendar.HOUR_OF_DAY), cal.get(Calendar.MINUTE), true);  //마지막 boolean 값은 시간을 24시간으로 보일지 아닐지

                dialog.show();

            }
        });


        btnPOS.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MenuActivity.class);
                //intent.putExtra("time",Date11);
                intent.putExtra("year", selectYear);
                intent.putExtra("month", selectMonth);
                intent.putExtra("day", selectDay);
                intent.putExtra("hour", selectHour);
                intent.putExtra("min", selectMin);
                startActivity(intent);
                finish();

                Toast.makeText(TimeActivity.this, "예약 되었습니다.", Toast.LENGTH_SHORT).show();
            }
        });
        btnNEG.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MenuActivity.class);
                startActivity(intent);
                Toast.makeText(TimeActivity.this, "취소되었습니다.", Toast.LENGTH_SHORT).show();
            }
        });
        //액션바 설정하기//
        //액션바 타이틀 변경하기
        //getSupportActionBar().setTitle("F D C");
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.actime);
        //액션바 배경색 변경
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(0xFFFFFFFF));
        //홈버튼 표시
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.home);
        //액션바 숨기기
        //hideActionBar();
    }

    //액션바 클릭 -> 메인 화면으로 인텐트
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == android.R.id.home) {
            Intent intent = new Intent(TimeActivity.this, MainActivity.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);

    }
}


