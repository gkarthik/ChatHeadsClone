package com.example.skywalker.service;

import android.app.ActionBar;
import android.app.Service;
import android.content.ClipData;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PixelFormat;
import android.graphics.Point;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.IBinder;
import android.util.Log;
import android.view.Display;
import android.view.DragEvent;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.skywalker.ChatHeadsClone.R;
import com.example.skywalker.Listener.CustomLocationListener;
import com.example.skywalker.rounded_image.CircularImageView;

/**
 * Created by skywalker on 2/5/15.
 */
public class ChatHeadService extends Service {

    private WindowManager windowManager;
    private RelativeLayout chatHeadLayout;
    final double fraction = 0.3;

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override public void onCreate() {
        super.onCreate();
        windowManager = (WindowManager) getSystemService(WINDOW_SERVICE);
        Display display = windowManager.getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        final int screenWidth = size.x;
        final int screenHeight = size.y;

        chatHeadLayout = new RelativeLayout(this);
        final CircularImageView dropView = new CircularImageView(this);
        dropView.setImageResource(R.drawable.ic_action_cancel);
        WindowManager.LayoutParams p = new WindowManager.LayoutParams(
                (int) (screenWidth * fraction),
                (int) (screenHeight * fraction),
                WindowManager.LayoutParams.TYPE_SYSTEM_ERROR,
                WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE | WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED,
                PixelFormat.TRANSLUCENT);

        p.gravity = Gravity.CENTER | Gravity.BOTTOM;

        windowManager.addView(dropView, p);

        CircularImageView chatHead = new CircularImageView(this);
        p.y = (int) (screenHeight * fraction);

        chatHead.setImageResource(R.drawable.ic_launcher);

        final WindowManager.LayoutParams params = new WindowManager.LayoutParams(
                WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.TYPE_SYSTEM_ERROR,
                WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE | WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED,
                PixelFormat.TRANSLUCENT);

        params.gravity = Gravity.BOTTOM | Gravity.CENTER;
        params.x = 20;
        params.y = 100;

        chatHeadLayout.setOnTouchListener(new View.OnTouchListener() {
            private int initialX;
            private int initialY;
            private float initialTouchX;
            private float initialTouchY;

            @Override public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        initialX = params.x;
                        initialY = params.y;
                        initialTouchX = event.getRawX();
                        initialTouchY = event.getRawY();
                        return true;
                    case MotionEvent.ACTION_UP:
                        dropView.setVisibility(View.INVISIBLE);
                        if(checkDrop(event.getRawX(), event.getRawY(), screenHeight, screenWidth)){
                            v.setBackgroundColor(Color.GREEN);
                        } else {
                            v.setBackgroundColor(Color.TRANSPARENT);
                        }
                        return true;
                    case MotionEvent.ACTION_MOVE:
                        dropView.setVisibility(View.VISIBLE);
                        params.x = initialX + (int) (event.getRawX() - initialTouchX);
                        params.y = initialY - (int) (event.getRawY() - initialTouchY);
                        windowManager.updateViewLayout(v, params);
                        if(checkDrop(event.getRawX(), event.getRawY(), screenHeight, screenWidth)){
                            v.setBackgroundColor(Color.GREEN);
                        } else {
                            v.setBackgroundColor(Color.TRANSPARENT);
                        }
                        return true;
                }
                return false;
            }
        });

        chatHeadLayout.addView(chatHead);
        windowManager.addView(chatHeadLayout, params);
        LocationManager locManager = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
        CustomLocationListener locListener = new CustomLocationListener();
        locManager.requestLocationUpdates( LocationManager.GPS_PROVIDER, 0, 0, locListener);
        locListener.setAppContext(getApplicationContext());
        locListener.setChatHead(chatHead);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (chatHeadLayout != null) windowManager.removeView(chatHeadLayout);
    }

    public Boolean checkDrop(float x, float y, int screenHeight, int screenWidth){
        Boolean flag = false;
        if(x > screenWidth * ((1-fraction)/2) && x < screenWidth - (screenWidth * ((1-fraction)/2)) && y > screenHeight * ((1-fraction)) ){
            flag = true;
        }
        return flag;
    }
}
