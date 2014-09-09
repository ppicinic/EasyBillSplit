package com.philpicinic.easybillsplit.activity;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.philpicinic.easybillsplit.R;
import com.philpicinic.easybillsplit.com.philpicinic.easybillsplit.item.BasicItem;
import com.philpicinic.easybillsplit.com.philpicinic.easybillsplit.item.IItem;
import com.philpicinic.easybillsplit.contact.IPerson;

import java.util.ArrayList;
import java.util.List;

public class ItemCreateActivity extends ActionBarActivity {

    private ArrayList<IPerson> members;
    private ArrayList<IItem> items;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_create);
        items = new ArrayList<IItem>();
        members = getIntent().getParcelableArrayListExtra("group");

        final Spinner itemMemberChoice = (Spinner) findViewById(R.id.item_member_join);
        ArrayAdapter<IPerson> aa = new ArrayAdapter<IPerson>(this, android.R.layout.simple_spinner_item, members);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        itemMemberChoice.setAdapter(aa);

        final EditText nameText = (EditText) findViewById(R.id.item_name);
        final EditText priceText = (EditText) findViewById(R.id.item_price);

        ListView itemList = (ListView) findViewById(R.id.item_list);
        final ArrayAdapter<IItem> itemAdapter = new ArrayAdapter<IItem>(this, android.R.layout.simple_list_item_1, items);
        itemList.setAdapter(itemAdapter);

        Button addBtn = (Button) findViewById(R.id.add_item_btn);
        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = nameText.getText().toString();
                String price = priceText.getText().toString();
                IPerson person = (IPerson) itemMemberChoice.getSelectedItem();
                if(name != null && name.length() > 0 && price != null && price.length() > 0) {
                    IItem item = new BasicItem(name, price, person);
                    items.add(item);
                    itemAdapter.notifyDataSetChanged();
                    nameText.setText("");
                    priceText.setText("");
                    priceText.clearFocus();
                }
            }
        });
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
