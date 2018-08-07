package com.example.shrung.drawwithopengl;

import android.content.Context;
import android.opengl.GLES20;
import android.opengl.GLSurfaceView;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import static android.opengl.GLES20.GL_COLOR_BUFFER_BIT;
import static android.opengl.GLES20.glClear;
import static android.opengl.GLES20.glClearColor;
import static android.opengl.GLES20.glViewport;

public class MyRenderer implements GLSurfaceView.Renderer {

    private Context mContext;
    private Point mPoint;


    public MyRenderer(Context context){
        mContext = context;
    }

    @Override
    public void onSurfaceCreated(GL10 gl10, EGLConfig eglConfig) {
        glClearColor(0.0f,0.0f,0.0f,0.0f);

        mPoint = new Point();

    }

    @Override
    public void onSurfaceChanged(GL10 gl10, int width, int height) {

        glViewport(0,0,width,height);


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

    public void addDataToBuffer(Point point){
        mPoint.vertexBuffer.put(point.mX);
        mPoint.vertexBuffer.put(point.mY);
        mPoint.vertexBuffer.position(0);
    }

    private void setX(float normalizedX){
        mPoint.mX = normalizedX;
    }

    private void setY(float normalizedY){
        mPoint.mY = normalizedY;
    }
}
