package com.example.shrung.drawwithopengl;

import android.opengl.GLES20;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import static android.opengl.GLES20.GL_FLOAT;
import static android.opengl.GLES20.GL_FRAGMENT_SHADER;
import static android.opengl.GLES20.GL_LINES;
import static android.opengl.GLES20.GL_POINTS;
import static android.opengl.GLES20.GL_VERTEX_SHADER;
import static android.opengl.GLES20.glAttachShader;
import static android.opengl.GLES20.glBindBuffer;
import static android.opengl.GLES20.glCreateProgram;
import static android.opengl.GLES20.glDisableVertexAttribArray;
import static android.opengl.GLES20.glDrawArrays;
import static android.opengl.GLES20.glEnableVertexAttribArray;
import static android.opengl.GLES20.glGetAttribLocation;
import static android.opengl.GLES20.glGetUniformLocation;
import static android.opengl.GLES20.glLineWidth;
import static android.opengl.GLES20.glLinkProgram;
import static android.opengl.GLES20.glUseProgram;
import static android.opengl.GLES20.glVertexAttribPointer;

public class Point {

    private int mProgram;
    public float mX;
    public float mY;
    private int mPositionHandle = -1;
    public FloatBuffer vertexBuffer;
    private int mBufferSize = 0;


    private static final String ATTRIBUTE_POSITION = "aPosition";
    private static final String UNIFORM_COLOR_HANDLE = "uColor";

    private final String vertexShaderCode =
            "attribute vec4 aPosition;" +
                    "void main()" +
                    "{" +
                    "gl_Position = aPosition;" +
                    "gl_PointSize = 10.0;" +
                    "}";

    private final String fragmentShaderCode =
            "precision mediump float;" +
                    "uniform vec4 uColor;" +
                    "void main() {" +
                    "gl_FragColor = uColor;" +
                    "}";


    float color[] = {0.63671875f, 0.76953125f, 0.22265625f, 1.0f};

    public float squareCoords[];
    private int mColorHandle = -1;
    private int mCubeBufferIdx;

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

    }

    public Point(float x, float y) {
        mX = x;
        mY = y;
    }


    public void draw() {

        glUseProgram(mProgram);

        mPositionHandle = glGetAttribLocation(mProgram, ATTRIBUTE_POSITION);



        // Pass in the position information
        glBindBuffer(GLES20.GL_ARRAY_BUFFER, mCubeBufferIdx);
        glEnableVertexAttribArray(mPositionHandle);
        glVertexAttribPointer(mPositionHandle, 2, GLES20.GL_FLOAT, false, 0, 0);

//        glEnableVertexAttribArray(mPositionHandle);

        mColorHandle = glGetUniformLocation(mProgram, UNIFORM_COLOR_HANDLE);

        GLES20.glUniform4fv(mColorHandle, 1, color, 0);

        glLineWidth(5);

        if (mBufferSize != 0)
            glDrawArrays(GL_POINTS, 0, mBufferSize);

        glDisableVertexAttribArray(mPositionHandle);

    }


    public void generateBuffers(float[] positions) {
        mBufferSize = positions.length;

        FloatBuffer cubeBuffer = getBuffer(positions);

        // Second, copy these buffers into OpenGL's memory. After, we don't need to keep the client-side buffers around.
        final int buffers[] = new int[1];
        GLES20.glGenBuffers(1, buffers, 0);

        glBindBuffer(GLES20.GL_ARRAY_BUFFER, buffers[0]);
        GLES20.glBufferData(GLES20.GL_ARRAY_BUFFER, cubeBuffer.capacity() * 4,
                cubeBuffer, GLES20.GL_DYNAMIC_DRAW);

        glBindBuffer(GLES20.GL_ARRAY_BUFFER, 0);

        mCubeBufferIdx = buffers[0];

        cubeBuffer.limit(0);
        cubeBuffer = null;
    }

    private FloatBuffer getBuffer(float[] positions) {

        final FloatBuffer positionBuffer;

        positionBuffer = ByteBuffer.allocateDirect(positions.length * 4)
                .order(ByteOrder.nativeOrder()).asFloatBuffer();
        positionBuffer.put(positions).position(0);

        return positionBuffer;
    }


}
