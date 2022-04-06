package com.example.courts_test_androidx;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class Court_case extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_court_case);
        Bundle arguments = getIntent().getExtras();
        String link = arguments.getString("link");

    }
}