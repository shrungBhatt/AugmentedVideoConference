package com.example.shrung.drawwithopengl;

import android.graphics.Bitmap;
import android.hardware.Camera;

public class ImageData {


    private int mWidth;
    private int mHeight;
    private byte[] mImageData;
    private Camera mCamera;
    private Bitmap mBitmap;


    public int getWidth() {
        return mWidth;
    }

    public void setWidth(int width) {
        mWidth = width;
    }

    public int getHeight() {
        return mHeight;
    }

    public void setHeight(int height) {
        mHeight = height;
    }

    public byte[] getImageData() {
        return mImageData;
    }

    public void setImageData(byte[] imageData) {
        mImageData = imageData;
    }

    public Camera getCamera() {
        return mCamera;
    }

    public void setCamera(Camera camera) {
        mCamera = camera;
    }

    public Bitmap getBitmap() {
        return mBitmap;
    }

    public void setBitmap(Bitmap bitmap) {
        mBitmap = bitmap;
    }

    public void setMetaData(int width, int height, byte[] imageData, Camera camera, Bitmap bitmap){
        mWidth = width;
        mHeight = height;
        mImageData = imageData;
        mCamera = camera;
        mBitmap = bitmap;
    }
}
