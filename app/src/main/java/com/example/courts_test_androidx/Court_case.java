package com.example.courts_test_androidx;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.w3c.dom.Text;

import java.io.IOException;

public class Court_case extends AppCompatActivity {
    JSONObject jsonObject;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_court_case);

        Bundle arguments = getIntent().getExtras();
        String link = "http://ctf.djambek.com:8080/details?link=" + arguments.getString("link");
        try {
            Document res = Jsoup.connect(link).ignoreContentType(true).get();
            jsonObject = new JSONObject(res.text());

        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }

        TextView tv1 = findViewById(R.id.textView15);
        TextView tv2 = findViewById(R.id.textView16);
        TextView tv3 = findViewById(R.id.textView17);
        TextView tv4 = findViewById(R.id.textView18);
        TextView tv5 = findViewById(R.id.textView19);
        TextView tv6 = findViewById(R.id.textView20);
        TextView tv7 = findViewById(R.id.textView21);
        TextView tv8 = findViewById(R.id.textView22);
        TextView tv9 = findViewById(R.id.textView23);
        TextView tv10 = findViewById(R.id.textView24);
        TextView tv11 = findViewById(R.id.textView25);
        TextView tv12 = findViewById(R.id.textView26);
        TextView tv13 = findViewById(R.id.textView27);
        TextView tv14 = findViewById(R.id.textView28);
        TextView tv15 = findViewById(R.id.textView29);
        TextView tv16 = findViewById(R.id.textView30);
        TextView tv17 = findViewById(R.id.textView31);
        TextView tv18 = findViewById(R.id.textView32);


        TextView[] list_tv = new TextView[]{tv1, tv2, tv3, tv4, tv5, tv6,
                tv7, tv8, tv9, tv10, tv11, tv12,
                tv13, tv14, tv15, tv16, tv17, tv18};
        String[] args = new String[]{"id", "number", "number_input_document", "participants",
                "register_date", "date_hearing_first_instance",
                "date_of_appellate_instance", "result_hearing", "number_in_next_instance", "number_in_last_instance", "judge", "category",
                "status", "article", "resons_solve", "date_of_decision"};
        String[] names = new String[]{"Уникальный индификатор дела", "Номер дела", "Номер входящего документа",
                "", "Дата регистрации", "Дата рассмотрения дела в первой инстанции",
                "Дата поступления дела в апелляционную инстанцию", "Результат рассмотрения", "Номер дела в суде вышестоящей инстанции",
                "Номер дела в суде нижестоящей инстанции",
                "Судья", "Категория дела", "Статус",
                "Статья", "Основание решения суда", "Дата вступления решения в силу"};
        Log.d("ASD", list_tv.length+"");
        Log.d("ASD", args.length+"");
        Log.d("ASD", names.length+"");
        int counter = 0;
        Log.d("JSON", String.valueOf(names.length));
        Log.d("JSON", String.valueOf(list_tv.length));
        for (int i = 0; i < args.length; i++) {
            if (!args[i].equals("participants")) {
                Log.d("JSON", args[i]);
                try {
                    if (!jsonObject.getString(args[i]).equals("")) {
                        Log.d("ASD", args[i].equals("number_in_next_instance") + jsonObject.getString("url_number_in_next_instance"));
                        if (args[i].equals("number_in_next_instance") && !jsonObject.getString("url_number_in_next_instance").equals("")) {
                            //list_tv[counter].setTextColor(Color.BLUE);
                            //<font color='#FFFFFF'>
                            Log.d("ASD", "я тута");
                            Log.d("ASD", names[i]);
                            Log.d("ASD", jsonObject.getString(args[i]));

                            list_tv[counter].setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    Intent intent = new Intent(Court_case.this, Court_case.class);
                                    try {
                                        Toast.makeText(getApplicationContext(), "Загрузка...", Toast.LENGTH_SHORT).show();
                                        intent.putExtra("link", jsonObject.getString("url_number_in_next_instance"));
                                        startActivity(intent);
                                        finish();
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                }
                            });
                            list_tv[counter].setText(Html.fromHtml("<font><b>" + names[i] + ": " + "</b></font>" + "<font color='#0066FF'>" + jsonObject.getString(args[i]) + "</font>"));
                            counter += 1;
                        } else {
                            if (args[i].equals("number_in_last_instance") && !jsonObject.getString("url_number_in_last_instance").equals("")) {
                                //list_tv[counter].setTextColor(Color.BLUE);

                                list_tv[counter].setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        Intent intent = new Intent(Court_case.this, Court_case.class);
                                        try {
                                            Toast.makeText(getApplicationContext(), "Загрузка...", Toast.LENGTH_SHORT).show();
                                            intent.putExtra("link", jsonObject.getString("url_number_in_last_instance"));
                                            startActivity(intent);
                                            finish();
                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                });
                                list_tv[counter].setText(Html.fromHtml("<font><b>" + names[i] + ": " + "</b></font>" + "<font color='#0066FF'>" + jsonObject.getString(args[i]) + "</font>"));
                                counter += 1;
                            } else {
                                list_tv[counter].setText(Html.fromHtml("<font><b>" + names[i] + ": " + "</b></font>" + jsonObject.getString(args[i])));
                                counter += 1;
                            }

                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } else {
                Log.d("ASD", "наконец-то участиник");
                try {
                    JSONArray parts = jsonObject.getJSONArray("participants");
                    Log.d("ASD", String.valueOf(parts));
                    for (int j = 0; j < parts.length(); j++) {
                        Log.d("ASD", "Зашли в цикл");
                        Log.d("ASD", counter + "");
                        list_tv[counter].setText(Html.fromHtml("<font><b>" + parts.getJSONObject(j).getString("type") + "</b></font>" + parts.getJSONObject(j).getString("name")));
                        Log.d("ASD", "написали текст");
                        counter += 1;
                        Log.d("ASD", counter + "");
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

        }

 
    }
}