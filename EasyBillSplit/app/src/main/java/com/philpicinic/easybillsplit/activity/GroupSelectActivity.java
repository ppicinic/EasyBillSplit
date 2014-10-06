package com.philpicinic.easybillsplit.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.philpicinic.easybillsplit.R;
import com.philpicinic.easybillsplit.dialogs.GroupNameDialog;


public class GroupSelectActivity extends ActionBarActivity {

    public static final String HAS_NAME = "HAS_NAME";
    public static final String GROUP_NAME = "GROUP_NAME";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_select);
        setTitle(R.string.group_select_activity_name);

        Button btn = (Button)findViewById(R.id.create_group_btn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                GroupNameDialog dialog = new GroupNameDialog();
                dialog.show(getSupportFragmentManager().beginTransaction(), null);
            }
        });
    }

    public void createGroup(){
        Bundle args = new Bundle();
        args.putBoolean(HAS_NAME, false);
        Intent intent = new Intent(getApplicationContext(), GroupCreateActivity.class);
        intent.putExtra("BUNDLE", args);
        startActivity(intent);
    }

    public void createGroup(String name){
        Bundle args = new Bundle();
        args.putBoolean(HAS_NAME, true);
        args.putString(GROUP_NAME, name);
        Intent intent = new Intent(getApplicationContext(), GroupCreateActivity.class);
        intent.putExtra("BUNDLE", args);
        startActivity(intent);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.group_select, menu);
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
