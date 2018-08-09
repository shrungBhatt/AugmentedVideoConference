package com.example.shrung.drawwithopengl;

import android.annotation.SuppressLint;
import android.graphics.Canvas;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;

public class CanvasActivity extends AppCompatActivity {


    private ImageView mImageView;
    private CanvasView mCanvasView;


    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_canvas);


        mImageView = findViewById(R.id.image_view);
        mCanvasView = findViewById(R.id.canvas_view);

        initCanvas();


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

    private void getDrawer(){


    }


}
