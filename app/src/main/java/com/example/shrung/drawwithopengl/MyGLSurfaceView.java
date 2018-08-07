package com.example.shrung.drawwithopengl;

import android.content.Context;
import android.opengl.GLSurfaceView;

public class MyGLSurfaceView extends GLSurfaceView {

    public MyRenderer mMyRenderer;

    public MyGLSurfaceView(Context context) {
        super(context);


        setEGLContextClientVersion(2);

        mMyRenderer = new MyRenderer(context);

        setRenderer(mMyRenderer);

    }



}
