package com.damirsoft.androidclearchroma.listener;

import androidx.annotation.ColorInt;


public interface OnColorSelectedListener {

    /**
     * Called when positive button has been clicked.
     * @param color int: The color that was clicked.
     */
    void onPositiveButtonClick(@ColorInt int color);

    /**
     * Called when negative button has been clicked.
     * @param color int: The color that was clicked.
     */
    void onNegativeButtonClick(@ColorInt int color);
}
