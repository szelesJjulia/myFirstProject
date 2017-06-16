package com.ebookfrenzy.szelesjulide.practiceprojectgooglemaplist;

import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;
import android.util.Log;

/**
 * Created by szeles.julide on 2017/06/15.
 */

public class MyLocListener implements LocationListener {
    @Override
    public void onLocationChanged(Location location) {
        if (location != null){
            Log.e("Latitude: ", "" + location.getLatitude());
            Log.e("Longtitude: ", "" + location.getLongitude());
        }
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }
}
