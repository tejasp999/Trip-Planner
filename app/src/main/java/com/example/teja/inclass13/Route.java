package com.example.teja.inclass13;

import com.google.android.gms.maps.model.LatLng;

import java.util.List;

/**
 * Created by teja on 12/22/17.
 */

public class Route {
    public String endAddress;
    public LatLng endLocation;
    public String startAddress;
    public LatLng startLocation;

    public List<LatLng> points;
}
