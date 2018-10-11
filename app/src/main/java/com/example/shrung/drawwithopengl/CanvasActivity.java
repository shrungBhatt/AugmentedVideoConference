package com.example.shrung.drawwithopengl;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.hardware.Camera;
import android.os.Bundle;
import android.os.Environment;
import android.renderscript.Allocation;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;


public class CanvasActivity extends AppCompatActivity {


    private static final String TAG = "CanvasActivity";
    private CanvasView mCanvasView;
    private FrameLayout mCameraView;
    private Button mCropButton;
    private Button mResetButton;
    private Camera mCamera;
    private CameraTextureActivity mCameraTextureActivity;
    private ImageView mImageView;


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

        try {
            mCamera = Camera.open();
        }catch (Exception e){
            e.printStackTrace();
        }

        mCameraView = findViewById(R.id.camera_view);
        mCameraTextureActivity = new CameraTextureActivity(this,mCamera);

        mCameraView.addView(mCameraTextureActivity);




        mCropButton = findViewById(R.id.crop_button);
        mCropButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cropImage();

            }
        });

        mResetButton = findViewById(R.id.reset_button);
        mResetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mCanvasView.invalidate();


            }
        });

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


    public void cropImage() {

        Bitmap bitmap = generateBitmapFromImageData(mCameraTextureActivity.getImageData());


        if(bitmap != null) {
            int croppedWidth = (int) mCanvasView.getCroppedWidth();
            int croppedHeight = (int) mCanvasView.getCroppedHeight();

            int imageWidth = bitmap.getWidth();
            int imageHeight = bitmap.getHeight();


            int width = imageWidth <= croppedWidth ? imageWidth : croppedWidth;
            int height = imageHeight <= croppedHeight ? imageHeight : croppedHeight;

            int pointX = (int) mCanvasView.getIntersectionPoint().mX;
            int pointY = (int) mCanvasView.getIntersectionPoint().mY;

            try {
                Bitmap result = Bitmap.createBitmap(bitmap, pointX, pointY, width, height, null, true);
                mImageView.setImageBitmap(result);
                saveImage(result);
                Toast.makeText(getApplicationContext(),"Success",Toast.LENGTH_SHORT).show();
//            mImageView.setImageBitmap(result);
            } catch (Exception e) {
                Toast.makeText(getApplicationContext(), "Failed!", Toast.LENGTH_SHORT).show();
                Log.e(TAG,e.getMessage());
            }
            mCanvasView.clearDrawnPoints();
        }else{
            Toast.makeText(getApplicationContext(),"Bitmap is null !",Toast.LENGTH_SHORT).show();
        }


    }

    private Bitmap generateBitmapFromImageData(ImageData imageData){
        Bitmap bitmap;

        bitmap = Bitmap.createBitmap(imageData.getWidth(),
                imageData.getHeight(),
                Bitmap.Config.ARGB_8888);

        Allocation bmData = Utils.renderScriptNV21ToRGBA888(
                this,
                imageData.getWidth(),
                imageData.getHeight(),
                imageData.getImageData());

        bmData.copyTo(bitmap);

        return Utils.rotateBitmap(bitmap,90);

    }

    private void saveImage(Bitmap bitmap) {

        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 40, bytes);

        try {
            File file = new File(Environment.getExternalStorageDirectory()
                    + File.separator + "test.jpg");
            file.createNewFile();
            FileOutputStream fo = new FileOutputStream(file);
            fo.write(bytes.toByteArray());

            fo.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
