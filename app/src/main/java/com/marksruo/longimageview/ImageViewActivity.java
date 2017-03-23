package com.marksruo.longimageview;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import com.marksruo.longimageview.databinding.ActivityImageBinding;

/***
 * @author marks.luo
 * @Description: TODO()
 * @date:2017-03-14 09:55
 */
public class ImageViewActivity extends AppCompatActivity {
    private String url = "http://img1.imgtn.bdimg.com/it/u=1794894692,1423685501&fm=23&gp=0.jpg";//正常图
    private ActivityImageBinding binding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_image);
//         binding.image.setImageType(SubsamplingScaleImageView.SCALE_TYPE_CENTER_CROP);
        binding.image.setImageUri(url);
    }
}
