package com.philpicinic.easybillsplit.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.TextView;

import com.philpicinic.easybillsplit.R;
import com.philpicinic.easybillsplit.adapters.BillAdapter;
import com.philpicinic.easybillsplit.contact.IPerson;
import com.philpicinic.easybillsplit.item.IItem;
import com.philpicinic.easybillsplit.service.ManagerService;

import java.math.BigDecimal;
import java.util.ArrayList;

public class BillOutputActivity extends ActionBarActivity {

    public static final String PERSON_ID = "person_id";

    private ArrayList<IItem> items;
    private ArrayList<IPerson> members;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bill_output);

        items = ManagerService.getInstance().getItems();
        members = ManagerService.getInstance().getMembers();

        BillAdapter billAdapter = new BillAdapter(this, R.layout.person_bill_amt_layout, members);

        ListView listView = (ListView) findViewById(R.id.person_bill_amt);
        listView.setAdapter(billAdapter);

        BigDecimal totalAmount = ManagerService.getInstance().getTotalCost();
        TextView totalText = (TextView) findViewById(R.id.total_amt);
        totalText.setText(totalAmount.toString());
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.bill_output, menu);
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

    public void checkPersonBill(int id){
        Intent intent = new Intent(this, PersonBillActivity.class);
        intent.putExtra(PERSON_ID, id);
        startActivity(intent);
    }
}
