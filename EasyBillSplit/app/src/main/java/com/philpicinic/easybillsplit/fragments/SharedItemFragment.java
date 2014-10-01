package com.philpicinic.easybillsplit.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.philpicinic.easybillsplit.R;
import com.philpicinic.easybillsplit.activity.ItemCreateActivity;
import com.philpicinic.easybillsplit.contact.IPerson;
import com.philpicinic.easybillsplit.dialogs.SharePersonDialogFragment;
import com.philpicinic.easybillsplit.item.IItem;
import com.philpicinic.easybillsplit.item.SharedItem;
import com.philpicinic.easybillsplit.service.ManagerService;

import java.util.ArrayList;

/**
 * Created by Phil on 9/24/2014.
 */
public class SharedItemFragment extends Fragment {

    private ArrayList<IPerson> members;
    private ArrayList<IPerson> membersChosen;
    private ItemCreateActivity activity;
    private TextView memberDropDown;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstance){
        View view = inflater.inflate(R.layout.shared_item_layout, null);
        membersChosen = new ArrayList<IPerson>();
        members = ManagerService.getInstance().getMembers();
        activity = (ItemCreateActivity) getActivity();

        memberDropDown = (TextView) view.findViewById(R.id.person_dropdownlist);
        memberDropDown.setText(getString(R.string.none_selected));
        memberDropDown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharePersonDialogFragment dialog = new SharePersonDialogFragment();
                dialog.show(activity.getSupportFragmentManager(), null);
            }
        });

        final EditText nameText = (EditText) view.findViewById(R.id.item_name);
        final EditText priceText = (EditText) view.findViewById(R.id.item_price);

        Button addBtn = (Button) view.findViewById(R.id.add_item_btn);
        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = nameText.getText().toString();
                String price = priceText.getText().toString();
                if(name != null && name.length() > 0 && price != null && price.length() > 0 && membersChosen.size() > 0) {
                    IItem item = new SharedItem(name, price, membersChosen);
                    activity.addItem(item);
                    nameText.setText("");
                    membersChosen = new ArrayList<IPerson>();
                    updateMembers();
                    priceText.setText("");
                    priceText.clearFocus();
                }
            }
        });

        return view;
    }

    public void updateMembers(){
        if(membersChosen.size() > 0) {
            StringBuilder sb = new StringBuilder();
            for (IPerson person : membersChosen) {
                sb.append(person.toString());
                sb.append(", ");
            }
            sb.delete(sb.length() - 2, sb.length());
            memberDropDown.setText(sb.toString());
        }else{
            memberDropDown.setText(getString(R.string.none_selected));
        }
    }

    public ArrayList<IPerson> getChosenMembers(){
        return membersChosen;
    }
}
