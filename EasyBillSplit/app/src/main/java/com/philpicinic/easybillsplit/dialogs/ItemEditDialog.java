package com.philpicinic.easybillsplit.dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.text.InputType;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.philpicinic.easybillsplit.activity.ItemCreateActivity;
import com.philpicinic.easybillsplit.contact.IPerson;
import com.philpicinic.easybillsplit.item.BasicItem;
import com.philpicinic.easybillsplit.service.ManagerService;

import java.util.ArrayList;

/**
 * Created by phil on 9/20/14.
 */
public class ItemEditDialog extends DialogFragment{

    private ArrayList<IPerson> members;
    private IPerson member;
    private BasicItem item;
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState){
        members = ManagerService.getInstance().getMembers();
        int t = getArguments().getInt(ItemCreateActivity.ITEM_ID);
        item = (BasicItem) ManagerService.getInstance().getItems().get(t);
        member = item.getPerson();

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        final EditText nameText = new EditText(getActivity());
        nameText.setText(item.getName());
        nameText.setInputType(InputType.TYPE_TEXT_FLAG_CAP_WORDS);
        final EditText priceText = new EditText(getActivity());
        priceText.setText(item.getPrice().toString());
        priceText.setInputType(InputType.TYPE_CLASS_NUMBER |
                InputType.TYPE_NUMBER_FLAG_DECIMAL | InputType.TYPE_NUMBER_FLAG_SIGNED);
        LinearLayout layout = new LinearLayout(getActivity());
        layout.setOrientation(LinearLayout.VERTICAL);
        layout.addView(nameText);
        layout.addView(priceText);
        builder.setView(layout);
        builder.setSingleChoiceItems(getNameArray(), getChecked(), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                member = members.get(i);
            }
        });

        builder.setNegativeButton("Cancel", null);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                String name = nameText.getText().toString().trim();
                String price = priceText.getText().toString().trim();
                if(member != null && name != null && name.length() > 0
                        && price != null && price.length() > 0){
                    item.setName(name);
                    item.setPrice(price);
                    item.setPerson(member);
                    ((ItemCreateActivity)getActivity()).itemNotify();
                }
            }
        });
        return builder.create();
    }

    private String[] getNameArray(){
        String[] array = new String[members.size()];
        for(int i = 0; i < members.size(); i++){
            array[i] = members.get(i).toString();
        }
        return array;
    }

    private int getChecked(){
        return members.indexOf(member);
}

}
