package com.marks.longview.photoDraweeView;

import android.view.View;

/***
 * @author marks.luo
 * @Description: (Interface definition for a callback to be invoked when the Photo is tapped with a single
 * tap.)
 * @date:2017-07-20 17:10
 */
public interface OnPhotoTapListener {
    /**
     * A callback to receive where the user taps on a photo. You will only receive a callback if
     * the user taps on the actual photo, tapping on 'whitespace' will be ignored.
     *
     * @param view - View the user tapped.
     * @param x - where the user tapped from the of the Drawable, as percentage of the
     * Drawable width.
     * @param y - where the user tapped from the top of the Drawable, as percentage of the
     * Drawable height.
     */
    void onPhotoTap(View view, float x, float y);
}
