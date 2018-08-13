package com.example.shrung.drawwithopengl;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.SurfaceTexture;
import android.hardware.Camera;
import android.view.TextureView;

import java.io.IOException;

public class CameraTextureActivity extends TextureView implements TextureView.SurfaceTextureListener,
        Camera.PreviewCallback {


    private Context mContext;
    private Camera mCamera;
    private Bitmap mCurrentBitmap;
    private ImageData mImageData = new ImageData();


    public CameraTextureActivity(Context context, Camera camera) {
        super(context);

        mContext = context;
        mCamera = camera;

        setSurfaceTextureListener(this);

    }

    @Override
    public void onSurfaceTextureAvailable(SurfaceTexture surfaceTexture, int i, int i1) {
        try {
            mCamera.setDisplayOrientation(90);
            Camera.Parameters parameters = mCamera.getParameters();
            parameters.setFocusMode(Camera.Parameters.FOCUS_MODE_CONTINUOUS_VIDEO);
            mCamera.setParameters(parameters);
            mCamera.setPreviewTexture(surfaceTexture);
            mCamera.startPreview();
            mCamera.setPreviewCallback(this);
        } catch (IOException ioe) {
            // Something bad happened
        }


    }

    @Override
    public void onSurfaceTextureSizeChanged(SurfaceTexture surfaceTexture, int i, int i1) {

    }

    @Override
    public boolean onSurfaceTextureDestroyed(SurfaceTexture surfaceTexture) {
        mCamera.stopPreview();
        mCamera.release();
        return true;
    }

    @Override
    public void onSurfaceTextureUpdated(SurfaceTexture surfaceTexture) {

    }

    @Override
    public void onPreviewFrame(byte[] bytes, Camera camera) {

        mImageData.setMetaData(camera.getParameters().getPreviewSize().width,
                camera.getParameters().getPreviewSize().height,
                bytes, camera, null);




    }





    public ImageData getImageData() {
        return mImageData;
    }

    public void setImageData(ImageData imageData) {
        mImageData = imageData;
    }


}
