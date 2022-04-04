package com.example.courts_test_androidx;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;

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
        //ArrayList<ArrayList<String>> short_info = new ArrayList<ArrayList<>>();
        Thread thread = new Thread(){
            @Override
            public void run() {
                try {
                    Log.d("RESULT", String.valueOf(search.search(1)));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        };
        thread.start();


    }
    static public ArrayList<ArrayList<String>> return_short_info(ArrayList<ArrayList<String>> search_result){
        ArrayList<ArrayList<String>> returned_value = new ArrayList<ArrayList<String>>();
        for (ArrayList<String> i: search_result){
            ArrayList<String> tmp = new ArrayList<>();

        }
        return null;
    }
}