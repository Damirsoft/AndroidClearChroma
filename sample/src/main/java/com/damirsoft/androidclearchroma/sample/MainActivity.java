package com.damirsoft.androidclearchroma.sample;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import androidx.annotation.ColorInt;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import androidx.core.content.ContextCompat;
import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.PreferenceManager;
import androidx.appcompat.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.damirsoft.androidclearchroma.ChromaDialog;
import com.damirsoft.androidclearchroma.ChromaUtil;
import com.damirsoft.androidclearchroma.IndicatorMode;
import com.damirsoft.androidclearchroma.colormode.ColorMode;
import com.damirsoft.androidclearchroma.listener.OnColorChangedListener;
import com.damirsoft.androidclearchroma.listener.OnColorSelectedListener;

/**
 * Main activity of sample project, contains buttons for show fragment, preferences and dialog
 */
public class MainActivity extends AppCompatActivity implements OnColorSelectedListener, OnColorChangedListener {

    private static final String EXTRA_COLOR = "EXTRA_COLOR";
    private static final String EXTRA_COLOR_MODE = "EXTRA_COLOR_MODE";
    private static final String EXTRA_INDICATOR_MODE = "EXTRA_INDICATOR_MODE";

    private Spinner spinner;
    private TextView textView;
    private Toolbar toolbar;
    private FloatingActionButton fab;

    private
    @ColorInt
    int color;
    private ColorMode colorMode;
    private IndicatorMode indicatorMode;

    private static final String TAG_CHROMA_DIALOG = "TAG_CHROMA_DIALOG";
    private ChromaDialog chromaDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Load defaultValues from prefs_v7.xml
        PreferenceManager.setDefaultValues(this, R.xml.prefs_v7, false);

        toolbar = findViewById(R.id.toolbar);
        textView = findViewById(R.id.text_view);
        spinner = findViewById(R.id.spinner);
        fab = findViewById(R.id.fab);

        if (savedInstanceState == null) {
            color = ContextCompat.getColor(this, R.color.colorPrimary);
            colorMode = ColorMode.RGB;
            indicatorMode = IndicatorMode.HEX;
        } else {
            color = savedInstanceState.getInt(EXTRA_COLOR);
            colorMode = ColorMode.getColorModeFromId(savedInstanceState.getInt(EXTRA_COLOR_MODE));
            indicatorMode = IndicatorMode.getIndicatorModeFromId(savedInstanceState.getInt(EXTRA_INDICATOR_MODE));
        }

        setSupportActionBar(toolbar);
        updateTextView(color);
        updateToolbarAndStatusBar(color);
        fab.setBackgroundTintList(ColorStateList.valueOf(color));
        setupSpinner();

        // Event for Floating Action Button
        findViewById(R.id.fab).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (chromaDialog == null) {
                    chromaDialog = new ChromaDialog.Builder()
                            .initialColor(color)
                            .colorMode(colorMode)
                            .indicatorMode(indicatorMode) //HEX or DECIMAL;
                            .create();
                }
                chromaDialog.show(getSupportFragmentManager(), TAG_CHROMA_DIALOG);
            }
        });

        // Events for each button
        findViewById(R.id.button_open_fragment).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, FragmentColorActivity.class));
            }
        });

        // Events for each button
        findViewById(R.id.button_open_view).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, ViewColorActivity.class));
            }
        });


        findViewById(R.id.prefsButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, PreferencesCompatActivity.class));
            }
        });
    }

    private void setupSpinner() {
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item);
        for (ColorMode m : ColorMode.values()) {
            adapter.add(m.name());
        }
        adapter.notifyDataSetChanged();

        spinner.setAdapter(adapter);
        spinner.setSelection(colorMode.ordinal());
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                colorMode = ColorMode.values()[position];
                switch(colorMode) {
                    case CMYK:
                    case CMYK255:
                        indicatorMode = IndicatorMode.DECIMAL;
                }
                chromaDialog = null;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    private void updateTextView(int newColor) {
        textView.setText(ChromaUtil.getFormattedColorString(newColor, colorMode == ColorMode.ARGB));
        textView.setTextColor(newColor);
    }

    private void updateToolbarAndStatusBar(int newColor) {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            toolbar.setBackground(new ColorDrawable(newColor));
        }
        else {
            toolbar.setBackgroundDrawable(new ColorDrawable(newColor));
        }
        Utility.updatetatusBar(this, newColor);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putInt(EXTRA_COLOR, color);
        outState.putInt(EXTRA_COLOR_MODE, colorMode.ordinal());
        outState.putInt(EXTRA_INDICATOR_MODE, indicatorMode.ordinal());
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onPositiveButtonClick(@ColorInt int newColor) {
        color = newColor;
        chromaDialog = null;
        fab.setBackgroundTintList(ColorStateList.valueOf(color));
        updateTextView(newColor);
        updateToolbarAndStatusBar(newColor);
    }

    @Override
    public void onNegativeButtonClick(@ColorInt int newColor) {
        updateToolbarAndStatusBar(color);
    }

    @Override
    public void onColorChanged(@ColorInt int newColor) {
        updateToolbarAndStatusBar(newColor);
    }
}
