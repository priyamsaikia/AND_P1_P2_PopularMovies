package apps.orchotech.com.popularmovies.activities;

import android.os.Bundle;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceActivity;

import apps.orchotech.com.popularmovies.R;

/**
 * Created by PriyamSaikia on 15-05-2016.
 */
public class SettingsActivity extends PreferenceActivity implements Preference.OnPreferenceChangeListener {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.pref_general);
        findPreference(getString(R.string.settings_key_sort_by)).setOnPreferenceChangeListener(this);
    }

    @Override
    public boolean onPreferenceChange(Preference preference, Object newValue) {
        if (preference instanceof ListPreference) {
            preference.setSummary(newValue.toString());
            finish();
        }
        return true;
    }
}
