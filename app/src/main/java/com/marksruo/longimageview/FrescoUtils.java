package com.marksruo.longimageview;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.util.Log;

import com.facebook.binaryresource.BinaryResource;
import com.facebook.binaryresource.FileBinaryResource;
import com.facebook.cache.common.CacheKey;
import com.facebook.common.executors.CallerThreadExecutor;
import com.facebook.common.references.CloseableReference;
import com.facebook.datasource.DataSource;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.imagepipeline.cache.DefaultCacheKeyFactory;
import com.facebook.imagepipeline.core.ImagePipeline;
import com.facebook.imagepipeline.core.ImagePipelineFactory;
import com.facebook.imagepipeline.datasource.BaseBitmapDataSubscriber;
import com.facebook.imagepipeline.image.CloseableImage;
import com.facebook.imagepipeline.request.ImageRequest;
import com.facebook.imagepipeline.request.ImageRequestBuilder;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;

/***
 * @author marks.luo
 * @Description: TODO(FrescoUtils)
 * @date:2017-06-02 11:53
 */
public class FrescoUtils {
    private static final String TAG = "FrescoUtils";
    public static final String IMAGE_PIC_CACHE_DIR = Environment.getExternalStorageDirectory().getPath() + "/Android/";

    /***
     * 保存图片
     * @param picUrl 图片地址
     * @param context
     */
    public static void savePicture(String picUrl, Context context) {
        File file = new File(IMAGE_PIC_CACHE_DIR);
        if (!file.exists()) {
            file.mkdir();
        }
        CacheKey cacheKey = DefaultCacheKeyFactory.getInstance().getEncodedCacheKey(ImageRequest.fromUri(Uri.parse(picUrl)), context);
        File cacheFile = getCachedImageOnDisk(cacheKey);
        if (cacheFile == null) {
            downLoadImage(Uri.parse(picUrl), "down", context);
            return;
        } else {
            copyTo(cacheFile, file, "down");
        }
    }

    /**
     * 复制文件
     *
     * @param cacheFile 源文件
     * @param file      目标文件
     * @return
     */
    private static boolean copyTo(File cacheFile, File file, String down) {
        FileInputStream fi = null;
        FileOutputStream fo = null;
        FileChannel in = null;
        FileChannel out = null;
        try {
            fi = new FileInputStream(cacheFile);
            in = fi.getChannel();
            File dst;
            dst = new File(file, down + ".jpg");
            fo = new FileOutputStream(dst);
            out = fo.getChannel();//得到对应的文件通道
            in.transferTo(0, in.size(), out);//连接两个通道，并且从in通道读取，然后写入out通道
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            try {

                if (fi != null) {
                    fi.close();
                }

                if (in != null) {
                    in.close();
                }

                if (fo != null) {
                    fo.close();
                }

                if (out != null) {
                    out.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
                return false;
            }
        }

    }

    private static void downLoadImage(Uri uri, final String filename, Context context) {
        ImageRequest imageRequest = ImageRequestBuilder
                .newBuilderWithSource(uri)
                .setProgressiveRenderingEnabled(true)
                .build();
        ImagePipeline imagePipeline = Fresco.getImagePipeline();
        DataSource<CloseableReference<CloseableImage>> dataSource = imagePipeline.fetchDecodedImage(imageRequest, context);
        dataSource.subscribe(new BaseBitmapDataSubscriber() {
            @Override
            protected void onNewResultImpl(Bitmap bitmap) {
                if (bitmap == null) {
                    Log.i("lbxx", "保存图片失败，无法下载图片");
                }
                File appDir = new File(IMAGE_PIC_CACHE_DIR);
                if (!appDir.exists()) {
                    appDir.mkdir();
                }
                String fileName = filename + ".jpg";
                File file = new File(appDir, fileName);
                try {
                    FileOutputStream fos = new FileOutputStream(file);
                    assert bitmap != null;
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
                    fos.flush();
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            protected void onFailureImpl(DataSource<CloseableReference<CloseableImage>> dataSource) {

            }
        }, CallerThreadExecutor.getInstance());
    }

    public static File getCachedImageOnDisk(CacheKey cacheKey) {
        File loadFile = null;
        if (cacheKey != null) {
            if (ImagePipelineFactory.getInstance().getMainDiskStorageCache().hasKey(cacheKey)) {//判断磁盘存储中是否存在
                BinaryResource binaryResource = ImagePipelineFactory.getInstance().getMainDiskStorageCache().getResource(cacheKey);
                loadFile = ((FileBinaryResource) binaryResource).getFile();
            } else if (ImagePipelineFactory.getInstance().getSmallImageDiskStorageCache().hasKey(cacheKey)) {
                BinaryResource binaryResource = ImagePipelineFactory.getInstance().getSmallImageDiskStorageCache().getResource(cacheKey);
                loadFile = ((FileBinaryResource) binaryResource).getFile();
            }
        }
        return loadFile;
    }

}
