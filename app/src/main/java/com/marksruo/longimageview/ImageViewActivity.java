package com.marksruo.longimageview;

import android.databinding.DataBindingUtil;
import android.graphics.Bitmap;
import android.graphics.PointF;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.facebook.common.executors.CallerThreadExecutor;
import com.facebook.common.references.CloseableReference;
import com.facebook.datasource.DataSource;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.imagepipeline.core.ImagePipeline;
import com.facebook.imagepipeline.datasource.BaseBitmapDataSubscriber;
import com.facebook.imagepipeline.image.CloseableImage;
import com.facebook.imagepipeline.request.ImageRequest;
import com.facebook.imagepipeline.request.ImageRequestBuilder;
import com.marks.longview.ImageSource;
import com.marks.longview.ImageViewState;
import com.marks.longview.SubsamplingScaleImageView;
import com.marksruo.longimageview.databinding.ActivityImageBinding;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/***
 * @author marks.luo
 * @Description: TODO()
 * @date:2017-03-14 09:55
 */
public class ImageViewActivity extends AppCompatActivity {
    private String url = "http://img1.imgtn.bdimg.com/it/u=1794894692,1423685501&fm=23&gp=0.jpg";//正常图
    private String uri = "http://opposns-test.oss-cn-shenzhen.aliyuncs.com/uploads/thread/attachment/2017/06/01/14963087825961.png_app.big.4g.thread.list.webp";
    private ActivityImageBinding binding;
    private String path = Environment.getExternalStorageDirectory().getPath() + "/Android/";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_image);
        binding.image.setMinScale(1.0F);
        binding.image.setMaxScale(10.0F);
        binding.image.setMinimumScaleType(SubsamplingScaleImageView.SCALE_TYPE_CENTER_CROP);
        binding.image.setImageType(SubsamplingScaleImageView.SCALE_TYPE_CENTER_INSIDE);
        binding.image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        binding.imageLoading.setVisibility(View.VISIBLE);
        showLongPhoto(Uri.parse(uri));
    }

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 1:
                    binding.image.setImage(ImageSource.uri((String) msg.obj), new ImageViewState(1.0F, new PointF(0, 0), 0));
                    binding.imageLoading.setVisibility(View.GONE);
                    Log.i("lbxx", "加载图片");
                    break;
            }
        }
    };

    public void showLongPhoto(final Uri url) {
        ImageRequest imageRequest = ImageRequestBuilder.newBuilderWithSource(url).setProgressiveRenderingEnabled(true).build();
        ImagePipeline imagePipeline = Fresco.getImagePipeline();
        DataSource<CloseableReference<CloseableImage>> dataSource = imagePipeline.fetchDecodedImage(imageRequest, this);
        dataSource.subscribe(new BaseBitmapDataSubscriber() {
            @Override
            protected void onNewResultImpl(Bitmap bitmap) {
                if (bitmap == null) {
                    Toast.makeText(ImageViewActivity.this, "图片加载失败", Toast.LENGTH_LONG).show();
                    Log.i("lbxx", "图片加载失败=====");
                    return;
                }
                File appDir = new File(path);
                if (!appDir.exists()) {
                    appDir.mkdir();
                }
                String fileName = String.valueOf(uri.hashCode()) + ".jpg";
                File file = new File(appDir, fileName);
                String path = file.getAbsolutePath();
                try {
                    FileOutputStream fos = new FileOutputStream(file);
                    assert bitmap != null;
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
                    fos.flush();
                    Log.i("lbxx", "file.path==" + file.getAbsolutePath());
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                Message msg = new Message();
                msg.what = 1;
                msg.obj = path;
                mHandler.sendMessage(msg);
                Log.i("lbxx", "handler.sendMessage");
//                binding.image.setImage(ImageSource.uri(file.getAbsolutePath()), new ImageViewState(1.0F, new PointF(0, 0), 0));
//                binding.imageLoading.setVisibility(View.GONE);
            }

            @Override
            protected void onFailureImpl(DataSource<CloseableReference<CloseableImage>> dataSource) {
                Log.i("lbxx", "获取图片失败");
                Toast.makeText(ImageViewActivity.this, "加载图片失败", Toast.LENGTH_SHORT).show();
                binding.imageLoading.setVisibility(View.GONE);
            }
        }, CallerThreadExecutor.getInstance());
    }
}
