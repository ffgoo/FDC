package com.example.fdcapp;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;


public class LoadingActivity extends Activity {
    Handler handler = new Handler();
    Runnable r = new Runnable() {
        @Override
        public void run() {
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(intent);
            finish();
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView((R.layout.loading));
    }
    @Override
    protected  void onResume() {
        super.onResume();
        handler.postDelayed(r,2000);
    }
    @Override
    protected void onPause() {
        super.onPause();
        handler.removeCallbacks(r);
    }
}


//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.loading);
//
//
//            startLoading();
//    }
//
//    private void startLoading() {
//
//
//        Handler handler = new Handler();
//        handler.postDelayed(new Runnable() {
//          @Override
//            public void run() {
//           LoadingActivity.this.finish();
//            }
//            }, 2000);
//    }
//}



