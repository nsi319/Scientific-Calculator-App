package com.appdevlab.calculator;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.TextView;

import com.appdevlab.calculator.adapter.HistoryAdapter;

import java.util.ArrayList;
import java.util.Arrays;

import static com.appdevlab.calculator.MainActivity.SHARED_PREF;

public class History extends AppCompatActivity {
    ArrayList<String> primary,secondary;
    SharedPreferences sharedPreferences;
    TextView title;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        getSupportActionBar().setElevation(0);
        title = (TextView) findViewById(R.id.title);

        sharedPreferences = getSharedPreferences(SHARED_PREF, MODE_PRIVATE);

        primary = (ArrayList<String>) Arrays.asList(sharedPreferences.getString("primary","").split(";"));
        secondary = new ArrayList<String>(Arrays.asList(sharedPreferences.getString("secondary","").split(";")));

        primary.remove("");
        secondary.remove("");

        if(primary.size()==0) {
            title.setTextColor(Color.RED);
            title.setText("No History");
        }
        else {
            final RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerView);

            HistoryAdapter adapter = new HistoryAdapter(primary, secondary);
            recyclerView.setHasFixedSize(true);
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
            linearLayoutManager.setReverseLayout(true);
            linearLayoutManager.setStackFromEnd(true);
            recyclerView.setLayoutManager(linearLayoutManager);
            recyclerView.setAdapter(adapter);
        }
    }
}
