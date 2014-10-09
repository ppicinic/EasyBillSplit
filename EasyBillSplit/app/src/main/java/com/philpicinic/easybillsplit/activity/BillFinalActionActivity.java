package com.philpicinic.easybillsplit.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckedTextView;
import android.widget.EditText;

import com.philpicinic.easybillsplit.R;
import com.philpicinic.easybillsplit.fragments.TaxFragment;
import com.philpicinic.easybillsplit.fragments.TipFragment;
import com.philpicinic.easybillsplit.item.IItem;
import com.philpicinic.easybillsplit.service.ManagerService;

import java.math.BigDecimal;
import java.util.ArrayList;

public class BillFinalActionActivity extends BaseActionBarActivity {

    private ArrayList<IItem> items;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bill_final_action);

        items = ManagerService.getInstance().getItems();

        final EditText taxText = (EditText) findViewById(R.id.tax_input);
        final EditText tipText = (EditText) findViewById(R.id.tip_input);

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        boolean taxCheck = prefs.getBoolean("PREF_TAX_CHECK", true);
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        final TaxFragment taxFragment = (TaxFragment) fm.findFragmentById(R.id.tax_fragment);
        if(!taxCheck && !taxFragment.isHidden()){
            ft.hide(taxFragment);
        }else if(taxCheck && taxFragment.isHidden()){
            ft.show(taxFragment);
        }
        final CheckedTextView includeTax = (CheckedTextView) findViewById(R.id.include_tax);
        includeTax.setChecked(taxCheck);
        includeTax.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                includeTax.setChecked(!includeTax.isChecked());
                if(includeTax.isChecked() && taxFragment.isHidden()){
                    getSupportFragmentManager().beginTransaction().show(taxFragment).commit();
                }else if(!includeTax.isChecked() && !taxFragment.isHidden()){
                    getSupportFragmentManager().beginTransaction().hide(taxFragment).commit();
                }
            }
        });

        boolean tipCheck = prefs.getBoolean("PREF_TIP_CHECK", true);
        final TipFragment tipFragment = (TipFragment) fm.findFragmentById(R.id.tip_fragment);
        if(!tipCheck && !tipFragment.isHidden()){
            ft.hide(tipFragment);
        }else if(tipCheck && tipFragment.isHidden()){
            ft.show(tipFragment);
        }
        ft.commit();
        final CheckedTextView includeTip = (CheckedTextView) findViewById(R.id.include_tip);
        includeTip.setChecked(tipCheck);
        includeTip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                includeTip.setChecked(!includeTip.isChecked());
                if(includeTip.isChecked() && tipFragment.isHidden()){
                    getSupportFragmentManager().beginTransaction().show(tipFragment).commit();
                }else if(!includeTip.isChecked() && !tipFragment.isHidden()){
                    getSupportFragmentManager().beginTransaction().hide(tipFragment).commit();
                }
            }
        });


        BigDecimal total = new BigDecimal(0);
        for(IItem item : items){
            total =  total.add(item.total());
        }

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        BigDecimal taxRate = new BigDecimal("8.875");
        taxRate = taxRate.divide(new BigDecimal("100"));
        BigDecimal tax = total.multiply(taxRate);
        tax = tax.setScale(2, BigDecimal.ROUND_HALF_EVEN);

        taxText.setText(tax.toString());

        Button finishBtn = (Button) findViewById(R.id.final_btn);
        finishBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplication(), BillOutputActivity.class);
                ManagerService.getInstance().setTaxAmt(new BigDecimal(taxText.getText().toString()));
                ManagerService.getInstance().setTipRate(new BigDecimal(tipText.getText().toString()));
                startActivity(intent);
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.bill_final_action, menu);
        return true;
    }
}
