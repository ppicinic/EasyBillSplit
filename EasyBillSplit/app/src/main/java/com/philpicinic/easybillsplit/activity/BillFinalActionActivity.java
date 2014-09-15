package com.philpicinic.easybillsplit.activity;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.text.Editable;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.philpicinic.easybillsplit.R;
import com.philpicinic.easybillsplit.item.IItem;
import com.philpicinic.easybillsplit.service.ManagerService;

import java.math.BigDecimal;
import java.util.ArrayList;

public class BillFinalActionActivity extends ActionBarActivity {

    private ArrayList<IItem> items;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bill_final_action);

        items = ManagerService.getInstance().getItems();

        final EditText taxText = (EditText) findViewById(R.id.tax_input);

        BigDecimal total = new BigDecimal(0);
        for(IItem item : items){
            total =  total.add(item.total());
        }

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
