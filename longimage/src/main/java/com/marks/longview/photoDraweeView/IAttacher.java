package com.marks.longview.photoDraweeView;

import android.view.GestureDetector;
import android.view.View;

/***
 * @author marks.luo
 * @Description: ()
 * @date:2017-07-20 17:10
 */
public interface IAttacher {
    float DEFAULT_MAX_SCALE = 3.0f;
    float DEFAULT_MID_SCALE = 1.75f;
    float DEFAULT_MIN_SCALE = 1.0f;
    long ZOOM_DURATION = 200L;

    float getMinimumScale();

    float getMediumScale();

    float getMaximumScale();

    void setMaximumScale(float maximumScale);

    void setMediumScale(float mediumScale);

    void setMinimumScale(float minimumScale);

    float getScale();

    void setScale(float scale);

    void setScale(float scale, boolean animate);

    void setScale(float scale, float focalX, float focalY, boolean animate);

    void setOrientation(@Attacher.OrientationMode int orientation);

    void setZoomTransitionDuration(long duration);

    void setAllowParentInterceptOnEdge(boolean allow);

    void setOnDoubleTapListener(GestureDetector.OnDoubleTapListener listener);

    void setOnScaleChangeListener(OnScaleChangeListener listener);

    void setOnLongClickListener(View.OnLongClickListener listener);

    void setOnPhotoTapListener(OnPhotoTapListener listener);

    void setOnViewTapListener(OnViewTapListener listener);

    OnPhotoTapListener getOnPhotoTapListener();

    OnViewTapListener getOnViewTapListener();

    void update(int imageInfoWidth, int imageInfoHeight);
}
