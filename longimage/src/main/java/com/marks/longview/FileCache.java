package com.marks.longview;

import android.content.Context;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/***
 * @author marks.luo
 * @Description: TODO(文件缓存)
 * @date:2017-03-08 16:37
 */
public class FileCache {
    private File cacheDir;

    public FileCache(Context context) {
        //找到缓存图像的目录
        if (android.os.Environment.getExternalStorageState().equals(android.os.Environment.MEDIA_MOUNTED)) {
            cacheDir = new File(android.os.Environment.getExternalStorageDirectory(), "fresco_cache");
        } else {
            cacheDir = context.getCacheDir();
        }
        if (!cacheDir.exists()) {
            cacheDir.mkdirs();
        }
    }

    public File getCacheDir() {
        return cacheDir;
    }

    public File getFile(String url) {
        String filename = String.valueOf(url.hashCode());
        File f = new File(cacheDir, filename);
        if (f.exists()) {
            return f;
        }
        return null;
    }

    public void clear() {
        File[] files = cacheDir.listFiles();
        if (files == null) {
            return;
        }
        for (File f : files) {
            f.delete();
        }
    }

    /***
     * 复制文件
     * @param inputStream
     * @param outputStream
     */
    public static void CopyStream(InputStream inputStream, OutputStream outputStream) {
        final int buffer_size = 1024;
        byte[] bytes = new byte[buffer_size];
        for (; ; ) {
            try {
                int count = inputStream.read(bytes, 0, buffer_size);
                if (count == -1) {
                    break;
                }
                outputStream.write(bytes, 0, count);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
