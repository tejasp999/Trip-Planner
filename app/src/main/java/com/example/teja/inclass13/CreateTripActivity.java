package com.example.teja.inclass13;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.Serializable;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Iterator;

public class CreateTripActivity extends AppCompatActivity implements CreateTripAdapter.ICheckedContents {
    private DatabaseReference dataref,dataRefDeals;
    public static boolean available = false;
    static private RecyclerView.LayoutManager mLayoutManager;
    RecyclerView DealsRecyclerView;
    ArrayList<Deals> dealsAddedList;
    ArrayList<Deals> dealsArrayList;
    ArrayList<Deals> checkedDealsArray;
    ArrayList<Trips> tripsArrayList;
    ArrayList<Trips> tripsAvailableList;
    static CreateTripAdapter adapter;
    public static final String TRIPS ="trips";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trip);

        findViewById(R.id.button4).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(CreateTripActivity.this,MainActivity.class);
                startActivity(i);
            }
        });

        findViewById(R.id.button3).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tripsArrayList = getSharedValues();
                if(tripsArrayList==null||tripsArrayList.size()==0){
                    tripsArrayList = new ArrayList<>();
                }
                for (Deals d:checkedDealsArray) {
                    if(d.isChecked()){
                        Trips t = new Trips();
                        t.setName(d.getPlace());
                        t.setCost(d.getCost());
                        t.setLocation(d.getLocation());
                        tripsArrayList.add(t);
                    }
                }
                saveSharedPreferences(tripsArrayList);
                Intent i = new Intent(CreateTripActivity.this,MainActivity.class);
                startActivity(i);
            }
        });

        try {
            getResults();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void getResults() throws InterruptedException {
        dealsAddedList = new ArrayList<Deals>();
        tripsAvailableList = getSharedValues();
        dealsArrayList = new ArrayList<Deals>();
        dataref = FirebaseDatabase.getInstance().getReference();
        dataRefDeals = dataref.child("Deals");

        dataRefDeals.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                dealsArrayList.clear();
                //Toast.makeText(PostsActivity.this, "Entered on data change results", Toast.LENGTH_LONG).show();
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    available = false;
                    Log.d("hi","counting");
                    Deals msg = postSnapshot.getValue(Deals.class);
                    if(tripsAvailableList != null) {
                        for (Trips t : tripsAvailableList) {
                            Log.d("the", "name " + t.getName());
                            Log.d("the", "place " + msg.getPlace());
                            if (t.getName().equals(msg.getPlace())) {
                                Log.d("got", "inside");
                                available = true;
                            }
                        }
                    }
                    Log.d("The","Boolean "+available);
                    if (available==false) {
                        Log.d("Got","False");
                        dealsArrayList.add(msg);
                    }
                }
                Log.d("the","final"+dealsArrayList.size());
                updateData(dealsArrayList);
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public void updateData(ArrayList<Deals> msg){
        if (msg.size() > 0){
            DealsRecyclerView = (RecyclerView) findViewById(R.id.tripView);
            mLayoutManager = new LinearLayoutManager(CreateTripActivity.this);
            DealsRecyclerView.setLayoutManager(new LinearLayoutManager(CreateTripActivity.this, LinearLayoutManager.VERTICAL, false));
            adapter = new CreateTripAdapter(msg , CreateTripActivity.this,  CreateTripActivity.this);
            DealsRecyclerView.setAdapter(adapter);
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    public void CheckedDeals(ArrayList<Deals> dealsArrayList) {
//        String noofpeople = ((EditText) findViewById(R.id.editText2)).getText().toString();
//        if (noofpeople != "" && noofpeople.length() > 0) {
            Long cost = new Long(0);
            checkedDealsArray = new ArrayList<>();
            checkedDealsArray = dealsArrayList;
            for (Deals d : checkedDealsArray) {
                if (d.isChecked) {
                    cost += d.getCost();
                }
            }
           // ((TextView) findViewById(R.id.textView5)).setText("" + (int) (cost * 0.2 * Integer.parseInt(noofpeople)));
//        } else {
//            Toast.makeText(CreateTripActivity.this,"enter trip name and no. of people",Toast.LENGTH_SHORT).show();
//        }
    }

    public void saveSharedPreferences(ArrayList<Trips> resultsList){
        SharedPreferences preferences2 = getApplicationContext().getSharedPreferences("Hello", MODE_PRIVATE);
        Gson gsonNew = new Gson();
        SharedPreferences.Editor prefEditor = preferences2.edit();
        String json2 = gsonNew.toJson(resultsList);
        prefEditor.putString("favorite", json2);
        prefEditor.commit();
    }

    public ArrayList<Trips> getSharedValues(){
        SharedPreferences preferences = getApplicationContext().getSharedPreferences("Hello",MODE_PRIVATE);
        Gson gson = new Gson();
        String json = preferences.getString("favorite", null);
        Type type = new TypeToken<ArrayList<Trips>>() {}.getType();
        ArrayList<Trips> trips = gson.fromJson(json, type);
        return trips;
    }
}
