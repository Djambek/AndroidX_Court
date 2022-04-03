package com.example.courts_test_androidx;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import java.util.ArrayList;

public class Searching_result extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_searching_result);
        Bundle arguments = getIntent().getExtras();
        String[] params = arguments.getStringArray("params").clone();
        Search_court_case_lib search = new Search_court_case_lib(
                params[0],
                params[1],
                params[2],
                params[3],
                params[4],
                params[5],
                params[6],
                params[7]
        );
        ArrayList<String> short_info = new ArrayList<>();

    }
}