package com.zhibin.hotfixdemo;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d("hello", "world");
        Log.d("hello", "world");
        Log.d("hello", "world");
        Log.d("hello", "world");
        Log.d("hello", "world");
        Log.d("hello", "world");
        Log.d("hello", "world");
    }

    void hello() {
        System.out.println("hello");
    }

    void hello1() {
        System.out.println("hello");
        Log.d("hello", "world");
        Log.d("hello", "world");
        Log.d("hello", "world");
        Log.d("hello", "world");
        Log.d("hello", "world");
        Log.d("hello", "world");
        Log.d("hello", "world");
    }

    void hello2() {
        System.out.println("hello");
        Log.d("hello", "world");
        Log.d("hello", "world");
        Log.d("hello", "world");
        Log.d("hello", "world");
        Log.d("hello", "world");
        Log.d("hello", "world");
        Log.d("hello", "world");
    }

    void hello3() {
        System.out.println("hello");
        Log.d("hello", "world");
        Log.d("hello", "world");
        Log.d("hello", "world");
        Log.d("hello", "world");
        Log.d("hello", "world");
        Log.d("hello", "world");
        Log.d("hello", "world");
    }

}
