package com.philpicinic.easybillsplit.adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.philpicinic.easybillsplit.R;
import com.philpicinic.easybillsplit.contact.IPerson;
import com.philpicinic.easybillsplit.item.IItem;
import com.philpicinic.easybillsplit.service.ManagerService;

import org.w3c.dom.Text;

import java.math.BigDecimal;
import java.util.ArrayList;

/**
 * Created by Phil on 9/17/2014.
 */
public class PersonBillAdapter extends ArrayAdapter<IItem> {

    private Context context;
    private int layoutResourceId;
    private ArrayList<IItem> data = null;
    private IPerson person;

    public PersonBillAdapter(Context context, int layoutResourceId, ArrayList<IItem> data, IPerson person){
        super(context, layoutResourceId, data);
        this.layoutResourceId = layoutResourceId;
        this.context = context;
        this.data = data;
        this.person = person;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        View row = convertView;

        if(row == null){
            LayoutInflater inflater = ((Activity)context).getLayoutInflater();
            row = inflater.inflate(layoutResourceId, parent, false);
        }
        IItem item = data.get(position);
        TextView text = (TextView) row.findViewById(R.id.item_name);
        text.setText(item.toString());

        TextView priceText = (TextView) row.findViewById(R.id.item_total_amt);
        BigDecimal amount = ManagerService.getInstance().calculateItemForPerson(item, person);
        priceText.setText(amount.toString());
        return row;
    }
}
