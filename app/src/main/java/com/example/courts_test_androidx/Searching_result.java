package com.example.courts_test_androidx;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;

import de.javakaffee.kryoserializers.ArraysAsListSerializer;

public class Searching_result extends AppCompatActivity {
    JSONArray jsonObject;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_searching_result);

        if (android.os.Build.VERSION.SDK_INT > 9)
        {
            StrictMode.ThreadPolicy policy = new
                    StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }



        Bundle arguments = getIntent().getExtras();
        String[] params = arguments.getStringArray("params").clone();
        Integer page = arguments.getInt("page");
        Log.d("Masive", Arrays.toString(params));




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
        final Integer[] pages_all = new Integer[1];
        Thread thread = new Thread(){
            @Override
            public void run() {
                try {
                    Log.d("ANSWER", "начинаем поиск");
                    //pages_all[0] = search.get_count_of_page();
                    String url = "http://ctf.djambek.com:8080/search_case?link="+search.get_link();
                    Log.d("URL", url);
                    Document res = Jsoup.connect(url).ignoreContentType(true).get();
                    //Log.d("ANSWER", res.text());
                    //Log.d("ANSWER", "!@3");
                    //Log.d("ANSWER", String.valueOf(res));
                    //Log.d("ANSWER", "Зашли сюда");
                    jsonObject = new JSONObject(res.text()).getJSONArray("cases");
                    //result[0] = "";
                    pages_all[0] = new JSONObject(res.text()).getInt("pages");
                    //Log.d("RESULT", String.valueOf(search.search(1)));

                } catch (IOException | JSONException e) {
                    e.printStackTrace();
                    Log.e("ERROR", e.toString());
                }
            }
        };
        thread.start();
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        TextView textView = findViewById(R.id.textView13);
        textView.setText(page+" из "+ pages_all[0]);

        Button per = findViewById(R.id.button2);
        if (page == 1){
            per.setVisibility(View.GONE);
        }
        per.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Toast.makeText(getApplicationContext(), "Загрузка...", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(Searching_result.this, Searching_result.class);
                        intent.putExtra("params", params);
                        intent.putExtra("page", page-1);
                        startActivity(intent);
                        finish();
                    }
                }
        );
        Button next = findViewById(R.id.button3);
        if (page == pages_all[0]){
            Log.d("BUTTON", "now i can be visible");
            next.setVisibility(View.GONE);
        }
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), "Загрузка...", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(Searching_result.this, Searching_result.class);
                intent.putExtra("params", params);
                intent.putExtra("page", page+1);
                startActivity(intent);
                finish();

            }
        });

        ListView list = findViewById(R.id.listview);
        //Log.d("RESULT2", String.valueOf(return_short_info(result[0])));
        list.setAdapter(new ShortInfoAdapter(getApplicationContext(), jsonObject));
        Log.d("SELECTED", "now we start to choice item");
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(getApplicationContext(), "Загрузка...", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(Searching_result.this, Court_case.class);
                try {
                    intent.putExtra("link", jsonObject.getJSONObject(i).getString("url"));
                } catch (JSONException e) {
                    Log.e("ERROR", e.toString());
                }
                startActivity(intent);
            }
        });

    }
}
