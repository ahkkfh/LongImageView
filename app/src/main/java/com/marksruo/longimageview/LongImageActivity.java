package com.marksruo.longimageview;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.marks.longview.SubsamplingScaleImageView;
import com.marksruo.longimageview.databinding.ActivityLongBinding;

/***
 * @author marks.luo
 * @Description: TODO()
 * @date:2017-03-14 09:45
 */
public class LongImageActivity extends BaseActivity {
    private String url = "http://ww3.sinaimg.cn/bmiddle/a20a9b80jw1etmbognz7rj20cs3jiqa6.jpg";//长图
    private ActivityLongBinding binding;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_long);
        binding.subsamplingscaleImage.setImageType(SubsamplingScaleImageView.SCALE_TYPE_CENTER_CROP);
        binding.subsamplingscaleImage.setImageUri(url);

    }
}
