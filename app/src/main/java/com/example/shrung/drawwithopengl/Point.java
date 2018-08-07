package com.example.shrung.drawwithopengl;

import android.opengl.GLES20;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import static android.opengl.GLES20.GL_FLOAT;
import static android.opengl.GLES20.GL_FRAGMENT_SHADER;
import static android.opengl.GLES20.GL_POINTS;
import static android.opengl.GLES20.GL_VERTEX_SHADER;
import static android.opengl.GLES20.glAttachShader;
import static android.opengl.GLES20.glCreateProgram;
import static android.opengl.GLES20.glDisableVertexAttribArray;
import static android.opengl.GLES20.glDrawArrays;
import static android.opengl.GLES20.glEnableVertexAttribArray;
import static android.opengl.GLES20.glGetAttribLocation;
import static android.opengl.GLES20.glGetUniformLocation;
import static android.opengl.GLES20.glLinkProgram;
import static android.opengl.GLES20.glUseProgram;
import static android.opengl.GLES20.glVertexAttribPointer;

public class Point {

    private int mProgram;
    public float mX;
    public float mY;
    private int mPositionHandle = -1;
    public FloatBuffer vertexBuffer;


    private static final String ATTRIBUTE_POSITION = "aPosition";
    private static final String UNIFORM_COLOR_HANDLE = "uColor";
    private int BUFFER_SIZE = 4;

    private final String vertexShaderCode =
            "attribute vec4 aPosition;" +
                    "void main()" +
                    "{" +
                        "gl_Position = aPosition;" +
                        "gl_PointSize = 10.0;"+
                    "}";

    private final String fragmentShaderCode =
            "precision mediump float;" +
                    "uniform vec4 uColor;"+
                    "void main() {" +
                    "gl_FragColor = uColor;" +
                    "}";


    float color[] = { 0.63671875f, 0.76953125f, 0.22265625f, 1.0f };

    static float squareCoords[] = {
            0.5f, 0.5f,
            0.5f, -0.5f,
            -0.5f, 0.5f,
            -0.5f, -0.5f
    };
    private int mColorHandle = -1;

    public Point() {

        int vertexShader = MyRenderer.loadShader(GL_VERTEX_SHADER,
                vertexShaderCode);

        int fragmentShader = MyRenderer.loadShader(GL_FRAGMENT_SHADER,
                fragmentShaderCode);

        mProgram = glCreateProgram();


        // add the vertex shader to program
        glAttachShader(mProgram, vertexShader);

        // add the fragment shader to program
        glAttachShader(mProgram, fragmentShader);

        glLinkProgram(mProgram);

        ByteBuffer bb = ByteBuffer.allocateDirect(
                // (# of coordinate values * 4 bytes per float)
                squareCoords.length * 4);
        bb.order(ByteOrder.nativeOrder());
        vertexBuffer = bb.asFloatBuffer();
        vertexBuffer.put(squareCoords);
        vertexBuffer.position(0);

    }

    public Point(float x, float y) {
        mX = x;
        mY = y;
    }

    //This method will be used to keep updating the floatBuffer.
    private void getBuffer() {


    }

    public void draw() {

        glUseProgram(mProgram);

        mPositionHandle = glGetAttribLocation(mProgram, ATTRIBUTE_POSITION);


        glVertexAttribPointer(mPositionHandle, 2,
                GL_FLOAT, false, 0, vertexBuffer);


        glEnableVertexAttribArray(mPositionHandle);

        mColorHandle = glGetUniformLocation(mProgram, UNIFORM_COLOR_HANDLE);

        GLES20.glUniform4fv(mColorHandle, 1, color, 0);

        glDrawArrays(GL_POINTS, 0, vertexBuffer.capacity()/2);

        glDisableVertexAttribArray(mPositionHandle);

    }


}
