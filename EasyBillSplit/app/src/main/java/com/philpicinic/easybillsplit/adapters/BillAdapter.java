package com.philpicinic.easybillsplit.adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.philpicinic.easybillsplit.R;
import com.philpicinic.easybillsplit.activity.BillFinalActionActivity;
import com.philpicinic.easybillsplit.activity.BillOutputActivity;
import com.philpicinic.easybillsplit.activity.PersonBillActivity;
import com.philpicinic.easybillsplit.contact.IPerson;
import com.philpicinic.easybillsplit.service.ManagerService;

import java.math.BigDecimal;
import java.util.ArrayList;

/**
 * Created by Phil on 9/16/2014.
 */
public class BillAdapter extends ArrayAdapter<IPerson> {

    private Context context;
    private int layoutResourceId;
    private ArrayList<IPerson> data = null;

    public BillAdapter(Context context, int layoutResourceId, ArrayList<IPerson> data){
        super(context, layoutResourceId, data);
        this.layoutResourceId = layoutResourceId;
        this.context = context;
        this.data = data;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        View row = convertView;

        if(row == null){
            LayoutInflater inflater = ((Activity)context).getLayoutInflater();
            row = inflater.inflate(layoutResourceId, parent, false);
        }
        final IPerson person = data.get(position);
        TextView text = (TextView) row.findViewById(R.id.person_name);
        text.setText(person.toString());
        text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                BillOutputActivity activity = (BillOutputActivity) getContext();
                activity.checkPersonBill(person.getId());
            }
        });

        BigDecimal total = ManagerService.getInstance().calculatePerson(data.get(position));
        TextView amtText = (TextView) row.findViewById(R.id.person_total_amt);
        amtText.setText("$" + total.toString());
        return row;
    }
}
