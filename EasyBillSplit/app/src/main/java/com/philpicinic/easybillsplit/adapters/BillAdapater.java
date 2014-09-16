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

import java.util.ArrayList;

/**
 * Created by Phil on 9/16/2014.
 */
public class BillAdapater extends ArrayAdapter<IPerson> {

    private Context context;
    private int layoutResourceId;
    private ArrayList<IPerson> data = null;

    public BillAdapater(Context context, int layoutResourceId, ArrayList<IPerson> data){
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
        IPerson person = data.get(position);
        TextView text = (TextView) row.findViewById(R.id.person_name);
        text.setText(person.toString());
        return row;
    }
}
