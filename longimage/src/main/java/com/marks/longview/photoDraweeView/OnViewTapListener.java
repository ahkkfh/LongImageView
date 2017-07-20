package com.marks.longview.photoDraweeView;

import android.view.View;

/***
 * @author marks.luo
 * @Description: (Interface definition for a callback to be invoked when the ImageView is tapped with a single tap.)
 * @date:2017-07-20 17:07
 */
public interface OnViewTapListener {

    /**
     * A callback to receive where the user taps on a ImageView. You will receive a callback if
     * the user taps anywhere on the view, tapping on 'whitespace' will not be ignored.
     *
     * @param view - View the user tapped.
     * @param x    - where the user tapped from the left of the View.
     * @param y    - where the user tapped from the top of the View.
     */
    void onViewTap(View view, float x, float y);
}
