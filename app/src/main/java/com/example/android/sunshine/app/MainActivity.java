package com.example.android.sunshine.app;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;


public class MainActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new ForecastFragment())
                    .commit();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            Intent intent = new Intent(this, SettingsActivity.class);
            startActivity(intent);
            return true;
        } else if (id == R.id.action_show_pref_loc) {
            displayUsersPreferredLocation();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void displayUsersPreferredLocation() {
        String zipCode = getUserPreferredZipCode();
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(buildZipcodeUri(zipCode));
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        } else {
            Toast.makeText(this, "No suitable mapping application is installed", Toast.LENGTH_SHORT).show();
        }
    }

    private Uri buildZipcodeUri(String zipcode) {
        final String BASE_URI = "geo:0,0?";
        Uri uri = Uri.parse(BASE_URI).buildUpon()
                .appendQueryParameter("q", zipcode)
                .build();
        return uri;
    }

    private String getUserPreferredZipCode() {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        String zipCode = preferences.getString(this.getString(R.string.pref_location_key), "DL5 5PU");
        return zipCode;
    }
}
