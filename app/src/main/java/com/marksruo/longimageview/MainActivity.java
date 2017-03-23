package com.marksruo.longimageview;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.marks.longview.SubsamplingScaleImageView;
import com.marksruo.longimageview.databinding.ActivityMainBinding;


public class MainActivity extends AppCompatActivity {
    private SubsamplingScaleImageView mImageView;
    private String url = "http://ww3.sinaimg.cn/bmiddle/a20a9b80jw1etmbognz7rj20cs3jiqa6.jpg";//长图
    private String path = "http://img1.imgtn.bdimg.com/it/u=1794894692,1423685501&fm=23&gp=0.jpg";//正常图
    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        binding.thumbnailsImage.setImageThumbnailByUri(url);
        binding.thumbnailsImageView.setImageThumbnailByUri(path);
        binding.longClick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, LongImageActivity.class));
            }
        });
        binding.thumbnailsImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, LongImageActivity.class));
            }
        });
        binding.showView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, ShowImageActivity.class));
            }
        });
        binding.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, ImageViewActivity.class));
            }
        });
        binding.showViewPager.setVisibility(View.GONE);
        binding.showViewPager.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, ViewPagerActivity.class));
            }
        });
    }
}
