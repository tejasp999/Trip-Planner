package com.example.teja.inclass13;

import java.util.List;

/**
 * Created by teja on 12/22/17.
 */

public interface DirectionFinderListener {
    void onDirectionFinderStart();
    void onDirectionFinderSuccess(List<Route> route);
}
