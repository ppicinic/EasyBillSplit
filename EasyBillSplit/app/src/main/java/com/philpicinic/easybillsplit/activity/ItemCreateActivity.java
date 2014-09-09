package com.philpicinic.easybillsplit.activity;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.philpicinic.easybillsplit.R;
import com.philpicinic.easybillsplit.contact.IPerson;

import java.util.ArrayList;

public class ItemCreateActivity extends ActionBarActivity {

    private ArrayList<IPerson> members;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_create);
        members = getIntent().getParcelableArrayListExtra("group");

        Spinner itemMemberChoice = (Spinner) findViewById(R.id.item_member_join);
        ArrayAdapter<IPerson> aa = new ArrayAdapter<IPerson>(this, android.R.layout.simple_spinner_item, members);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        itemMemberChoice.setAdapter(aa);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.item_create, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
