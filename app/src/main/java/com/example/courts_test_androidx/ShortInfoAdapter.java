package com.example.courts_test_androidx;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ShortInfoAdapter extends BaseAdapter {
    JSONArray cases;
    Context context;
    ShortInfoAdapter(Context context, JSONArray cases){
        this.context = context;
        this.cases = cases;
    }

    @Override
    public int getCount() {
        return cases.length();
    }

    @Override
    public Object getItem(int i) {
        try {
            return cases.getJSONObject(i);
        } catch (JSONException e) {
            e.printStackTrace();
            return 0;
        }
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View my_view = LayoutInflater.from(context).inflate(R.layout.shortinfo_adapterview, null);
        TextView textView_number = my_view.findViewById(R.id.textView4);
        TextView textView_plaintiff = my_view.findViewById(R.id.textView10);
        TextView textView_status = my_view.findViewById(R.id.textView11);
        TextView textView_defendant = my_view.findViewById(R.id.textView12);
        String type_first_slide = "";
        String name_firts_slide = "";
        String type_second_slide = "";
        String name_second_slide = "";
        String number = "";
        String status = "";
        try {
            number = cases.getJSONObject(i).getString("number");
            status = cases.getJSONObject(i).getString("status");

            JSONArray part = cases.getJSONObject(i).getJSONArray("participants");
            if (part.length() > 0){
                type_first_slide = part.getJSONObject(0).getString("type");
                name_firts_slide = part.getJSONObject(0).getString("name");
                if (part.length() == 2) {
                    type_second_slide = part.getJSONObject(1).getString("type");
                    name_second_slide = part.getJSONObject(1).getString("name");
                }
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        textView_number.setText(number);
        textView_plaintiff.setText(type_first_slide+name_firts_slide);
        textView_defendant.setText(type_second_slide+name_second_slide);
        textView_status.setText(status);

        return my_view;
    }
}
