package io.github.akndmr.samplepreferencefragment;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.preference.PreferenceManager;
import android.util.Log;
import android.view.View;

import akndmr.github.io.colorprefutil.ColorPrefUtil;

public class MainActivity extends AppCompatActivity {


    public static final String TAG = "COLOR_LOG";

    SharedPreferences mSharedPreferences;
    private String colorSelected;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.btn_settings).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, SettingsActivity.class));
            }
        });

        /*
         *  Set preferences
         *  #readAgain indicating whether the default values should be set more than once.
         *  When false, the system sets the default values only if this method has never been called in the past
         */
        PreferenceManager.setDefaultValues(this,
                R.xml.preferences, false);

        // Get shared pref
        mSharedPreferences =
                PreferenceManager.getDefaultSharedPreferences(this);

        // Get selected color
        colorSelected = mSharedPreferences.getString(Constants.PREF_COLOR, Constants.COLOR_DEFAULT);


        // Change colors of views
        switch (colorSelected){
            case Constants.COLOR_BLUE :
                ColorPrefUtil.changeBackgroundColorOfSingleView(this, findViewById(R.id.btn_settings), R.color.themeBluish);
                ColorPrefUtil.changeTextColorOfSingleView(this, findViewById(R.id.btn_settings), R.color.textColorLight, null);
                break;
            case Constants.COLOR_RED :
                ColorPrefUtil.changeBackgroundColorOfSingleView(this, findViewById(R.id.btn_settings), R.color.themeReddish);
                ColorPrefUtil.changeTextColorOfSingleView(this, findViewById(R.id.btn_settings), R.color.textColorLight, null);
                break;
            case Constants.COLOR_YELLOW :
                ColorPrefUtil.changeBackgroundColorOfSingleView(this, findViewById(R.id.btn_settings), R.color.themeYellowish);
                ColorPrefUtil.changeTextColorOfSingleView(this, findViewById(R.id.btn_settings), R.color.textColorDark, null);
                break;
            case Constants.COLOR_GRAY :
                ColorPrefUtil.changeBackgroundColorOfSingleView(this, findViewById(R.id.btn_settings), R.color.themeGrayish);
                ColorPrefUtil.changeTextColorOfSingleView(this, findViewById(R.id.btn_settings), R.color.textColorDark, null);
                break;
            default:
                ColorPrefUtil.changeBackgroundColorOfSingleView(this, findViewById(R.id.btn_settings), R.color.colorPrimary);
                ColorPrefUtil.changeTextColorOfSingleView(this, findViewById(R.id.btn_settings), R.color.textColorLight, null);
                break;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        // Check if color has changed. If changed, recreate activity to apply colors
        // Especially if user navigates back from Settings activity
        if(!colorSelected.equals(mSharedPreferences.getString(Constants.PREF_COLOR, Constants.COLOR_DEFAULT))){

            String color = mSharedPreferences.getString(Constants.PREF_COLOR, Constants.COLOR_DEFAULT);
            Log.d(TAG, "Color has changed! Color was: " + colorSelected + " - New color is: " +color);

            recreate();
        }
    }
}
