package com.zhibin.hotfixdemo;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TextView tvText = findViewById(R.id.tv_text);
        tvText.setOnClickListener(v -> {
            Log.d("tvOnclick", "yes");
        });

        Log.d("hello", "world");
        Log.d("hello", "world");
        Log.d("hello", "world");
        Log.d("hello", "world");
        Log.d("hello", "world");
        Log.d("hello", "world");
        Log.d("hello", "world");
    }

    void hello() {
        System.out.println("world");
    }

    void hello2() {
        System.out.println("hello");
        Log.d("hello", "world");
        Log.d("hello", "world");
        Log.d("hello", "world");
        Log.d("hello", "world1111");
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
        Log.d("hello", "world");
        Log.d("hello", "world");
        Log.d("hello", "world222");
        Log.d("hello", "world33");
        Log.d("hello", "world");

    }

    void hello1() {
        System.out.println("hello");
        Log.d("hello", "world");
        Log.d("hello", "world");
        Log.d("hello", "world11111");
        Log.d("hello", "world");
        Log.d("hello", "world");
        Log.d("hello", "world");
        Log.d("hello", "world");
    }

    void he() {
        String expr = "(public|private|protected)?\\s{0,}([\\w<>,\\[\\]]+)?\\s{0,}([a-z][\\w]+)\\s?\\(([A-Z][\\w\\[\\]<,>]+\\s+[\\w]+\\s?,?\\s?){0," +
                "}\\)\\s?([\\w]{6,}\\s?[\\w]{9})?\\s{0,}\\{\\s?";
    }





}
