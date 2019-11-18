package com.example.fdcapp;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Switch;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

public class MenuActivity extends AppCompatActivity {


    Button btntime, search1;
    Switch switch1, switch2;
    ImageView now1, now2, now3, now4, now5, battImg,
            con1, cno2, cno3, con4, con5;
    TextView battTxt,timetext;
    ImageButton plus, minus;
    ProgressBar progress1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu);

        btntime = (Button) findViewById(R.id.btntime);
        now1 = findViewById(R.id.now1);
        now2 = findViewById(R.id.now2);
        now3 = findViewById(R.id.now3);
        now4 = findViewById(R.id.now4);
        now5 = findViewById(R.id.now5);

        final int[] i = {1};


        Intent intent = getIntent();
        Integer year = (Integer) intent.getIntExtra("year", 0);
        Integer month = (Integer) intent.getIntExtra("month", 0);
        Integer day = (Integer) intent.getIntExtra("day", 0);
        Integer hour = (Integer) intent.getIntExtra("hour", 0);
        Integer min = (Integer) intent.getIntExtra("min", 0);

        timetext = findViewById(R.id.timetext);
        timetext.setText((year) + "/"
                + (month) + "/"
                + (day) + "   "
                + (hour) + ":"
                + (min) + " ");



//시간설정 인텐트

        btntime.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), TimeActivity.class);
                startActivity(intent);


            }
        });
//현재 위치 이미지 설정
        now1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (i[0] % 2 == 1) {
                    now1.setImageResource(R.drawable.con);
                    now2.setImageResource(R.drawable.uncon);
                    now3.setImageResource(R.drawable.uncon);
                    now4.setImageResource(R.drawable.uncon);
                    now5.setImageResource(R.drawable.uncon);
                    i[0]++;
                } else {
                    now1.setImageResource(R.drawable.con);
                    now2.setImageResource(R.drawable.uncon);
                    now3.setImageResource(R.drawable.uncon);
                    now4.setImageResource(R.drawable.uncon);
                    now5.setImageResource(R.drawable.uncon);
                }
            }
        });
        now2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (i[0] % 2 == 1) {
                    now1.setImageResource(R.drawable.uncon);
                    now2.setImageResource(R.drawable.con);
                    now3.setImageResource(R.drawable.uncon);
                    now4.setImageResource(R.drawable.uncon);
                    now5.setImageResource(R.drawable.uncon);
                    i[0]++;
                } else {
                    now1.setImageResource(R.drawable.uncon);
                    now2.setImageResource(R.drawable.con);
                    now3.setImageResource(R.drawable.uncon);
                    now4.setImageResource(R.drawable.uncon);
                    now5.setImageResource(R.drawable.uncon);
                }
            }
        });
        now3.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (i[0] % 2 == 1) {
                    now1.setImageResource(R.drawable.uncon);
                    now2.setImageResource(R.drawable.uncon);
                    now3.setImageResource(R.drawable.con);
                    now4.setImageResource(R.drawable.uncon);
                    now5.setImageResource(R.drawable.uncon);
                    i[0]++;
                } else {
                    now1.setImageResource(R.drawable.uncon);
                    now2.setImageResource(R.drawable.uncon);
                    now3.setImageResource(R.drawable.con);
                    now4.setImageResource(R.drawable.uncon);
                    now5.setImageResource(R.drawable.uncon);
                }
            }
        });
        now4.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (i[0] % 2 == 1) {
                    now1.setImageResource(R.drawable.uncon);
                    now2.setImageResource(R.drawable.uncon);
                    now3.setImageResource(R.drawable.uncon);
                    now4.setImageResource(R.drawable.con);
                    now5.setImageResource(R.drawable.uncon);
                    i[0]++;
                } else {
                    now1.setImageResource(R.drawable.uncon);
                    now2.setImageResource(R.drawable.uncon);
                    now3.setImageResource(R.drawable.uncon);
                    now4.setImageResource(R.drawable.con);
                    now5.setImageResource(R.drawable.uncon);
                }
            }
        });
        now5.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (i[0] % 2 == 1) {
                    now1.setImageResource(R.drawable.uncon);
                    now2.setImageResource(R.drawable.uncon);
                    now3.setImageResource(R.drawable.uncon);
                    now4.setImageResource(R.drawable.uncon);
                    now5.setImageResource(R.drawable.con);
                    i[0]++;
                } else {
                    now1.setImageResource(R.drawable.uncon);
                    now2.setImageResource(R.drawable.uncon);
                    now3.setImageResource(R.drawable.uncon);
                    now4.setImageResource(R.drawable.uncon);
                    now5.setImageResource(R.drawable.con);
                }
            }
        });


//프로그레스바 설정
        progress1 = (ProgressBar) findViewById(R.id.progress1);
        progress1.setProgress(0);

        ImageButton plus = findViewById(R.id.plus);
        plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progress1.incrementProgressBy(34);
            }

        });
        ImageButton minus = findViewById(R.id.minus);
        minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progress1.incrementProgressBy(-34);
            }
        });

//액션바 설정하기//
        //액션바 타이틀 변경하기
        //getSupportActionBar().setTitle("F D C");
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.acmenu);
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
            Intent intent = new Intent(MenuActivity.this, MainActivity.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);

    }
}




