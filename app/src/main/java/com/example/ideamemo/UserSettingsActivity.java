package com.example.ideamemo;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Switch;

import androidx.appcompat.widget.Toolbar;

public class UserSettingsActivity extends BaseActivity{
    private Switch nightMode;
    private SharedPreferences sharedPreferences;
    private Boolean night_change;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.preference_layout);

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        Intent intent = getIntent();
        /*
        if(intent.getExtras() != null) night_change = intent.getBooleanExtra("night_change", false);
        else night_change = false;
         */
        initView();

        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        myToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setAction("NIGHT_SWITCH");
                sendBroadcast(intent);
                finish();
            }
        });
//        if(isNightMode()) myToolbar.setNavigationIcon(getDrawable(R.drawable.ic_settings_white_24dp));
//        else myToolbar.setNavigationIcon(getDrawable(R.drawable.ic_settings_black_24dp));
    }

    @Override
    protected void needRefresh() {
        Log.d(TAG, "needRefresh: UserSettings");
    }

    public void initView(){
        nightMode = findViewById(R.id.nightMode);
        nightMode.setChecked(sharedPreferences.getBoolean("nightMode", false));
        nightMode.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                setNightModePref(isChecked);
                setSelfNightMode();

            }
        });
    }

    private void setNightModePref(boolean night){
        //??????nightMode switch??????pref??????nightMode
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("nightMode", night);
        editor.commit();
    }

    private void setSelfNightMode(){
        //????????????????????????activity

        super.setNightMode();
        Intent intent = new Intent(this, UserSettingsActivity.class);
        //intent.putExtra("night_change", !night_change); //??????????????????????????????????????????????????????MainActivity???

        startActivity(intent);
        finish();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN){
            Intent intent = new Intent();
            intent.setAction("NIGHT_SWITCH");
            sendBroadcast(intent);
            finish();
            return true;
        }

        return super.onKeyDown(keyCode, event);
    }
}
