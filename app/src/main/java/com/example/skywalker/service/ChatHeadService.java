package com.example.skywalker.service;

import android.app.Service;
import android.content.ClipData;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PixelFormat;
import android.os.IBinder;
import android.view.DragEvent;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;

import com.example.skywalker.ChatHeadsClone.R;
import com.example.skywalker.rounded_image.CircularImageView;

/**
 * Created by skywalker on 2/5/15.
 */
public class ChatHeadService extends Service {

    private WindowManager windowManager;
    private CircularImageView chatHead;

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override public void onCreate() {
        super.onCreate();

        windowManager = (WindowManager) getSystemService(WINDOW_SERVICE);

        chatHead = new CircularImageView(this);
        chatHead.setImageResource(R.drawable.ic_launcher);

        final WindowManager.LayoutParams params = new WindowManager.LayoutParams(
                WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.TYPE_SYSTEM_OVERLAY,
                WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED,
                PixelFormat.TRANSLUCENT);

        params.gravity = Gravity.BOTTOM | Gravity.LEFT;
        params.x = 20;
        params.y = 100;

        chatHead.setOnLongClickListener(new View.OnLongClickListener(){

            @Override
            public boolean onLongClick(View v) {
               System.out.println("Long Click!");
                return true;
            }
        });

        windowManager.addView(chatHead, params);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (chatHead != null) windowManager.removeView(chatHead);
    }
}
