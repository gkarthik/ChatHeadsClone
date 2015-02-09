package com.example.skywalker.rounded_image;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Path;
import android.graphics.PixelFormat;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.DragEvent;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.ImageView;

/**
 * Created by skywalker on 2/5/15.
 */
    public class CircularImageView extends ImageView {

        public CircularImageView(Context context) {
            super(context);
        }

        public CircularImageView(Context context, AttributeSet attrs) {
            super(context, attrs);
        }

        public CircularImageView(Context context, AttributeSet attrs, int defStyle) {
            super(context, attrs, defStyle);
        }

    @Override
        protected void onDraw(Canvas canvas) {
            float radius = this.getWidth()/2;
            Path clipPath = new Path();
            RectF rect = new RectF(0, 0, this.getWidth(), this.getHeight());
            clipPath.addRoundRect(rect, radius, radius, Path.Direction.CW);
            canvas.clipPath(clipPath);
            canvas.drawColor(Color.argb(80, 0, 0, 0 ));
            super.onDraw(canvas);
        }
}
