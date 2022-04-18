package com.example.courts_test_androidx;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.w3c.dom.Text;

import java.io.IOException;
import java.util.ArrayList;

public class Court_case extends AppCompatActivity {
    JSONObject jsonObject;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_court_case);

        Bundle arguments = getIntent().getExtras();

        String link = "http://ctf.djambek.com:8080/details?link=" + arguments.getString("link");
        Log.d("RESULT_SEARCH_LINK", link);
        Thread thread = new Thread(){
            @Override
            public void run() {
                super.run();
                try {
                    Document res = Jsoup.connect(link).ignoreContentType(true).get();
                    jsonObject = new JSONObject(res.text());

                } catch (IOException | JSONException e) {
                    e.printStackTrace();
                }
            }
        };
        thread.start();
        try {
            thread.join();
            Log.d("RESULT_SEARCH", String.valueOf(jsonObject));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


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


        ArrayList<ArrayList<Object>> case_info = new ArrayList<>();

            for (int i = 0; i < args.length; i++) {
                try {
                    Log.d("ARGS", args[i]);
                    ArrayList<Object> tmp = new ArrayList<>();
                    if (args[i].equals("participants")) {

                        JSONArray parts = jsonObject.getJSONArray("participants");
                        Log.d("RESULT", String.valueOf(parts));
                        for (int j = 0; j < parts.length(); j++) {
                            Log.d("RESULT", "Зашли в цикл участников");
                            tmp.add(parts.getJSONObject(j).getString("type"));
                            tmp.add(parts.getJSONObject(j).getString("name"));
                            tmp.add(null);
                            case_info.add(tmp);
                            tmp = new ArrayList<>();

                        }

                    } else {
                        if (args[i].equals("number_in_last_instance")) {
                            tmp.add(names[i] + ": ");
                            tmp.add(jsonObject.getString(args[i]));
                            if (!jsonObject.getString("url_number_in_last_instance").equals("")) {
                                tmp.add(jsonObject.getString("url_number_in_last_instance"));
                            }
                            tmp.add(null); //Цвет
                        } else {
                            if (args[i].equals("number_in_next_instance")) {
                                tmp.add(names[i] + ": ");
                                tmp.add(jsonObject.getString(args[i]));
                                if (!jsonObject.getString("url_number_in_next_instance").equals("")) {
                                    tmp.add(jsonObject.getString("url_number_in_next_instance"));
                                }
                                tmp.add(null); //Цвет
                            } else {
                                tmp.add(names[i] + ": ");
                                tmp.add(jsonObject.getString(args[i]));
                                tmp.add(null); // Цвет, тут он должен быть прозрачным

                            }
                        }
                    }
                    if(tmp.size() > 2 && !tmp.get(0).equals("") && !tmp.get(1).equals("")) {
                        case_info.add(tmp);
                    }
                    tmp = new ArrayList<>();

                } catch (JSONException e) {
                    Log.e("JSON ERROR", e.getMessage());
                }
            }

        ListView listView = findViewById(R.id.list_view_court_info);
        Log.d("MASSIVE", String.valueOf(case_info));
        listView.setAdapter(new Court_info_adapter(this, case_info));

//        for (int i = 0; i < args.length; i++) {
//            if (!args[i].equals("participants")) {
//                Log.d("JSON", args[i]);
//                try {
//                    if (!jsonObject.getString(args[i]).equals("")) {
//                        Log.d("ASD", args[i].equals("number_in_next_instance") + jsonObject.getString("url_number_in_next_instance"));
//                        if (args[i].equals("number_in_next_instance") && !jsonObject.getString("url_number_in_next_instance").equals("")) {
//                            //list_tv[counter].setTextColor(Color.BLUE);
//                            //<font color='#FFFFFF'>
//                            Log.d("ASD", "я тута");
//                            Log.d("ASD", names[i]);
//                            Log.d("ASD", jsonObject.getString(args[i]));
//
//                            list_tv[counter].setOnClickListener(new View.OnClickListener() {
//                                @Override
//                                public void onClick(View view) {
//                                    Intent intent = new Intent(Court_case.this, Court_case.class);
//                                    try {
//                                        Toast.makeText(getApplicationContext(), "Загрузка...", Toast.LENGTH_SHORT).show();
//                                        intent.putExtra("link", jsonObject.getString("url_number_in_next_instance"));
//                                        startActivity(intent);
//                                        finish();
//                                    } catch (JSONException e) {
//                                        e.printStackTrace();
//                                    }
//                                }
//                            });
//                            list_tv[counter].setText(Html.fromHtml("<font><b>" + names[i] + ": " + "</b></font>" + "<font color='#0066FF'>" + jsonObject.getString(args[i]) + "</font>"));
//                            counter += 1;
//                        } else {
//                            if (args[i].equals("number_in_last_instance") && !jsonObject.getString("url_number_in_last_instance").equals("")) {
//                                //list_tv[counter].setTextColor(Color.BLUE);
//
//                                list_tv[counter].setOnClickListener(new View.OnClickListener() {
//                                    @Override
//                                    public void onClick(View view) {
//                                        Intent intent = new Intent(Court_case.this, Court_case.class);
//                                        try {
//                                            Toast.makeText(getApplicationContext(), "Загрузка...", Toast.LENGTH_SHORT).show();
//                                            intent.putExtra("link", jsonObject.getString("url_number_in_last_instance"));
//                                            startActivity(intent);
//                                            finish();
//                                        } catch (JSONException e) {
//                                            e.printStackTrace();
//                                        }
//                                    }
//                                });
//                                list_tv[counter].setText(Html.fromHtml("<font><b>" + names[i] + ": " + "</b></font>" + "<font color='#0066FF'>" + jsonObject.getString(args[i]) + "</font>"));
//                                counter += 1;
//                            } else {
//                                list_tv[counter].setText(Html.fromHtml("<font><b>" + names[i] + ": " + "</b></font>" + jsonObject.getString(args[i])));
//                                counter += 1;
//                            }
//
//                        }
//                    }
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//            } else {
//                Log.d("ASD", "наконец-то участиник");
//                try {
//                    JSONArray parts = jsonObject.getJSONArray("participants");
//                    Log.d("ASD", String.valueOf(parts));
//                    for (int j = 0; j < parts.length(); j++) {
//                        Log.d("ASD", "Зашли в цикл");
//                        Log.d("ASD", counter + "");
//                        list_tv[counter].setText(Html.fromHtml("<font><b>" + parts.getJSONObject(j).getString("type") + "</b></font>" + parts.getJSONObject(j).getString("name")));
//                        Log.d("ASD", "написали текст");
//                        counter += 1;
//                        Log.d("ASD", counter + "");
//                    }
//
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//            }
//
//        }
//
//
    }
}