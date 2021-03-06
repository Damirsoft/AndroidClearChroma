package com.damirsoft.androidclearchroma.sample;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import androidx.annotation.ColorInt;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import android.view.MenuItem;

import com.damirsoft.androidclearchroma.ChromaUtil;
import com.damirsoft.androidclearchroma.IndicatorMode;
import com.damirsoft.androidclearchroma.colormode.ColorMode;
import com.damirsoft.androidclearchroma.fragment.ChromaColorFragment;
import com.damirsoft.androidclearchroma.listener.OnColorChangedListener;

/**
 * An activity that simply displays the color fragment.
 */
public class FragmentColorActivity extends AppCompatActivity implements OnColorChangedListener {

    private static final String TAG_COLOR_FRAGMENT = "TAG_COLOR_FRAGMENT";

    private static final String SAVED_COLOR = "SAVED_COLOR";
    private @ColorInt int initialColor = Color.BLUE;

    private ActionBar toolbar;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_color_fragment);

        toolbar = getSupportActionBar();
        if (toolbar != null) {
            toolbar.setDisplayHomeAsUpEnabled(true);
        }

        if (savedInstanceState != null) {
            initialColor = savedInstanceState.getInt(SAVED_COLOR, initialColor);
        }
        onColorChanged(initialColor);

        if (null == savedInstanceState) {
            ChromaColorFragment chromaColorFragment =
                    ChromaColorFragment.newInstance(initialColor, ColorMode.ARGB, IndicatorMode.HEX);
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.container_color_fragment, chromaColorFragment, TAG_COLOR_FRAGMENT)
                    .commit();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(SAVED_COLOR, initialColor);
    }

    @Override
    public void onColorChanged(@ColorInt int color) {
        initialColor = color;
        toolbar.setBackgroundDrawable(new ColorDrawable(color));
        Utility.updatetatusBar(this, color);
        toolbar.setTitle(ChromaUtil.getFormattedColorString(color, false));
    }
}
