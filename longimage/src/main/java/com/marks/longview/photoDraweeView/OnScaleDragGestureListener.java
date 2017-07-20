package com.marks.longview.photoDraweeView;

/***
 * @author marks.luo
 * @Description: ()
 * @date:2017-07-20 17:09
 */
public interface OnScaleDragGestureListener {
    void onDrag(float dx, float dy);

    void onFling(float startX, float startY, float velocityX, float velocityY);

    void onScale(float scaleFactor, float focusX, float focusY);

    void onScaleEnd();
}
