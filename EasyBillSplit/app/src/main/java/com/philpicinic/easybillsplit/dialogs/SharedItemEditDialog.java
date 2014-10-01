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
import com.philpicinic.easybillsplit.item.SharedItem;
import com.philpicinic.easybillsplit.service.ManagerService;

import java.util.ArrayList;

/**
 * Created by Phil on 9/30/2014.
 */
public class SharedItemEditDialog extends DialogFragment {

    private SharedItem item;
    private ArrayList<IPerson> editedMembers;
    private ArrayList<IPerson> members;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState){
        members = ManagerService.getInstance().getMembers();
        int t = getArguments().getInt("item_id");
        item = (SharedItem) ManagerService.getInstance().getItems().get(t);
        editedMembers = new ArrayList<IPerson>();
        for(IPerson person : item.getMembers()){
            editedMembers.add(person);
        }
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        final EditText nameText = new EditText(getActivity());
        nameText.setText(item.getName());
        nameText.setInputType(InputType.TYPE_TEXT_FLAG_CAP_WORDS);
        final EditText priceText = new EditText(getActivity());
        priceText.setText(item.getPrice().toString());
        priceText.setInputType(InputType.TYPE_NUMBER_FLAG_DECIMAL);
        LinearLayout layout = new LinearLayout(getActivity());
        layout.setOrientation(LinearLayout.VERTICAL);
        layout.addView(nameText);
        layout.addView(priceText);
        builder.setView(layout);
        builder.setMultiChoiceItems(getNameArray(), getChecked(), new DialogInterface.OnMultiChoiceClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i, boolean b) {
                if(b){
                    editedMembers.add(members.get(i));
                }else{
                    editedMembers.remove(members.get(i));
                }
            }
        });

        builder.setNegativeButton("Cancel", null);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                String name = nameText.getText().toString().trim();
                String price = priceText.getText().toString().trim();
                if(editedMembers != null && editedMembers.size() > 0 && name != null && name.length() > 0
                        && price != null && price.length() > 0){
                    item.setName(name);
                    item.setPrice(price);
                    item.setMembers(editedMembers);
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

    private boolean[] getChecked(){
        boolean[] array = new boolean[members.size()];
        for(int i = 0; i <members.size(); i++){
            array[i] = editedMembers.contains(members.get(i));
        }
        return array;
    }
}
