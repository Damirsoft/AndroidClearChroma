package com.damirsoft.androidclearchroma.listener;

import androidx.annotation.ColorInt;

public interface OnColorChangedListener {

    /**
     * Called when color has been changed
     * @param color int: The color that was selected in view
     */
    void onColorChanged(@ColorInt int color);
}
