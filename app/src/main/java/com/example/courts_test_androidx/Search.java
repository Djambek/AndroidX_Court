package com.example.courts_test_androidx;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import com.google.android.material.textfield.TextInputLayout;


import java.util.Arrays;

import io.paperdb.Paper;


public class Search extends AppCompatActivity {

    String[] courts  = {"Все суды", "Басманный районный суд","Замоскворецкий районный суд",
            "Мещанский районный суд","Пресненский районный суд","Таганский районный суд",
            "Тверской районный суд","Хамовнический районный", "Головинский районный суд", "Коптевский районный суд","Савёловский районный суд",
            "Тимирязевский районный суд", "Бабушкинский районный суд", "Бутырский районный суд", "Останкинский районный суд",
            "Измайловский районный суд", "Перовский районный суд", "Преображенский районный суд", "Кузьминский районный суд", "Лефортовский районный суд",
            "Люблинский районный суд", "Нагатинский районный суд", "Симоновский районный суд", "Чертановский районный суд", "Гагаринский районный суд", "Зюзинский районный суд",
            "Черёмушкинский районный суд", "Дорогомиловский районный суд", "Кунцевский районный суд", "Никулинский районный суд", "Солнцевский районный суд",
            "Тушинский районный суд", "Хорошёвский районный суд", "Зеленоградский районный суд"};
    String selected_cout;


    String[] instance =  {"Первая", "Апелляционная", "Кассационная", "Надзорная"};
    String selected_instance;

    String[] disturbance = {"Все типы судопроизводств", "Административное", "Гражданское",
            "Об административных правонарушениях", "Первичные документы", "Производство по материалам",
            "Уголовное"};
    String selected_disturbance;

    String unique_id;
    String number_case;
    String participants;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        // spinner for courts
        Spinner spinner_court = findViewById(R.id.spinner3);
        Arrays.sort(courts);
        ArrayAdapter<String> adapter = new ArrayAdapter(this, R.layout.spinner_items, courts);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_court.setAdapter(adapter);
        AdapterView.OnItemSelectedListener itemSelectedListener = new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // Получаем выбранный объект
                selected_cout = (String) parent.getItemAtPosition(position);
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {}
        };
        spinner_court.setOnItemSelectedListener(itemSelectedListener);
        spinner_court.setAdapter(adapter);

        // spinner for instance
        Spinner spinner_instance = findViewById(R.id.spinner4);
        ArrayAdapter<String> adapter_instance = new ArrayAdapter(this, R.layout.spinner_items, instance);
        adapter_instance.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_instance.setAdapter(adapter_instance);
        AdapterView.OnItemSelectedListener itemSelectedListener_instance = new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // Получаем выбранный объект
                selected_instance = (String) parent.getItemAtPosition(position);
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {}
        };
        spinner_instance.setOnItemSelectedListener(itemSelectedListener_instance);
        spinner_instance.setAdapter(adapter_instance);

        // spinner for instance
        Spinner spinner_disturbance = findViewById(R.id.spinner5);
        ArrayAdapter<String> adapter_disturbance = new ArrayAdapter(this, R.layout.spinner_items, disturbance);
        adapter_disturbance.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_disturbance.setAdapter(adapter_disturbance);
        AdapterView.OnItemSelectedListener itemSelectedListener_disturbance = new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // Получаем выбранный объект
                selected_disturbance = (String) parent.getItemAtPosition(position);
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {}
        };
        spinner_disturbance.setOnItemSelectedListener(itemSelectedListener_disturbance);
        spinner_disturbance.setAdapter(adapter_disturbance);

        Button b = findViewById(R.id.textButton);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TextInputLayout textInputLayout_participants = findViewById(R.id.textInputLayout3);
                participants = textInputLayout_participants.getEditText().getText().toString().trim();

                TextInputLayout textInputLayout_unique_id = findViewById(R.id.textInputLayout);
                unique_id = textInputLayout_unique_id.getEditText().getText().toString().trim();

                Log.d("TEX_T", participants);
                String[] params = new String[8];
                params[0] = Paper.book().read("city");
                params[1] =selected_cout;
                params[2] = unique_id;
                params[3] = selected_instance;
                params[4] = "";
                params[5] = number_case;
                params[6] = participants;
                params[7] = selected_disturbance;
                Intent intent = new Intent(Search.this, Searching_result.class);
                intent.putExtra("params", params);
                startActivity(intent);
            }
        });



    }
}