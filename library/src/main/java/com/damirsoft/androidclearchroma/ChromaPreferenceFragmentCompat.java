package com.damirsoft.androidclearchroma;

import android.os.Bundle;
import androidx.annotation.ColorInt;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.damirsoft.androidclearchroma.listener.OnColorSelectedListener;


public abstract class ChromaPreferenceFragmentCompat extends PreferenceFragmentCompat implements OnColorSelectedListener {

    //I also recommend you to use some 3rd-party lib to style PreferenceFragmentCompat
    //because by default v7 prefs don't respect device theme and look ugly, even on v21+
    //theoretically useful links:
    // * http://stackoverflow.com/questions/32070670/preferencefragmentcompat-requires-preferencetheme-to-be-set
    // * https://github.com/consp1racy/android-support-preference

    private static final String TAG_FRAGMENT_DIALOG = "TAG_FRAGMENT_DIALOG";

    private Preference currentPreference;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);

        ChromaDialog chromaDialog = (ChromaDialog) (getFragmentManager() != null ? getFragmentManager().findFragmentByTag(TAG_FRAGMENT_DIALOG) : null);
        if (chromaDialog != null) {
            String keyPreference = chromaDialog.getKeyPreference();
            if (keyPreference != null)
                currentPreference = getPreferenceManager().findPreference(keyPreference);
            chromaDialog.setOnColorSelectedListener(this);
        }

        return view;
    }

    @Override
    public void onDisplayPreferenceDialog(Preference preference) {
        currentPreference = preference;
        // Try if the preference is one of our custom Preferences
        ChromaDialog dialogFragment = null;
        if (preference instanceof ChromaPreferenceCompat) {
            // Create a new instance of ChromaDialog with the key of the related
            // Preference
            dialogFragment = ChromaDialog.newInstance(
                    preference.getKey(),
                    ((ChromaPreferenceCompat) preference).getColor(),
                    ((ChromaPreferenceCompat) preference).getColorMode(),
                    ((ChromaPreferenceCompat) preference).getIndicatorMode());
            dialogFragment.setOnColorSelectedListener(this);
        }

        // If it was one of our custom Preferences, show its dialog
        if (dialogFragment != null && getFragmentManager() != null) {
            dialogFragment.setTargetFragment(this, 0);
            dialogFragment.show(getFragmentManager(), TAG_FRAGMENT_DIALOG);
        }
        // Could not be handled here. Try with the super method.
        else {
            super.onDisplayPreferenceDialog(preference);
        }
    }

    @Override
    public void onPositiveButtonClick(@ColorInt int color) {
        if (currentPreference instanceof ChromaPreferenceCompat) {
            ((ChromaPreferenceCompat) currentPreference).setColor(color);
        }
    }

    @Override
    public void onNegativeButtonClick(@ColorInt int color) {
    }
}
