package com.example.shrung.drawwithopengl;

import android.annotation.SuppressLint;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private MyGLSurfaceView mMyGLSurfaceView;
    private MyRenderer mMyRenderer;
    private ArrayList<Point> mPoints = new ArrayList<>();
    private Point mPoint = new Point();

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mMyGLSurfaceView = new MyGLSurfaceView(this);

        mMyRenderer = mMyGLSurfaceView.mMyRenderer;

        mMyGLSurfaceView.setOnTouchListener(new View.OnTouchListener() {
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
                        mMyGLSurfaceView.queueEvent(new Runnable() {
                            @Override
                            public void run() {

                            }
                        });
                    } else if (event.getAction() == MotionEvent.ACTION_MOVE) {
                        mMyGLSurfaceView.queueEvent(new Runnable() {
                            @Override
                            public void run() {
                                mPoint.mX = normalizedX;
                                mPoint.mY = normalizedY;
                                mPoints.add(mPoint);
                                if(mMyRenderer != null){
                                    mMyRenderer.addDataToBuffer(mPoint);
                                }

                                Log.e("Points","x: "+mPoint.mX+"\t"+"y: "+mPoint.mY+"\n");

                            }
                        });
                    }

                    return true;
                } else {
                    return false;
                }
            }
        });

        setContentView(mMyGLSurfaceView);




    }
}
