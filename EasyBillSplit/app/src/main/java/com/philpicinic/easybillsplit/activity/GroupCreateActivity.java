package com.philpicinic.easybillsplit.activity;

import android.support.annotation.LayoutRes;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.philpicinic.easybillsplit.R;

import java.util.ArrayList;


public class GroupCreateActivity extends ActionBarActivity {

    private ArrayList<String> members;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_create);
        members = new ArrayList<String>();

        final EditText name = (EditText) findViewById(R.id.member_name);

        final ArrayAdapter<String> aa = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, members);

        Button btn = (Button) findViewById(R.id.add_member_btn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                String result = "phil";
                String result = name.getText().toString();
                if(result != null && result.length() > 0) {
                    members.add(result);
                    name.setText("");
                    aa.notifyDataSetChanged();
                }
            }
        });

        ListView membersList = (ListView) findViewById(R.id.member_list);
        membersList.setAdapter(aa);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.group_create, menu);
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
