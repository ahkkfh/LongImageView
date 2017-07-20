package com.marks.longview;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.util.Log;
import android.widget.ImageView;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Image tools
 * <p/>
 * Created by Administrator on 2015/12/19.
 */
public class ImageUtils {
    private static final String TAG = ImageUtils.class.getSimpleName();

    private ImageUtils(Context context) {
        mContext = context;
    }

    private static Context mContext;
    private static ImageUtils mInstance;

    public static ImageUtils getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new ImageUtils(context);
        }
        return mInstance;
    }

    private static final double DEFAULT_WIDTH = 720;

    /**
     * 将多张图片合成长图
     *
     * @param imgPaths 图片路径
     * @return 长图bitmap
     */
    public static Bitmap combineMultipleImages(List<String> imgPaths) {
        List<BitmapFactory.Options> optionses = new ArrayList<>();
        double reqWidth = DEFAULT_WIDTH;
        double totalHeight = 0;
        for (String path : imgPaths) {
            BitmapFactory.Options opt = new BitmapFactory.Options();
            opt.inJustDecodeBounds = true;
            BitmapFactory.decodeFile(path, opt);
            int w = opt.outWidth;
            int h = opt.outHeight;
            double scale = w / reqWidth;
            double reqHeight = h / scale;
            totalHeight += reqHeight;//初步计算
            opt.inSampleSize = calculateSamleSize((int) reqWidth, (int) reqHeight, w, h);
            optionses.add(opt);
        }
        double size = totalHeight * reqWidth * 4;
        double sizeScale = size / (1024 * 1024 * 4);//面积的比例

        if (sizeScale > 1) {
            sizeScale = Math.sqrt(sizeScale);//长宽的压缩比例
        } else {
            sizeScale = 1;
        }

        reqWidth /= sizeScale;
        totalHeight /= sizeScale;

        Bitmap result = Bitmap.createBitmap((int) reqWidth, (int) totalHeight, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(result);
        int nextHeight = 0;
        for (String path : imgPaths) {
            BitmapFactory.Options opt = optionses.remove(0);
            int w = opt.outWidth;
            int h = opt.outHeight;
            double scale = w / reqWidth;
            double reqHeight = h / scale;
            opt.inJustDecodeBounds = false;
            Bitmap tmpBitmap = BitmapFactory.decodeFile(path, opt);
            Bitmap scaledBitmap = Bitmap.createScaledBitmap(tmpBitmap, (int) reqWidth, (int) reqHeight, false);
            tmpBitmap.recycle();
            canvas.drawBitmap(scaledBitmap, 0, nextHeight, null);
            nextHeight += reqHeight;
            scaledBitmap.recycle();
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR1) {
            Log.e(TAG, "[LongPicture]Picture size is " + result.getByteCount());
        }
        return result;
    }

    public static int calculateSamleSize(int reqWidth, int reqHeight, int currentWidth, int currentHeight) {
        int inSampleSize = 1;
        if (currentHeight > reqHeight || currentWidth > reqWidth) {
            final int halfHeight = currentHeight / 2;//inSampleSize只能为二的N次方
            final int halfWidth = currentWidth / 2;
                /*
                If set to a value > 1, requests the decoder to subsample the original image,
                returning a smaller image to save memory.
                The sample size is the number of pixels in either dimension that correspond to a single pixel in the decoded bitmap.
                For example, inSampleSize == 4 returns an image that is 1/4 the width/height of the original, and 1/16 the number of pixels.
                Any value <= 1 is treated the same as 1.
                Note: the decoder uses a final value based on powers of 2, any other value will be rounded down to the nearest power of 2.
                 */
            while ((halfHeight / inSampleSize) > reqHeight && (halfWidth / inSampleSize) > reqWidth) {
                inSampleSize *= 2;
            }
        }
        return inSampleSize;
    }

    public static Bitmap decodeSampledBitmapFromResource(Resources res, int resId,
                                                         int reqWidth, int reqHeight) {

        // First decode with inJustDecodeBounds=true to check dimensions
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(res, resId, options);

        // Calculate inSampleSize
        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);

        // Decode bitmap with inSampleSize set
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeResource(res, resId, options);
    }

    private static int calculateInSampleSize(
            BitmapFactory.Options options, int reqWidth, int reqHeight) {
        // Raw height and width of image
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {

            final int halfHeight = height / 2;
            final int halfWidth = width / 2;

            // Calculate the largest inSampleSize value that is a power of 2 and keeps both
            // height and width larger than the requested height and width.
            while ((halfHeight / inSampleSize) > reqHeight
                    && (halfWidth / inSampleSize) > reqWidth) {
                inSampleSize *= 2;
            }
        }

        return inSampleSize;
    }

//    public static Bitmap compressImage(Bitmap image) {
//        ByteArrayOutputStream baos = new ByteArrayOutputStream();
//        image.compress(Bitmap.CompressFormat.JPEG, 100, baos);//质量压缩方法，这里100表示不压缩，把压缩后的数据存放到baos中
//        int options = 100;
//        while ( baos.toByteArray().length / 1024>100) {	//循环判断如果压缩后图片是否大于100kb,大于继续压缩
//            baos.reset();//重置baos即清空baos
//            image.compress(Bitmap.CompressFormat.JPEG, options, baos);//这里压缩options%，把压缩后的数据存放到baos中
//            options -= 10;//每次都减少10
//        }
//
//        ByteArrayInputStream isBm = new ByteArrayInputStream(baos.toByteArray());//把压缩后的数据baos存放到ByteArrayInputStream中
//        Bitmap bitmap = BitmapFactory.decodeStream(isBm, null, null);//把ByteArrayInputStream数据生成图片
//        baos.reset();
//        return bitmap;
//    }

    public static Bitmap compressResource(Resources resources, int resId) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(resources, resId, options);
        options.outWidth = (int) (options.outWidth * 0.3);
        options.outHeight = (int) (options.outHeight * 0.3);
        options.inPreferredConfig = Bitmap.Config.RGB_565;
        options.inSampleSize = 2;
        options.inJustDecodeBounds = false;
        Bitmap bitmap = BitmapFactory.decodeResource(resources, resId, options);
        return bitmap;
    }

    public static void releaseImg(ImageView imageView) {
        //			if(DEBUG)Log.v(TAG, "imgeview cache= "+imageView.getDrawingCache());
        imageView.setImageBitmap(null);
//        final BitmapDrawable drawable = (BitmapDrawable) imageView.getDrawingCache();
//        if (drawable != null && drawable.getBitmap() != null && !drawable.getBitmap().isRecycled()) {
//            drawable.getBitmap().recycle();
//        }
        Bitmap drawingCache = imageView.getDrawingCache();
        if (drawingCache != null && !drawingCache.isRecycled()) {
            drawingCache.recycle();
        }
    }
}
