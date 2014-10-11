package com.philpicinic.easybillsplit.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.view.Menu;
import android.widget.ListView;
import android.widget.TextView;

import com.philpicinic.easybillsplit.R;
import com.philpicinic.easybillsplit.adapters.PersonBillAdapter;
import com.philpicinic.easybillsplit.contact.IPerson;
import com.philpicinic.easybillsplit.fragments.SubtotalFragment;
import com.philpicinic.easybillsplit.fragments.TaxFinalFragment;
import com.philpicinic.easybillsplit.fragments.TipFinalFragment;
import com.philpicinic.easybillsplit.item.IItem;
import com.philpicinic.easybillsplit.service.ManagerService;

import java.math.BigDecimal;
import java.util.ArrayList;

public class PersonBillActivity extends BaseActionBarActivity {

    private ArrayList<IItem> myItems;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_person_bill);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

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

        boolean taxIncluded = ManagerService.getInstance().isTaxIncluded();
        boolean tipIncluded = ManagerService.getInstance().isTipIncluded();
        FragmentManager fm = getSupportFragmentManager();
        SubtotalFragment subtotalFragment = (SubtotalFragment)
                fm.findFragmentById(R.id.subtotal_fragment);
        TaxFinalFragment taxFinalFragment = (TaxFinalFragment)
                fm.findFragmentById(R.id.tax_final_fragment);
        TipFinalFragment tipFinalFragment = (TipFinalFragment)
                fm.findFragmentById(R.id.tip_final_fragment);
        FragmentTransaction ft = fm.beginTransaction();

        TextView subTotalText = (TextView) findViewById(R.id.sub_total_amt);
        TextView taxText = (TextView) findViewById(R.id.tax_amt);
        TextView tipText = (TextView) findViewById(R.id.tip_amt);
        subTotalText.setText(ManagerService.getInstance().calculateSubTotal(person).toString());
        taxText.setText(ManagerService.getInstance().calculateTax(person).toString());
        tipText.setText(ManagerService.getInstance().calculateTip(person).toString());
        if(!taxIncluded && !tipIncluded && !subtotalFragment.isHidden()){
            ft.hide(subtotalFragment);
        }
        if(!taxIncluded && !taxFinalFragment.isHidden()){
            ft.hide(taxFinalFragment);
        }
        if(!tipIncluded && !tipFinalFragment.isHidden()){
            ft.hide(tipFinalFragment);
        }
        ft.commit();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.person_bill, menu);
        return true;
    }
}
