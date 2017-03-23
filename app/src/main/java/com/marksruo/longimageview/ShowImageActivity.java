package com.marksruo.longimageview;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import com.marksruo.longimageview.databinding.ActivityShowBinding;

/***
 * @author marks.luo
 * @Description: TODO()
 * @date:2017-03-14 09:49
 */
public class ShowImageActivity extends AppCompatActivity {
    private String url = "http://ww3.sinaimg.cn/bmiddle/a20a9b80jw1etmbognz7rj20cs3jiqa6.jpg";//长图
    private ActivityShowBinding binding;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= DataBindingUtil.setContentView(this,R.layout.activity_show);
        binding.showImage.setImageUri(url);
    }
}
