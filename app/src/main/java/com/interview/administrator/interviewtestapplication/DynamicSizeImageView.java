package com.interview.administrator.interviewtestapplication;


import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;

import androidx.appcompat.widget.AppCompatImageView;

/**
 * <p>Description.</p>
 *
 * <b>Maintenance History</b>:
 * <table>
 * 		<tr>
 * 			<th>Date</th>
 * 			<th>Developer</th>
 * 			<th>Target</th>
 * 			<th>Content</th>
 * 		</tr>
 * 		<tr>
 * 			<td>2018-11-21 11:01</td>
 * 			<td>Rui Chaoqun</td>
 * 			<td>All</td>
 *			<td>Created.</td>
 * 		</tr>
 * </table>
 */
public class DynamicSizeImageView extends AppCompatImageView {
    private int mHeight;
    private Rect rect;

    public DynamicSizeImageView(Context context) {
        super(context);
    }

    public DynamicSizeImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public DynamicSizeImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    {
        rect = new Rect();
    }

    @Override
    protected void onDraw(Canvas canvas) {

        Matrix matrix = getImageMatrix();
        recalculateMatrix(matrix);
//        rect.left = 0;
//        rect.right = canvas.getWidth();
//        rect.top = 0;
//        rect.bottom = mHeight;
//        canvas.clipRect(rect);
        super.onDraw(canvas);

    }

    private void recalculateMatrix(Matrix matrix) {
        int dwidth = getDrawable().getIntrinsicWidth();
        int dheight = getDrawable().getIntrinsicHeight();

        int vwidth = getWidth();
        int vheight = mHeight;
        float scale;
        float dx = 0, dy = 0;

        if (dwidth * vheight > vwidth * dheight) {
            scale = (float) vheight / (float) dheight;
            dx = (vwidth - dwidth * scale) * 0.5f;
        } else {
            scale = (float) vwidth / (float) dwidth;
            dy = (vheight - dheight * scale) * 0.5f;
        }

        matrix.setScale(scale, scale);
        matrix.postTranslate(Math.round(dx), Math.round(dy));
    }

    public void setImageHeight(int height){
        mHeight = height;
        postInvalidate();
    }


    @Override
    public void setPadding(int left, int top, int right, int bottom) {
        super.setPadding(left, top, right, bottom);
    }
}
