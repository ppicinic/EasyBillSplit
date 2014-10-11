package com.philpicinic.easybillsplit.activity;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;

import com.philpicinic.easybillsplit.R;
import com.philpicinic.easybillsplit.service.ManagerService;

import java.math.BigDecimal;

public class SimpleSplitter extends BaseActionBarActivity {

    private boolean taxSet;
    private int number;
    private EditText numberPersons;
    private EditText subTotal;
    private EditText taxAmount;
    private EditText tipAmount;
    private TextView totalText;
    private TextView fullTotalText;
    private BigDecimal subTotalPrice;
    private BigDecimal taxPrice;
    private BigDecimal tipPercent;
    private BigDecimal totalPrice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_simple_splitter);

        taxSet = false;
        numberPersons = (EditText) findViewById(R.id.number_of_persons);
        subTotal = (EditText) findViewById(R.id.subtotal_amt);
        taxAmount = (EditText) findViewById(R.id.tax_amt);
        tipAmount = (EditText) findViewById(R.id.tip_amt);
        String tipSetting = PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).getString("PREF_TIP_AMOUNT", "15");
        tipPercent = new BigDecimal(tipSetting).divide(new BigDecimal(100), 4, BigDecimal.ROUND_HALF_UP);
        tipAmount.setText(tipSetting);
        totalText = (TextView) findViewById(R.id.person_total);
        fullTotalText = (TextView) findViewById(R.id.total_bill);

        number = 2;
        subTotalPrice = new BigDecimal("0.00");
        taxPrice = new BigDecimal("0.00");



        numberPersons.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
                String amount = numberPersons.getText().toString().trim();
                if(amount.length() > 0) {
                    number = Integer.parseInt(numberPersons.getText().toString());
                    update();
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        subTotal.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
                String amount = subTotal.getText().toString().trim();
                if(amount.length() > 0) {
                    subTotalPrice = new BigDecimal(subTotal.getText().toString());
//                        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
//                        BigDecimal taxRate = new BigDecimal(pref.getString("PREF_TAX_AMOUNT", "8.875"));
//                        taxRate = taxRate.divide(new BigDecimal("100"), 5, BigDecimal.ROUND_HALF_UP);
//                        taxPrice = subTotalPrice.multiply(taxRate);
//                        taxPrice = taxPrice.setScale(2, BigDecimal.ROUND_HALF_UP);
//                        Editable text = taxAmount.getText();
//                    text.
//                        text.replace(0, text.length() -1, taxPrice.toString());
////                        taxAmount.setText(taxPrice.toString());

                    update();
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        taxAmount.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
                System.out.println("happens");
                if(taxAmount.getText().toString().trim().length() > 0) {
                    taxSet = true;
                    taxPrice = new BigDecimal(taxAmount.getText().toString());
                    update();
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        tipAmount.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
                if(tipAmount.getText().toString().trim().length() > 0) {
                    tipPercent = new BigDecimal(tipAmount.getText().toString());
                    tipPercent = tipPercent.divide(new BigDecimal(100), 4, BigDecimal.ROUND_HALF_UP);
                    update();
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

    }

    private void update(){
        BigDecimal output = subTotalPrice;
        output = output.add(taxPrice);
        output = output.add(subTotalPrice.multiply(tipPercent));
        totalPrice = output.divide(new BigDecimal(number), 4, BigDecimal.ROUND_HALF_UP);
        output = output.setScale(2, BigDecimal.ROUND_HALF_UP);
        fullTotalText.setText(output.toString());
        totalPrice = totalPrice.setScale(2, BigDecimal.ROUND_HALF_UP);
        totalText.setText(totalPrice.toString());
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.simple_splitter, menu);
        return true;
    }

}
