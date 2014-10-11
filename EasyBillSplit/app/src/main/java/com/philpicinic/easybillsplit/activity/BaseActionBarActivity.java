package com.philpicinic.easybillsplit.activity;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.view.MenuItem;

import com.philpicinic.easybillsplit.R;

/**
 * Created by Phil on 10/9/2014.
 */
public abstract class BaseActionBarActivity extends ActionBarActivity {

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        switch(id){
            case android.R.id.home:
                onBackPressed();
                return true;
            case R.id.action_settings:
                Intent intent = new Intent(getApplicationContext(), SettingsActivity.class);
                startActivity(intent);
                return true;
        }
//        if (id == R.id.action_settings) {
//            return true;
//        }
        return super.onOptionsItemSelected(item);
    }
}
