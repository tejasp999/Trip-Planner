package com.example.teja.inclass13;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by teja on 12/4/17.
 */

public class CreateTripAdapter extends RecyclerView.Adapter<CreateTripAdapter.ViewHolder> {
    static ArrayList<Deals> dealsArrayList;
    static ICheckedContents checked;
    Context context;
    public CreateTripAdapter(ArrayList<Deals> recipes, Context context, ICheckedContents checked) {
        this.dealsArrayList = recipes;
        this.context = context;
        this.checked = checked;
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.get_trip_details, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Deals deal = dealsArrayList.get(position);
        holder.deals = deal;

        holder.Placename.setText(deal.getPlace());
        //holder.cost.setText(deal.getCost().toString());
        holder.duration.setText(deal.getDuration());

        android.location.Location loc = new android.location.Location("new location");
        loc.setLatitude(deal.getLocation().getLat());
        loc.setLongitude(deal.getLocation().getLon());

        android.location.Location cltloc = new android.location.Location("clt");
        cltloc.setLatitude(35.2270869);
        cltloc.setLongitude(-80.8431267);

        float dis = cltloc.distanceTo(loc);
        holder.cost.setText(deal.getCost().toString() + " $");
        holder.distance.setText(dis+" miles away");
    }

    @Override
    public int getItemCount() {
        return dealsArrayList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView Placename, cost, duration, distance;
        CheckBox checkBox;
        Deals deals;

        public ViewHolder(final View itemView) {
            super(itemView);

            checkBox = (CheckBox) itemView.findViewById(R.id.checkBox);
            Placename = (TextView) itemView.findViewById(R.id.placeData);
            cost = (TextView) itemView.findViewById(R.id.costData1);
            duration = (TextView) itemView.findViewById(R.id.duration);
            distance = (TextView) itemView.findViewById(R.id.distance);

            //duration.setClickable(true);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });

            checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if(isChecked){
                        int i = getAdapterPosition();
                        dealsArrayList.get(i).setChecked(true);
                        checked.CheckedDeals(dealsArrayList);
                    }
                    else
                    {
                        int i = getAdapterPosition();
                        dealsArrayList.get(i).setChecked(false);
                        checked.CheckedDeals(dealsArrayList);
                    }
                }
            });
        }
    }

    interface ICheckedContents
    {
        void CheckedDeals(ArrayList<Deals> dealsArrayList);
    }
}
