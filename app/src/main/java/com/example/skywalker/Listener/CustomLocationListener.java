package com.example.skywalker.Listener;

import android.graphics.Color;
import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;
import android.util.Log;

import com.example.skywalker.rounded_image.CircularImageView;

/**
 * Created by skywalker on 2/7/15.
 */
public class CustomLocationListener implements LocationListener{

    private CircularImageView chatHead;

    public CircularImageView getChatHead() {
        return chatHead;
    }

    public void setChatHead(CircularImageView chatHead) {
        this.chatHead = chatHead;
    }

    @Override
    public void onLocationChanged(Location loc)
    {
        loc.getLatitude();
        loc.getLongitude();
        Log.d("Latitude", String.valueOf(loc.getLatitude()));
        chatHead.setBackgroundColor(Color.GREEN);
//        String Text = “My current location is: “ +
//        “Latitud = “ + loc.getLatitude() +
//        “Longitud = “ + loc.getLongitude();
//        Toast.makeText( getApplicationContext(),
//
//                Text,
//
//                Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override

    public void onProviderDisabled(String provider)

    {

//        Toast.makeText( getApplicationContext(),
//
//                “Gps Disabled”,
//
//                Toast.LENGTH_SHORT ).show();

    }


    @Override

    public void onProviderEnabled(String provider)

    {

//        Toast.makeText( getApplicationContext(),
//
//                “Gps Enabled”,
//
//                Toast.LENGTH_SHORT).show();

    }

}
