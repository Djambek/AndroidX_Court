package com.example.courts_test_androidx;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class ShortInfoAdapter extends BaseAdapter {
    ArrayList<ArrayList<String>> cases;
    Context context;
    ShortInfoAdapter(Context context, ArrayList<ArrayList<String>> cases){
        this.context = context;
        this.cases = cases;
    }

    @Override
    public int getCount() {
        return cases.size();
    }

    @Override
    public Object getItem(int i) {
        return cases.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View my_view = LayoutInflater.from(context).inflate(R.layout.shortinfo_adapterview, null);
        TextView textView_number = my_view.findViewById(R.id.textView4);
        textView_number.setText("Номер дела: " + cases.get(i).get(0));
        TextView textView_plaintiff = my_view.findViewById(R.id.textView10);
        String pl = "";
        if (cases.get(i).get(1).contains("стец: ")) {
            pl = cases.get(i).get(1).split("стец: ")[1].split("Ответчик: ")[0];
        }
        textView_plaintiff.setText("Истец: "+pl);
        TextView textView_de = my_view.findViewById(R.id.textView12);
        String de = "";
        if (cases.get(i).get(1).contains("тветчик: ")) {
            de = cases.get(i).get(1).split("тветчик: ")[1];
        }
        textView_de.setText("Ответчик: "+de);
        TextView textView_defendant = my_view.findViewById(R.id.textView11);
        textView_defendant.setText(cases.get(i).get(2));
        return my_view;
    }
}
