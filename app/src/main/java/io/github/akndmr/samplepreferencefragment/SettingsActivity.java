package io.github.akndmr.samplepreferencefragment;

import android.content.SharedPreferences;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.preference.ListPreference;
import android.support.v7.preference.Preference;
import android.support.v7.preference.PreferenceFragmentCompat;
import android.support.v7.preference.PreferenceManager;
import android.widget.Toast;

import akndmr.github.io.colorprefutil.ColorPrefUtil;

public class SettingsActivity extends AppCompatActivity {



    // View to change background color of
    ConstraintLayout mSettingsLayout;
    SharedPreferences mSharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mSharedPreferences = getSharedPreferences("io.github.akndmr.samplepreferencefragment", MODE_PRIVATE);
        String colorSelected = mSharedPreferences.getString(Constants.PREF_COLOR, getResources().getString(R.string.pref_colors_default_value));
        if(!colorSelected.equals(Constants.COLOR_DEFAULT))
            ColorPrefUtil.changeThemeStyle(this, R.style.AppTheme2);

        setContentView(R.layout.activity_settings);

        mSettingsLayout = findViewById(R.id.cl_settings_layout);


        // Change colors of views
        switch (colorSelected){
            case Constants.COLOR_BLUE :
                ColorPrefUtil.changeBackgroundColorOfSingleView(this, mSettingsLayout, R.color.themeBluish);
                break;
            case Constants.COLOR_RED :
                ColorPrefUtil.changeBackgroundColorOfSingleView(this, mSettingsLayout, R.color.themeReddish);
                break;
            case Constants.COLOR_YELLOW :
                ColorPrefUtil.changeBackgroundColorOfSingleView(this, mSettingsLayout, R.color.themeYellowish);
                break;
            case Constants.COLOR_GRAY :
                ColorPrefUtil.changeBackgroundColorOfSingleView(this, mSettingsLayout, R.color.themeGrayish);
                break;
            default:
                ColorPrefUtil.changeBackgroundColorOfSingleView(this, mSettingsLayout, R.color.colorPrimary);
                break;
        }

        // Display the fragment as the main content.
        getSupportFragmentManager().beginTransaction()
                .replace(android.R.id.content, new SettingsFragment())
                .commit();
    }



    public static class SettingsFragment extends PreferenceFragmentCompat {

        private SharedPreferences mPreferences;
        private String sharedPrefName =
                "io.github.akndmr.samplepreferencefragment";

        @Override
        public void onCreatePreferences(Bundle savedInstanceState,
                                        String rootKey) {
            setPreferencesFromResource(R.xml.preferences, rootKey);
        }

        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);

            mPreferences = this.getActivity()
                    .getSharedPreferences(sharedPrefName, MODE_PRIVATE);

            // Get preference for color
            Preference preference =
                    this.findPreference(Constants.PREF_COLOR);



            preference.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
                @Override
                public boolean onPreferenceChange(Preference preference, Object o) {

                    String textValue = o.toString();

                    ListPreference listPreference = (ListPreference) preference;
                    int index = listPreference.findIndexOfValue(textValue);

                    CharSequence[] entries = listPreference.getEntries();

                    // If ListPreference has textValue, index will be > -1
                    if(index >= 0){
                        Toast.makeText(preference.getContext(), entries[index].toString(), Toast.LENGTH_LONG).show();

                        // Set summary to selected color/theme
                        listPreference.setSummary(entries[index].toString());

                        // Write selected color to sharedpref
                        // Use commit to be sure value is saved before recreate
                        SharedPreferences.Editor preferencesEditor =
                                mPreferences.edit();
                        preferencesEditor.putString(Constants.PREF_COLOR,
                                entries[index].toString()).commit();

                        // Recreate activity to apply changes
                        getActivity().recreate();
                    }

                    return true;
                }
            });
        }


        @Override
        public void onResume() {
            super.onResume();

            // Find preference
            Preference colorPref = findPreference(Constants.PREF_COLOR);

            // Get preference value
            String summary = mPreferences.getString(Constants.PREF_COLOR, getActivity().getResources().getString(R.string.pref_colors_default_value));

            // Set(update) preference summary
            colorPref.setSummary(summary);
            colorPref.setDefaultValue(getActivity().getResources().getStringArray(R.array.pref_color_values));
        }
    }
}
