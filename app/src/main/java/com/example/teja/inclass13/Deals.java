package com.example.teja.inclass13;

/**
 * Created by teja on 12/4/17.
 */

public class Deals {
    String Duration, Place;

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }

    boolean isChecked = false;
    Long Cost;
    public Location getLocation() {
        return Location;
    }

    public void setLocation(Location location) {
        this.Location = location;
    }

    Location Location;

    public Long getCost() {
        return Cost;
    }

    public void setCost(Long cost) {
        this.Cost = cost;
    }

    public String getDuration() {
        return Duration;
    }

    public void setDuration(String duration) {
        this.Duration = duration;
    }

    public String getPlace() {
        return Place;
    }

    public void setPlace(String place) {
        this.Place = place;
    }

}
