package com.philpicinic.easybillsplit.adapters;

import android.content.Context;
import android.database.Cursor;
import android.provider.ContactsContract;
import android.support.v4.widget.CursorAdapter;
import android.support.v4.widget.SimpleCursorAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.philpicinic.easybillsplit.R;

/**
 * Created by Phil on 10/2/2014.
 */
public class NumberAdapter extends SimpleCursorAdapter {

    private LayoutInflater inflater;
    private Context context;

    public NumberAdapter(Context context, int resource_id, String[] FROM_COLUMNS,
                         int[] TO_IDS, int flags) {
        super(context, resource_id, null, FROM_COLUMNS, TO_IDS, flags );
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.context = context;
    }

//    @Override
//    public View newView(Context context, Cursor cursor, ViewGroup viewGroup) {
//        return inflater.inflate(R.layout.contact_number_item, null, false);
//    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        TextView numberText = (TextView) view.findViewById(R.id.contact_number);
        String number = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
        numberText.setText(number);
        TextView typeText = (TextView) view.findViewById(R.id.contact_type);
        String typeString = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.TYPE));
        int type = Integer.parseInt(typeString);
        typeText.setText(ContactsContract.CommonDataKinds.Phone.
                getTypeLabel(context.getResources(), type, ""));
    }
}
