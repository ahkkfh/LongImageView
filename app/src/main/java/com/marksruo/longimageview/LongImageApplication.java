package com.marksruo.longimageview;

import android.app.Application;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.marks.longview.ImagePipelineConfigFactory;

/***
 * @author marks.luo
 * @Description: TODO()
 * @date:2017-03-08 16:52
 */

public class LongImageApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Fresco.initialize(this, ImagePipelineConfigFactory.getImagePipelineConfig(this));
    }
}
