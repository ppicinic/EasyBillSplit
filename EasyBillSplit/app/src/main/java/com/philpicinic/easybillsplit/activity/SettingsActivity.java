package com.philpicinic.easybillsplit.activity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.preference.PreferenceManager;

import com.philpicinic.easybillsplit.R;

/**
 * Created by Phil on 10/8/2014.
 */
public class SettingsActivity extends PreferenceActivity {

    public static final String SETTINGS = "SETTINGS";

    @Override
    @SuppressWarnings("deprecation")
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        addPreferencesFromResource(R.xml.userpreferences);
//        findPreference(R.id.)
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        getPreferenceScreen().findPreference("PREF_TAX_AMOUNT").
                setEnabled(prefs.getBoolean("PREF_TAX_CHECK", true));
        getPreferenceScreen().findPreference("PREF_TIP_AMOUNT").
                setEnabled(prefs.getBoolean("PREF_TIP_CHECK", true));
        prefs.registerOnSharedPreferenceChangeListener(new SharedPreferences.OnSharedPreferenceChangeListener() {
            @Override
            public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String s) {
                if (s.equals("PREF_TAX_CHECK")) {
                    boolean tax = sharedPreferences.getBoolean("PREF_TAX_CHECK", true);
                    getPreferenceScreen().findPreference("PREF_TAX_AMOUNT").setEnabled(tax);
                } else if (s.equals("PREF_TIP_CHECK")) {
                    boolean tip = sharedPreferences.getBoolean(s, true);
                    getPreferenceScreen().findPreference("PREF_TIP_AMOUNT").setEnabled(tip);
                }
            }
        });
    }
}
