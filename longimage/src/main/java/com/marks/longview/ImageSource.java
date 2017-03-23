package com.marks.longview;

import android.graphics.Bitmap;
import android.graphics.Rect;
import android.net.Uri;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

/**
 * 辅助类，用于设置各种源和附加属性，支持位图，资源，外部文件或者任何其他url，
 * 当使用预览图像时，必须使用dimensions（）方法为全尺寸图像的ImageSource对象设置完整大小图像的尺寸。
 */
public final class ImageSource {

    static final String FILE_SCHEME = "file:///";
    static final String ASSET_SCHEME = "file:///android_asset/";

    private final Uri uri;
    private final Bitmap bitmap;
    private final Integer resource;//资源id
    private boolean tile;
    private int sWidth;
    private int sHeight;
    private Rect sRegion;
    private boolean cached;


    private ImageSource(Bitmap bitmap, boolean cached) {
        this.bitmap = bitmap;
        this.uri = null;
        this.resource = null;
        this.tile = false;
        this.sWidth = bitmap.getWidth();
        this.sHeight = bitmap.getHeight();
        this.cached = cached;
    }

    private ImageSource(Uri uri) {
        //如果文件不存在，重新尝试url解码
        String urlString = uri.toString();
        if (urlString.startsWith(FILE_SCHEME)) {
            File file = new File(urlString.substring(FILE_SCHEME.length() - 1));
            if (!file.exists()) {
                try {
                    uri = Uri.parse(URLDecoder.decode(urlString, "UTF-8"));
                } catch (UnsupportedEncodingException e) {

                }
            }
        }
        this.bitmap = null;
        this.uri = uri;
        this.resource = null;
        this.tile = true;
    }

    private ImageSource(int resource) {
        this.bitmap = null;
        this.uri = null;
        this.resource = resource;
        this.tile = true;
    }

    /**
     * Create an instance from a resource. The correct resource for the device screen resolution will be used.
     * 根据资源id创建实例
     *
     * @param resId resource ID.
     */
    public static ImageSource resource(int resId) {
        return new ImageSource(resId);
    }

    /**
     * 根据资产创建实例
     *
     * @param assetName asset name.（资产名）
     */
    public static ImageSource asset(String assetName) {
        if (assetName == null) {
            throw new NullPointerException("Asset name must not be null");
        }
        return uri(ASSET_SCHEME + assetName);
    }

    /**
     * 根据uri创建实例，如果不是以uri开始，则假定是file的uri
     *
     * @param uri image URI.
     */
    public static ImageSource uri(String uri) {
        if (uri == null) {
            throw new NullPointerException("Uri must not be null");
        }
        if (!uri.contains("://")) {
            if (uri.startsWith("/")) {
                uri = uri.substring(1);
            }
            uri = FILE_SCHEME + uri;
        }
        return new ImageSource(Uri.parse(uri));
    }

    /**
     * Create an instance from a URI.
     *
     * @param uri image URI.
     */
    public static ImageSource uri(Uri uri) {
        if (uri == null) {
            throw new NullPointerException("Uri must not be null");
        }
        return new ImageSource(uri);
    }

    /**
     * 提供加载的位图用于显示
     *
     * @param bitmap bitmap to be displayed.
     */
    public static ImageSource bitmap(Bitmap bitmap) {
        if (bitmap == null) {
            throw new NullPointerException("Bitmap must not be null");
        }
        return new ImageSource(bitmap, false);
    }

    /**
     * 提供一个加载和缓存的位图用于显示
     *
     * @param bitmap bitmap to be displayed.
     * @return
     */
    public static ImageSource cacheBitmap(Bitmap bitmap) {
        if (bitmap == null) {
            throw new NullPointerException("Bitmap must not be null");
        }
        return new ImageSource(bitmap, true);
    }

    /**
     * Enable tiling of the image. This does not apply to preview images which are always loaded as a single bitmap.,
     * and tiling cannot be disabled when displaying a region of the source image.
     *
     * @return this instance for chaining.
     */
    public ImageSource tilingEnabled() {
        return tiling(true);
    }

    /**
     * Disable tiling of the image. This does not apply to preview images which are always loaded as a single bitmap,
     * and tiling cannot be disabled when displaying a region of the source image.
     *
     * @return this instance for chaining.
     */
    public ImageSource tilingDisabled() {
        return tiling(false);
    }

    /**
     * Enable or disable tiling of the image. This does not apply to preview images which are always loaded as a single bitmap,
     * and tiling cannot be disabled when displaying a region of the source image.
     *
     * @return this instance for chaining.
     */
    public ImageSource tiling(boolean tile) {
        this.tile = tile;
        return this;
    }

    /**
     * Use a region of the source image. Region must be set independently for the full size image and the preview if
     * you are using one.
     *
     * @return this instance for chaining.
     */
    public ImageSource region(Rect sRegion) {
        this.sRegion = sRegion;
        setInvariants();
        return this;
    }

    /**
     * Declare the dimensions of the image. This is only required for a full size image, when you are specifying a URI
     * and also a preview image. When displaying a bitmap object, or not using a preview, you do not need to declare
     * the image dimensions. Note if the declared dimensions are found to be incorrect, the view will reset.
     *
     * @return this instance for chaining.
     */
    public ImageSource dimensions(int sWidth, int sHeight) {
        if (bitmap == null) {
            this.sWidth = sWidth;
            this.sHeight = sHeight;
        }
        setInvariants();
        return this;
    }

    private void setInvariants() {
        if (this.sRegion != null) {
            this.tile = true;
            this.sWidth = this.sRegion.width();
            this.sHeight = this.sRegion.height();
        }
    }

    protected final Uri getUri() {
        return uri;
    }

    protected final Bitmap getBitmap() {
        return bitmap;
    }

    protected final Integer getResource() {
        return resource;
    }

    protected final boolean getTile() {
        return tile;
    }

    protected final int getSWidth() {
        return sWidth;
    }

    protected final int getSHeight() {
        return sHeight;
    }

    protected final Rect getSRegion() {
        return sRegion;
    }

    protected boolean isCached() {
        return cached;
    }
}
