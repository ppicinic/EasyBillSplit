package com.philpicinic.easybillsplit.adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.philpicinic.easybillsplit.R;
import com.philpicinic.easybillsplit.item.IItem;

import java.util.ArrayList;

/**
 * Created by Phil on 9/18/2014.
 */
public class ItemAdapter extends ArrayAdapter<IItem> {

    private Context context;
    private int layoutResourceId;
    private ArrayList<IItem> data = null;

    public ItemAdapter(Context context, int layoutResourceId, ArrayList<IItem> data){
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
        IItem item = data.get(position);
        TextView text = (TextView) row.findViewById(R.id.item_name);
        text.setText(item.toString());

        TextView priceText = (TextView) row.findViewById(R.id.item_total_amt);
        priceText.setText(item.total().toString());
        row.setLongClickable(true);
        return row;
    }
}
