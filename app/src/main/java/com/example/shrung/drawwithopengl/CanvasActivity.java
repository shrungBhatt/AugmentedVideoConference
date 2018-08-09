package com.example.shrung.drawwithopengl;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

import java.util.Arrays;
import java.util.Collections;

public class CanvasActivity extends AppCompatActivity {


    private ImageView mImageView;
    private CanvasView mCanvasView;


    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_canvas);


        mImageView = findViewById(R.id.image_view);
        mCanvasView = findViewById(R.id.canvas_view);

        initCanvas();

        Float[] floats = new Float[10];



        /*mCanvasView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event != null) {
                    // Convert touch coordinates into normalized device
                    // coordinates, keeping in mind that Android's Y
                    // coordinates are inverted.
                    final float normalizedX =
                            (event.getX() / (float) v.getWidth()) * 2 - 1;
                    final float normalizedY =
                            -((event.getY() / (float) v.getHeight()) * 2 - 1);


                    if (event.getAction() == MotionEvent.ACTION_DOWN) {

                    } else if (event.getAction() == MotionEvent.ACTION_MOVE) {

                    }

                    return true;
                } else {
                    return false;
                }
            }
        });*/

    }


    private void initCanvas() {
        if (mCanvasView != null) {
            mCanvasView.setMode(CanvasView.Mode.DRAW);
            mCanvasView.setDrawer(CanvasView.Drawer.PEN);
            mCanvasView.setBaseColor(Color.TRANSPARENT);
            mCanvasView.setPaintStrokeColor(Color.GREEN);
            mCanvasView.setPaintStrokeWidth(5f);
        }
    }


    public void cropImage(View view) {

        BitmapDrawable drawable = (BitmapDrawable) mImageView.getDrawable();
        Bitmap bitmap = drawable.getBitmap();

        Bitmap result =
                Bitmap.createBitmap(bitmap, (int) mCanvasView.getIntersectionPoint().mX,
                        (int) mCanvasView.getIntersectionPoint().mY, 500, 800, null,
                        true);

        mImageView.setImageBitmap(result);
    }
}
