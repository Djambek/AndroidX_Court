package com.example.courts_test_androidx;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;

import io.paperdb.Paper;

public class FirstRun extends AppCompatActivity {
    String[] citys = {"Москва"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.wtf("WW", "i don't know");
        setContentView(R.layout.activity_first_run);
        Log.wtf("WW", "i don't know");
        // работаю со выпадным списком
        Spinner spinner = findViewById(R.id.spinner);
        ArrayAdapter<String> adapter = new ArrayAdapter(this, R.layout.spinner_items, citys);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        AdapterView.OnItemSelectedListener itemSelectedListener = new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // Получаем выбранный объект
                Paper.book().write("city",  (String) parent.getItemAtPosition(position));
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {}
        };
        spinner.setOnItemSelectedListener(itemSelectedListener);
        spinner.setAdapter(adapter);
        Button b = findViewById(R.id.textButton);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(FirstRun.this, Home.class);
                startActivity(intent);
                finish();
            }
        });

    }
}
