package com.interview.administrator.interviewtestapplication;

import android.content.res.Resources;

/**
 * <p>Description.</p>
 * <p>
 * <b>Maintenance History</b>:
 * <table>
 * <tr>
 * <th>Date</th>
 * <th>Developer</th>
 * <th>Target</th>
 * <th>Content</th>
 * </tr>
 * <tr>
 * <td>2018-09-13 11:05</td>
 * <td>${User}</td>
 * <td>All</td>
 * <td>Created.</td>
 * </tr>
 * </table>
 */
public class UiUtils {
    /**
     * Get the width of screen in pixel.
     *
     * @return width of screen in pixel
     */
    public static int getScreenWidth() {
        return Resources.getSystem().getDisplayMetrics().widthPixels;
    }

    /**
     * Get the height of screen in pixel.
     *
     * @return height of screen in pixel
     */
    public static int getScreenHeight() {
        return Resources.getSystem().getDisplayMetrics().heightPixels;
    }

    public static int dp2px(int dp){
        return (int) (Resources.getSystem().getDisplayMetrics().density*dp);
    }

    public static int sp2px(int sp){
        return (int) (Resources.getSystem().getDisplayMetrics().scaledDensity*sp);
    }

    public static int getStatusBarHeight() {
        int result = 0;
        int resId = Resources.getSystem().getIdentifier("status_bar_height", "dimen", "android");
        if (resId > 0) {
            result = Resources.getSystem().getDimensionPixelSize(resId);
        }
        return result;
    }
}
