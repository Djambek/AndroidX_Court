package com.example.courts_test_androidx;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;

public class Court_case extends AppCompatActivity {
    JSONObject jsonObject;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_court_case);

        Bundle arguments = getIntent().getExtras();
        String link = "http://ctf.djambek.com:8080/details?link="+arguments.getString("link");
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
        TextView tv5= findViewById(R.id.textView19);
        TextView tv6 = findViewById(R.id.textView20);
        TextView tv7 = findViewById(R.id.textView21);
        TextView tv8 = findViewById(R.id.textView22);
        TextView tv9 = findViewById(R.id.textView23);
        TextView tv10 = findViewById(R.id.textView24);
        TextView tv11 = findViewById(R.id.textView25);
        TextView tv12 = findViewById(R.id.textView26);
        TextView tv13 = findViewById(R.id.textView27);


        TextView[] list_tv = new TextView[]{tv1, tv2, tv3, tv4, tv5, tv6, tv7, tv8, tv9, tv10, tv11, tv12, tv13};
        String[] args = new String[]{"id", "number","number_input_document" ,"participants",
                "register_date", "date_hearing_first_instance",
                "date_of_appellate_instance", "judge", "category",
                "status", "article", "resons_solve", "date_of_decision"};
        String[] names = new String[]{"Уникальный индификатор дела", "Номер дела", "Номер входящего документа",
                "","Дата регистрации", "Дата рассмотрения дела в первой инстанции",
                "Дата поступления дела в апелляционную инстанцию", "Судья","Категория дела", "Статус",
                "Статья", "Основание решения суда","Дата вступления решения в силу"};
        int counter=0;
        Log.d("JSON", String.valueOf(names.length));
        Log.d("JSON", String.valueOf(list_tv.length));
        for (int i = 0; i < args.length; i++) {
            if (!args[i].equals("participants")){
                Log.d("JSON", args[i]);
                try {
                    if (!jsonObject.getString(args[i]).equals("")){

                        list_tv[counter].setText(Html.fromHtml( "<font><b>" + names[i]+": " +"</b></font>" + jsonObject.getString(args[i])));

                        counter+=1;
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

        }




        /*


        try {

            if (!jsonObject.getString("id").equals("")){
                Log.d("JSON", jsonObject.getString("id"));
                id.setText("Уникальный индификатор дела:"+jsonObject.getString("id"));
            }else{
                id.getLayoutParams().height = 1;
            }
            JSONArray parts = jsonObject.getJSONArray("participants");
            if (parts.length() > 0){
                one_side.setText(parts.getJSONObject(0).getString("type")+parts.getJSONObject(0).getString("name"));
                if(parts.length()  == 2){
                    another_side.setText(parts.getJSONObject(1).getString("type")+parts.getJSONObject(1).getString("name"));
                }else{
                    another_side.getLayoutParams().height=1;
                }
            }
            if (!jsonObject.getString("number").equals("")){
                number.setText("Номер дела: "+jsonObject.getString("number"));
            }else{
                number.getLayoutParams().height = 1;
            }
            if (!jsonObject.getString("register_date").equals("")){
                date_registration.setText("Дата регистрации: "+jsonObject.getString("register_date"));
            }else{
                date_registration.getLayoutParams().height=1;
            }
            if(!jsonObject.getString("date_hearing_first_instance").equals("")){
                date_hearing_first_instance.setText("Дата рассмотрения дела в первой инстанции: "+ jsonObject.getString("date_hearing_first_instance"));
            }else{
                date_hearing_first_instance.getLayoutParams().height = 1;
            }
            if (!jsonObject.getString("date_of_appellate_instance").equals("")){
                date_of_appellate_instance.setText("Дата поступления дела в апелляционную инстанцию :"+ jsonObject.getString("date_of_appellate_instance"));
            }else{
                date_of_appellate_instance.getLayoutParams().height = 1;
            }


        } catch (JSONException e) {
            e.printStackTrace();
        }
        */
    }
}