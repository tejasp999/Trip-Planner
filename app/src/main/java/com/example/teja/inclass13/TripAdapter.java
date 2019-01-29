package com.example.teja.inclass13;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by teja on 12/4/17.
 */

public class TripAdapter extends RecyclerView.Adapter<TripAdapter.ViewHolder>{
    public static ArrayList<Trips> tripsArrayList;
    static IDataAdapter deleteInterface;
    public static ArrayList<Trips> tripsArrayListUpdate;
    public static Context context;
    public TripAdapter(ArrayList<Trips> recipes, Context context, IDataAdapter deleteInterface) {
        this.tripsArrayList = recipes;
        this.context = context;
        this.deleteInterface = deleteInterface;
    }
    @Override
    public TripAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.trip_details, parent, false);
        TripAdapter.ViewHolder viewHolder = new TripAdapter.ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(TripAdapter.ViewHolder holder, int position) {
        Trips trip = tripsArrayList.get(position);
        holder.trips = trip;
        holder.tripname.setText(trip.getName());
        holder.cost.setText(trip.getCost().toString());

    }
    @Override
    public int getItemCount() {
        return tripsArrayList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tripname, cost;
        Trips trips;

        public ViewHolder(final View itemView) {
            super(itemView);

            tripname = (TextView) itemView.findViewById(R.id.tripId);
            cost = (TextView) itemView.findViewById(R.id.costDetails);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });

            itemView.findViewById(R.id.viewMap).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(context, MapsActivity.class);
                    i.putExtra("place",tripname.getText().toString());
                    context.startActivity(i);
                }
            });


            itemView.findViewById((R.id.deleteTrip)).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    for (Trips favTrack:tripsArrayList) {
                        if (favTrack.getName().equals(tripname.getText().toString())) {
                            tripsArrayList.remove(favTrack);
                            break;
                        }
                    }
                    storeSharedPreferences(tripsArrayList);
                    Log.d("The values are","pl"+getSharedValues());
                    if (tripsArrayList.size()>0) {
                        deleteInterface.deleteData(tripsArrayList);
                    }else {
                        deleteInterface.reloadData();
                    }
                }
            });
        }
    }
    public interface IDataAdapter{
        void deleteData(ArrayList<Trips> arrayList);
        void reloadData();
    }
    public static ArrayList<Trips> getSharedValues(){
        SharedPreferences preferences = context.getSharedPreferences("Hello",Context.MODE_PRIVATE);
        Gson gson = new Gson();
        String json = preferences.getString("favorite", null);
        Type type = new TypeToken<ArrayList<Trips>>() {}.getType();
        ArrayList<Trips> trips = gson.fromJson(json, type);
        return trips;
    }
    public static void storeSharedPreferences(ArrayList<Trips> trackDatas){
        SharedPreferences preferences = context.getSharedPreferences("Hello",Context.MODE_PRIVATE);
        Gson gsonNew = new Gson();
        SharedPreferences.Editor prefEditor = preferences.edit();
        String json2 = gsonNew.toJson(trackDatas);
        prefEditor.putString("favorite",json2);
        prefEditor.commit();

    }
}
