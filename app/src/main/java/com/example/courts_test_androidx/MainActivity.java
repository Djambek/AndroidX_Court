package com.example.courts_test_androidx;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import io.paperdb.Book;
import io.paperdb.Paper;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.wtf("e", "Starting");

        Paper.init(this);
        if(Paper.book().read("city").equals("")){
            Intent intent = new Intent(this, FirstRun.class);
            startActivity(intent);
        }else {
            Intent intent = new Intent(this, Home.class);
            startActivity(intent);
        }
        setContentView(R.layout.activity_main);
        finish();
    }
}