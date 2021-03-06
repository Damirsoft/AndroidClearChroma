package com.damirsoft.androidclearchroma.sample;

import android.os.Bundle;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import android.view.MenuItem;

import com.damirsoft.androidclearchroma.ChromaPreferenceCompat;
import com.damirsoft.androidclearchroma.ChromaPreferenceFragmentCompat;
import com.damirsoft.androidclearchroma.IndicatorMode;
import com.damirsoft.androidclearchroma.colormode.ColorMode;

/**
 * Sample Activity for show chroma preferences with compatibility class
 */
public class PreferencesCompatActivity extends AppCompatActivity {

    private static final String TAG_PREFERENCE_FRAGMENT = "TAG_PREFERENCE_FRAGMENT";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Manage fragment who contains list of preferences
        ChromaPreferenceFragmentCompat chromaPreferenceFragmentCompat =
                (ChromaPreferenceFragmentCompat) getSupportFragmentManager().findFragmentByTag(TAG_PREFERENCE_FRAGMENT);

        if(chromaPreferenceFragmentCompat == null)
            chromaPreferenceFragmentCompat = new ColorPreferenceFragmentCompat();

        getSupportFragmentManager()
                .beginTransaction()
                .replace(android.R.id.content, chromaPreferenceFragmentCompat, TAG_PREFERENCE_FRAGMENT)
                .commit();

        ActionBar toolbar = getSupportActionBar();
        if(toolbar != null) {
            toolbar.setDisplayHomeAsUpEnabled(true);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == android.R.id.home) {
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * Tiny class inherited from ChromaPreferenceFragmentCompat who manage chroma preferences and callback of DialogFragment
     */
    public static class ColorPreferenceFragmentCompat extends ChromaPreferenceFragmentCompat {

        @Override
        public void onCreatePreferences(Bundle bundle, String s) {
            addPreferencesFromResource(R.xml.prefs_v7); // load your ChromaPreferenceCompat prefs from xml

            //or add them manually
            ChromaPreferenceCompat pref = new ChromaPreferenceCompat(getContext());
            pref.setIcon(R.drawable.palette);
            pref.setTitle("RGB(added from java)");
            pref.setSummary("Summary ...");
            pref.setColorMode(ColorMode.RGB);
            pref.setIndicatorMode(IndicatorMode.HEX);
            pref.setKey("any_key_you_need");
            getPreferenceScreen().addPreference(pref);
        }
    }
}
