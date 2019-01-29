package com.example.teja.inclass13;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.google.firebase.database.DatabaseReference;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements TripAdapter.IDataAdapter {
    private DatabaseReference dataref,dataRefDeals;
    static private RecyclerView.LayoutManager mLayoutManager;
    RecyclerView TripsRecyclerView;
    ArrayList<Deals> dealsArrayList;
    static TripAdapter adapter;
    ArrayList<Trips> tripsArrayList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tripsArrayList = getSharedValues();
        updateData(tripsArrayList);
    }
    public void createTripActivity(View view){
        Intent i = new Intent(MainActivity.this, CreateTripActivity.class);
        startActivity(i);
    }
    public ArrayList<Trips> getSharedValues(){
        SharedPreferences preferences = getApplicationContext().getSharedPreferences("Hello",MODE_PRIVATE);
        Gson gson = new Gson();
        String json = preferences.getString("favorite", null);
        Type type = new TypeToken<ArrayList<Trips>>() {}.getType();
        ArrayList<Trips> trips = gson.fromJson(json, type);
        return trips;
    }

    public void updateData(ArrayList<Trips> msg){
        if (msg!=null&&msg.size() > 0){
            TripsRecyclerView = (RecyclerView) findViewById(R.id.tripsViewData);
            mLayoutManager = new LinearLayoutManager(MainActivity.this);
            TripsRecyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this, LinearLayoutManager.VERTICAL, false));
            adapter = new TripAdapter(msg, MainActivity.this, MainActivity.this);
            TripsRecyclerView.setAdapter(adapter);
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    public void deleteData(ArrayList<Trips> arrayList) {
        updateData(arrayList);
    }

    @Override
    public void reloadData() {
        TripsRecyclerView.removeAllViews();
    }
}
