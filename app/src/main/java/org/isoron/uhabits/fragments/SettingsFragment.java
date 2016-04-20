/*
 * Copyright (C) 2016 Álinson Santos Xavier <isoron@gmail.com>
 *
 * This file is part of Loop Habit Tracker.
 *
 * Loop Habit Tracker is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by the
 * Free Software Foundation, either version 3 of the License, or (at your
 * option) any later version.
 *
 * Loop Habit Tracker is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY
 * or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for
 * more details.
 *
 * You should have received a copy of the GNU General Public License along
 * with this program. If not, see <http://www.gnu.org/licenses/>.
 */

package org.isoron.uhabits.fragments;

import android.app.backup.BackupManager;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.preference.Preference;
import android.support.v7.preference.PreferenceCategory;
import android.support.v7.preference.PreferenceFragmentCompat;

import org.isoron.uhabits.MainActivity;
import org.isoron.uhabits.R;
import org.isoron.uhabits.helpers.UIHelper;

public class SettingsFragment extends PreferenceFragmentCompat
        implements SharedPreferences.OnSharedPreferenceChangeListener
{
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.preferences);

        setResultOnPreferenceClick("importData", MainActivity.RESULT_IMPORT_DATA);
        setResultOnPreferenceClick("exportCSV", MainActivity.RESULT_EXPORT_CSV);
        setResultOnPreferenceClick("exportDB", MainActivity.RESULT_EXPORT_DB);
        setResultOnPreferenceClick("bugReport", MainActivity.RESULT_BUG_REPORT);

        if(UIHelper.isLocaleFullyTranslated())
            removePreference("translate", "linksCategory");
    }

    @Override
    public void onCreatePreferences(Bundle bundle, String s)
    {

    }

    private void removePreference(String preferenceKey, String categoryKey)
    {
        PreferenceCategory cat = (PreferenceCategory) findPreference(categoryKey);
        Preference pref = findPreference(preferenceKey);
        cat.removePreference(pref);
    }

    private void setResultOnPreferenceClick(String key, final int result)
    {
        Preference pref = findPreference(key);
        pref.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener()
        {
            @Override
            public boolean onPreferenceClick(Preference preference)
            {
                getActivity().setResult(result);
                getActivity().finish();
                return true;
            }
        });
    }

    @Override
    public void onResume()
    {
        super.onResume();
        getPreferenceManager().getSharedPreferences().
                registerOnSharedPreferenceChangeListener(this);
    }

    @Override
    public void onPause()
    {
        getPreferenceManager().getSharedPreferences().
                unregisterOnSharedPreferenceChangeListener(this);
        super.onPause();
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key)
    {
        BackupManager.dataChanged("org.isoron.uhabits");
    }
}