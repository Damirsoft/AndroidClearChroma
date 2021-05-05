package com.damirsoft.androidclearchroma.colormode;

import com.damirsoft.androidclearchroma.colormode.mode.ARGB;
import com.damirsoft.androidclearchroma.colormode.mode.AbstractColorMode;
import com.damirsoft.androidclearchroma.colormode.mode.CMYK;
import com.damirsoft.androidclearchroma.colormode.mode.CMYK255;
import com.damirsoft.androidclearchroma.colormode.mode.HSL;
import com.damirsoft.androidclearchroma.colormode.mode.HSV;
import com.damirsoft.androidclearchroma.colormode.mode.RGB;


public enum ColorMode {
    RGB(0), HSV(1), ARGB(2), CMYK(3), CMYK255(4), HSL(5);

    private int i;

    /**
     * Construct color mode with an unique ID
     * @param id Id of mode
     */
    ColorMode(int id) {
        i = id;
    }

    /**
     * Get unique ID of mode
     * @return ID
     */
    public int getId() {
        return i;
    }

    /**
     * Get color mode object link to mode
     * @return Color mode object
     */
    public AbstractColorMode getColorMode() {
        switch (this) {
            case RGB:
            default:
                return new RGB();
            case HSV:
                return new HSV();
            case ARGB:
                return new ARGB();
            case CMYK:
                return new CMYK();
            case CMYK255:
                return new CMYK255();
            case HSL:
                return new HSL();
        }
    }

    /**
     * Retrieves the color mode from id.
     * @param id Unique ID
     * @return Color mode
     */
    public static ColorMode getColorModeFromId(int id) {
        switch (id) {
            case(0):
            default:
                return RGB;
            case(1):
                return HSV;
            case(2):
                return ARGB;
            case(3):
                return CMYK;
            case(4):
                return CMYK255;
            case(5):
                return HSL;
        }
    }
}
