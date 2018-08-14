package com.descendant.settings.fragments;

import com.android.internal.logging.nano.MetricsProto;

import android.app.Activity;
import android.os.Bundle;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Bundle;
import android.os.UserHandle;
import android.content.ContentResolver;
import android.content.res.Resources;
import android.support.v7.preference.ListPreference;
import android.support.v7.preference.Preference;
import android.support.v7.preference.PreferenceScreen;
import android.support.v7.preference.Preference.OnPreferenceChangeListener;
import android.support.v14.preference.SwitchPreference;

import android.provider.Settings;
import com.android.settings.R;
import com.android.settings.SettingsPreferenceFragment;
import java.util.Locale;
import android.text.TextUtils;
import android.view.View;

import com.descendant.settings.preferences.Utils;
import com.descendant.settings.preferences.SystemSettingSwitchPreference;

import java.util.List;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class QuickSettings extends SettingsPreferenceFragment implements
        OnPreferenceChangeListener {

    private static final String QS_TILE_TINTING = "qs_tile_tinting_enable";
    private SwitchPreference mEnableQsTileTinting;
    private Preference mFileHeader;
    private static final int REQUEST_PICK_IMAGE = 0;

    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);

        addPreferencesFromResource(R.xml.descendant_settings_quicksettings);

        PreferenceScreen prefScreen = getPreferenceScreen();
        ContentResolver resolver = getActivity().getContentResolver();

        //QS Tile Theme
        mEnableQsTileTinting = (SwitchPreference) findPreference(QS_TILE_TINTING);
        mEnableQsTileTinting.setChecked(Settings.System.getInt(resolver,
                Settings.System.QS_TILE_TINTING_ENABLE, 1) == 1);
        mEnableQsTileTinting.setOnPreferenceChangeListener(this);

        }

    @Override
    public boolean onPreferenceTreeClick(Preference preference) {
	ContentResolver resolver = getActivity().getContentResolver();
        if (preference == mFileHeader) {
            Intent intent = new Intent(Intent.ACTION_PICK);
            intent.setType("image/*");
             startActivityForResult(intent, REQUEST_PICK_IMAGE);
             return true;
         }
         return super.onPreferenceTreeClick(preference);
     }

    @Override
    public boolean onPreferenceChange(Preference preference, Object newValue) {
	if (preference == mEnableQsTileTinting) {
            boolean value = (Boolean) newValue;
            Settings.System.putInt(getContentResolver(),
                    Settings.System.QS_TILE_TINTING_ENABLE, value ? 1 : 0);
             Utils.restartSystemUi(getContext());
        }
	return true;
    }

    @Override
    public int getMetricsCategory() {
        return MetricsProto.MetricsEvent.DESCENDANT_SETTINGS;
    }

}
