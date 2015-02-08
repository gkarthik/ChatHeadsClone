package com.example.skywalker.Listener;

import android.content.Context;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.example.skywalker.InstagramService;
import com.example.skywalker.rounded_image.CircularImageView;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by skywalker on 2/7/15.
 */
public class CustomLocationListener implements LocationListener{

    private CircularImageView chatHead;
    private String client_id = "9b95874bc86e4ce4b0c93af6502c9914";

    public Context getAppContext() {
        return appContext;
    }

    public void setAppContext(Context appContext) {
        this.appContext = appContext;
    }

    private Context appContext;



    public CircularImageView getChatHead() {
        return chatHead;
    }

    public void setChatHead(CircularImageView chatHead) {
        this.chatHead = chatHead;
    }

    @Override
    public void onLocationChanged(Location loc)
    {
        double lat = loc.getLatitude();
        double lgt = loc.getLongitude();
        String url = "";
        url = "https://api.instagram.com/v1/media/search?lat="+String.valueOf(lat)+"&lng="+String.valueOf(lgt)+"&distance=5000&client_id="+client_id;
        Log.d("URL", url);
        chatHead.setBackgroundColor(Color.GREEN);
        InstagramService iService = new InstagramService();
        iService.setChatHead(chatHead);
        iService.setAppContext(appContext);
        iService.execute(url);

    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
    }

    @Override
    public void onProviderDisabled(String provider)
    {
    }


    @Override
    public void onProviderEnabled(String provider)
    {

    }

}
