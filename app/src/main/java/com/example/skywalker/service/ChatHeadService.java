package com.example.skywalker.service;

import android.app.Service;
import android.content.ClipData;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PixelFormat;
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
        chatHeadLayout.setBackgroundColor(Color.BLUE);
        windowManager.addView(chatHeadLayout, params);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (chatHeadLayout != null) windowManager.removeView(chatHeadLayout);
    }
}
