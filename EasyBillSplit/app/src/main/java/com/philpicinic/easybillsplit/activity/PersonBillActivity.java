package com.philpicinic.easybillsplit.activity;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.TextView;

import com.philpicinic.easybillsplit.R;
import com.philpicinic.easybillsplit.adapters.PersonBillAdapter;
import com.philpicinic.easybillsplit.contact.IPerson;
import com.philpicinic.easybillsplit.item.IItem;
import com.philpicinic.easybillsplit.service.ManagerService;

import java.math.BigDecimal;
import java.util.ArrayList;

public class PersonBillActivity extends ActionBarActivity {

    private ArrayList<IItem> myItems;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_person_bill);

        int id = getIntent().getIntExtra(BillOutputActivity.PERSON_ID, -1);

        IPerson person = ManagerService.getInstance().getPerson(id);
        if(person != null) {
            setTitle(person.toString());
            myItems = ManagerService.getInstance().getPersonItems(person);
            ListView listView = (ListView) findViewById(R.id.item_bill_list);
            PersonBillAdapter personBillAdapter =
                    new PersonBillAdapter(this, R.layout.item_bill_amt_layout, myItems, person);
            listView.setAdapter(personBillAdapter);
            BigDecimal total = ManagerService.getInstance().calculatePersonItems(person, myItems);
            TextView textView = (TextView) findViewById(R.id.total_amt);
            textView.setText(total.toString());
        }else{
            finish();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.person_bill, menu);
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
