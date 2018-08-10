package com.example.shrung.drawwithopengl;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;


public class CanvasActivity extends AppCompatActivity {


    private ImageView mImageView;
    private CanvasView mCanvasView;
    private Button mCropButton;
    private Button mResetButton;


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

        Float[] floats = new Float[10];


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
                mCanvasView.clear();
                mCanvasView.invalidate();
                mImageView.setImageDrawable(getDrawable(R.drawable.wall));
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

        BitmapDrawable drawable = (BitmapDrawable) mImageView.getDrawable();
        Bitmap bitmap = drawable.getBitmap();

        int croppedWidth = (int) mCanvasView.getCroppedWidth();
        int croppedHeight = (int) mCanvasView.getCroppedHeight();

        int width = bitmap.getWidth() <= croppedWidth ? bitmap.getWidth() : croppedWidth;
        int height = bitmap.getHeight() <= croppedHeight ? bitmap.getHeight() : croppedHeight;

        int pointX = (int) mCanvasView.getIntersectionPoint().mX;
        int pointY = (int) mCanvasView.getIntersectionPoint().mY;

        Bitmap result = Bitmap.createBitmap(bitmap, pointX, pointY, width, height, null, true);

        mImageView.setImageBitmap(result);
    }
}
