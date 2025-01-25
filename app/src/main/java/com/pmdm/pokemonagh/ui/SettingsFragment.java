package com.pmdm.pokemonagh.ui;


import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.os.LocaleListCompat;
import androidx.preference.PreferenceFragmentCompat;
import androidx.preference.PreferenceManager;
import androidx.preference.SwitchPreferenceCompat;

import com.pmdm.pokemonagh.R;


public class SettingsFragment extends PreferenceFragmentCompat implements SharedPreferences.OnSharedPreferenceChangeListener {
    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        setPreferencesFromResource(R.xml.root_preferences, rootKey);

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this.getContext());
        Boolean spanish = sharedPreferences.getBoolean("switch_language", true);
        if (spanish) {
            LocaleListCompat appLocale = LocaleListCompat.forLanguageTags("es-ES");
            AppCompatDelegate.setApplicationLocales(appLocale);
        } else {
            LocaleListCompat appLocale = LocaleListCompat.forLanguageTags("en-US");
            AppCompatDelegate.setApplicationLocales(appLocale);
        }



    }

    @Override
    public void onResume() {
        super.onResume();
        getPreferenceManager().getSharedPreferences().registerOnSharedPreferenceChangeListener(this);

    }

    @Override
    public void onPause() {
        getPreferenceManager().getSharedPreferences().unregisterOnSharedPreferenceChangeListener(this);
        super.onPause();
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, @Nullable String key) {
        Boolean spanish = sharedPreferences.getBoolean("switch_language", true);
        if (spanish) {
            LocaleListCompat appLocale = LocaleListCompat.forLanguageTags("es-ES");
            AppCompatDelegate.setApplicationLocales(appLocale);
        } else {
            LocaleListCompat appLocale = LocaleListCompat.forLanguageTags("en-US");
            AppCompatDelegate.setApplicationLocales(appLocale);
        }
    }
}
