package com.example.skywalker;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.AsyncTask;
import android.util.Log;

import com.example.skywalker.ChatHeadsClone.R;
import com.example.skywalker.rounded_image.CircularImageView;

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
 * Created by skywalker on 2/8/15.
 */
public class InstagramService extends AsyncTask<String, String, Bitmap> {

    private CircularImageView chatHead;
    private Context appContext;

    public Context getAppContext() {
        return appContext;
    }

    public void setAppContext(Context appContext) {
        this.appContext = appContext;
    }

    public CircularImageView getChatHead() {
        return chatHead;
    }

    public void setChatHead(CircularImageView chatHead) {
        this.chatHead = chatHead;
    }

    @Override
    protected Bitmap doInBackground(String... params) {
        String imgUrl = getImageUrl(params[0]);
        Log.d("Image URL", imgUrl);
        Bitmap bp = BitmapFactory.decodeResource(appContext.getResources(), R.drawable.ic_launcher);
        if(imgUrl != ""){
            bp = getImage(imgUrl);
        }
        return bp;
    }

    public Bitmap getImage(String imgUrl){
        Bitmap x;
        InputStream input = null;
        try {
            HttpURLConnection connection = (HttpURLConnection) new URL(imgUrl).openConnection();
            connection.connect();
            input = connection.getInputStream();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new BitmapDrawable(appContext.getResources(), input).getBitmap();
    }

    public String getImageUrl(String urlToRead) {
        URL url;
        HttpURLConnection conn;
        BufferedReader rd;
        String line;
        String result = "";
        try {
            url = new URL(urlToRead);
            conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            while ((line = rd.readLine()) != null) {
                result += line;
            }
            rd.close();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        JSONObject obj = null;
        String imgUrl = "";
        try {
            obj = new JSONObject(result);

            JSONArray arr = obj.getJSONArray("data");
            if(arr.length()>0){
                imgUrl = arr.getJSONObject(0).getJSONObject("images").getJSONObject("thumbnail").getString("url");
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return imgUrl;
    }

    @Override
    protected void onPostExecute(Bitmap bitmap) {
        chatHead.setImageBitmap(bitmap);
    }
}
