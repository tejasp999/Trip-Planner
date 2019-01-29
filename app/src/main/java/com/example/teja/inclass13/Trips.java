package com.example.teja.inclass13;

import java.io.Serializable;

/**
 * Created by teja on 12/22/17.
 */

public class Trips implements Serializable {
    String name;
    Long cost;

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    Location location;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getCost() {
        return cost;
    }

    public void setCost(Long cost) {
        this.cost = cost;
    }
}
