package com.marksruo.longimageview;

import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.marks.longview.SubsamplingScaleImageView;


public class MainActivity extends AppCompatActivity {
    private SubsamplingScaleImageView mImageView;
//    private String url = "http://ww3.sinaimg.cn/bmiddle/a20a9b80jw1etmbognz7rj20cs3jiqa6.jpg";//长图
    private String url = "http://img1.imgtn.bdimg.com/it/u=1794894692,1423685501&fm=23&gp=0.jpg";//正常图

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mImageView = (SubsamplingScaleImageView) findViewById(R.id.subsamplingscale_image);
//        mImageView.setImageType(SubsamplingScaleImageView.SCALE_TYPE_CENTER_CROP);
//        mImageView.setImageUri(url);
        mImageView.setImageUriByGlide(url);

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) return;
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                | View.SYSTEM_UI_FLAG_FULLSCREEN
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
    }
}
