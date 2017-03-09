package com.marks.longview;

/***
 * @author marks.luo
 * @Description: TODO(图片下载监听器)
 * @date:2017-03-08 16:42
 */
public interface ImageDownloadListener {
    /***
     * 更新监听
     * @param progress 进度
     */
    void onUpdate(int progress);
}
