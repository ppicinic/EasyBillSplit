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
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.userpreferences);
//        findPreference(R.id.)
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        prefs.registerOnSharedPreferenceChangeListener(new SharedPreferences.OnSharedPreferenceChangeListener() {
            @Override
            public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String s) {
                System.out.println(s);
                System.out.println(sharedPreferences.getBoolean("PREF_TAX_CHECK", true));
                if(s.equals("PREF_TAX_CHECK")){
                    boolean tax = sharedPreferences.getBoolean("PREF_TAX_CHECK", true);
                    getPreferenceScreen().findPreference("PREF_TAX_AMOUNT").setEnabled(tax);
                }
            }
        });
    }
}
