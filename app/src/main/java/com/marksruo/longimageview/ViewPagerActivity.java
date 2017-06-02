package com.marksruo.longimageview;

import android.databinding.DataBindingUtil;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import com.marks.longview.ImageUrlUtil;
import com.marks.longview.widget.HackyViewPager;
import com.marksruo.longimageview.databinding.ActivityViewPagerBinding;

/***
 * @author marks.luo
 * @Description: TODO()
 * @date:2017-03-17 16:20
 */
public class ViewPagerActivity extends BaseActivity {
    private ActivityViewPagerBinding mBinding;
    private static final String IS_LOCKED = "isLocked";
    private int position;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //设置全屏展示
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) return;
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                | View.SYSTEM_UI_FLAG_FULLSCREEN
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);

        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_view_pager);
        mBinding.viewPager.setAdapter(new SamplePagerAdapter());
        if (getIntent() != null) {
            position = getIntent().getIntExtra("position", 0);
            mBinding.viewPager.setCurrentItem(position);
        }
        if (savedInstanceState != null) {
            boolean isLocked = savedInstanceState.getBoolean(IS_LOCKED, false);
            mBinding.viewPager.setLocked(isLocked);
        }
    }

    private boolean isViewPagerActive() {
        return (mBinding.viewPager != null && mBinding.viewPager instanceof HackyViewPager);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        if (isViewPagerActive()) {
            outState.putBoolean(IS_LOCKED, mBinding.viewPager.isLocked());
        }
        super.onSaveInstanceState(outState);
    }

    private class SamplePagerAdapter extends PagerAdapter {
        private final String[] sDrawables = ImageUrlUtil.getImageUrls();

        @Override
        public int getCount() {
            return sDrawables.length;
        }

        @Override
        public View instantiateItem(ViewGroup container, int position) {
//            PhotoView
            return null;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }
    }
}
