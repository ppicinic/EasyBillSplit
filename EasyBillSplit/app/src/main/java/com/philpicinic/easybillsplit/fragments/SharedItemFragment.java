package com.philpicinic.easybillsplit.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.philpicinic.easybillsplit.R;
import com.philpicinic.easybillsplit.activity.ItemCreateActivity;
import com.philpicinic.easybillsplit.contact.IPerson;
import com.philpicinic.easybillsplit.item.BasicItem;
import com.philpicinic.easybillsplit.item.IItem;
import com.philpicinic.easybillsplit.service.ManagerService;

import java.util.ArrayList;

/**
 * Created by Phil on 9/24/2014.
 */
public class SharedItemFragment extends Fragment {

    private ArrayList<IPerson> members;
    private ItemCreateActivity activity;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstance){
        View view = inflater.inflate(R.layout.single_item_layout, null);
        members = ManagerService.getInstance().getMembers();
        activity = (ItemCreateActivity) getActivity();
        final Spinner itemMemberChoice = (Spinner) view.findViewById(R.id.item_member_join);
        ArrayAdapter<IPerson> aa = new ArrayAdapter<IPerson>(getActivity(), android.R.layout.simple_spinner_item, members);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        itemMemberChoice.setAdapter(aa);

        final EditText nameText = (EditText) view.findViewById(R.id.item_name);
        final EditText priceText = (EditText) view.findViewById(R.id.item_price);

        Button addBtn = (Button) view.findViewById(R.id.add_item_btn);
        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = nameText.getText().toString();
                String price = priceText.getText().toString();
                IPerson person = (IPerson) itemMemberChoice.getSelectedItem();
                if(name != null && name.length() > 0 && price != null && price.length() > 0) {
                    IItem item = new BasicItem(name, price, person);
                    activity.addItem(item);
                    nameText.setText("");
                    priceText.setText("");
                    priceText.clearFocus();
                }
            }
        });

        return view;
    }
}
