package com.example.shrung.drawwithopengl;

import android.content.Context;
import android.opengl.GLES20;
import android.opengl.GLSurfaceView;

import java.lang.reflect.Array;
import java.util.ArrayList;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import static android.opengl.GLES20.GL_COLOR_BUFFER_BIT;
import static android.opengl.GLES20.glClear;
import static android.opengl.GLES20.glClearColor;
import static android.opengl.GLES20.glViewport;

public class MyRenderer implements GLSurfaceView.Renderer {

    private Context mContext;
    private Point mPoint;
    private ArrayList<Float> mPointerPositions = new ArrayList<>();


    public MyRenderer(Context context) {
        mContext = context;
    }

    @Override
    public void onSurfaceCreated(GL10 gl10, EGLConfig eglConfig) {
        glClearColor(0.0f, 0.0f, 0.0f, 0.0f);

        mPoint = new Point();

    }

    @Override
    public void onSurfaceChanged(GL10 gl10, int width, int height) {

        glViewport(0, 0, width, height);


    }

    @Override
    public void onDrawFrame(GL10 gl10) {
        glClear(GL_COLOR_BUFFER_BIT);


        mPoint.draw();

    }

    public static int loadShader(int type, String shaderCode) {

        // create a vertex shader type (GLES20.GL_VERTEX_SHADER)
        // or a fragment shader type (GLES20.GL_FRAGMENT_SHADER)
        int shader = GLES20.glCreateShader(type);

        // add the source code to the shader and compile it
        GLES20.glShaderSource(shader, shaderCode);
        GLES20.glCompileShader(shader);

        return shader;
    }

    public void addDataToBuffer(Point point) {
        mPointerPositions.add(point.mX);
        mPointerPositions.add(point.mY);

        Float[] temp = new Float[mPointerPositions.size()];
        mPointerPositions.toArray(temp);

        float[] positions = new float[temp.length];

        for (int i = 0; i < temp.length; i++) {
            positions[i] = temp[i];
        }

        if (mPoint != null) {
            mPoint.releaseBuffers();

            mPoint.generateBuffers(positions);
        }

    }

    private void setX(float normalizedX) {
        mPoint.mX = normalizedX;
    }

    private void setY(float normalizedY) {
        mPoint.mY = normalizedY;
    }
}
