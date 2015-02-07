package com.example.skywalker.service;

import android.app.Service;
import android.content.ClipData;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PixelFormat;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.IBinder;
import android.util.Log;
import android.view.DragEvent;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.example.skywalker.ChatHeadsClone.R;
import com.example.skywalker.Listener.CustomLocationListener;
import com.example.skywalker.rounded_image.CircularImageView;

/**
 * Created by skywalker on 2/5/15.
 */
public class ChatHeadService extends Service {

    private WindowManager windowManager;
    private RelativeLayout chatHeadLayout;

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override public void onCreate() {
        super.onCreate();

        LocationManager locManager = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
        CustomLocationListener locListener = new CustomLocationListener();
        locManager.requestLocationUpdates( LocationManager.GPS_PROVIDER, 0, 0, locListener);

        windowManager = (WindowManager) getSystemService(WINDOW_SERVICE);

        chatHeadLayout = new RelativeLayout(this);

        final CircularImageView dropView = new CircularImageView(this);
        dropView.setImageResource(R.drawable.ic_action_cancel);
        dropView.setVisibility(View.INVISIBLE);
        WindowManager.LayoutParams p = new WindowManager.LayoutParams(
                WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.TYPE_SYSTEM_ERROR,
                WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE | WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED,
                PixelFormat.TRANSLUCENT);

        //final WindowManager.LayoutParams params = new WindowManager.LayoutParams(100, 100, 2007, 8, -3);
        p.gravity = Gravity.BOTTOM | Gravity.CENTER;
        p.x = 20;
        p.y = 100;

        dropView.setOnDragListener(new View.OnDragListener(){
            @Override
            public boolean onDrag(View v, DragEvent event) {
                final int action = event.getAction();
                switch(action){
                    case DragEvent.ACTION_DROP:
                        // Gets the item containing the dragged data
                        View view = (View) event.getLocalState();
                        view.setVisibility(View.INVISIBLE);
                        View dropTarget = (View) v;
                        dropTarget.setBackgroundColor(Color.RED);
                        //windowManager.removeView(chatHeadLayout);
                        return true;
                    default:
                        break;
                }
                return false;
            }
        });

        windowManager.addView(dropView, p);

        CircularImageView chatHead = new CircularImageView(this);
        chatHead.setImageResource(R.drawable.ic_launcher);

        final WindowManager.LayoutParams params = new WindowManager.LayoutParams(
                WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.TYPE_SYSTEM_ERROR,
                WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE | WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED,
                PixelFormat.TRANSLUCENT);

        //final WindowManager.LayoutParams params = new WindowManager.LayoutParams(100, 100, 2007, 8, -3);
        params.gravity = Gravity.BOTTOM | Gravity.LEFT;
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
//                        ClipData data = ClipData.newPlainText("", "");
//                        View.DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(v);
//                        v.startDrag(data, shadowBuilder, v, 0);
                        return true;
                    case MotionEvent.ACTION_UP:
                        dropView.setVisibility(View.INVISIBLE);
                        return true;
                    case MotionEvent.ACTION_MOVE:

                        dropView.setVisibility(View.VISIBLE);
                        params.x = initialX + (int) (event.getRawX() - initialTouchX);
                        params.y = initialY - (int) (event.getRawY() - initialTouchY);
                        windowManager.updateViewLayout(v, params);
                        return true;
                }
                return false;
            }
        });

        chatHeadLayout.addView(chatHead);
        locListener.setChatHead(chatHead);
        chatHeadLayout.setBackgroundColor(Color.BLUE);
        windowManager.addView(chatHeadLayout, params);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (chatHeadLayout != null) windowManager.removeView(chatHeadLayout);
    }
}
