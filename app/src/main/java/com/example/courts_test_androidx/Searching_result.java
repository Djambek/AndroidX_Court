package com.example.courts_test_androidx;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.widget.ListView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import de.javakaffee.kryoserializers.ArraysAsListSerializer;

public class Searching_result extends AppCompatActivity {

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
        final ArrayList<ArrayList<String>>[] result = new ArrayList[]{new ArrayList<ArrayList<String>>()};
        Thread thread = new Thread(){
            @Override
            public void run() {
                try {
                    result[0] = search.search(1);
                    Log.d("RESULT", String.valueOf(search.search(1)));

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        };
        thread.start();
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        ListView list = findViewById(R.id.listview);
        Log.d("RESULT2", String.valueOf(return_short_info(result[0])));
        list.setAdapter(new ShortInfoAdapter(getApplicationContext(), return_short_info(result[0])));

    }
    static public ArrayList<ArrayList<String>> return_short_info(ArrayList<ArrayList<String>> search_result){
        ArrayList<ArrayList<String>> returned_value = new ArrayList<ArrayList<String>>();
        for (ArrayList<String> i: search_result){
            ArrayList<String> tmp = new ArrayList<>();
            tmp.add(i.get(0));
            tmp.add(i.get(1));
            tmp.add(i.get(2));
            returned_value.add(tmp);
        }
        return returned_value;
    }
}