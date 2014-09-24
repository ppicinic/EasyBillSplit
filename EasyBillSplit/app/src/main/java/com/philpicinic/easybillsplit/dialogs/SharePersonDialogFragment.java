package com.philpicinic.easybillsplit.dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.philpicinic.easybillsplit.R;
import com.philpicinic.easybillsplit.activity.ItemCreateActivity;
import com.philpicinic.easybillsplit.contact.IPerson;
import com.philpicinic.easybillsplit.service.ManagerService;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Phil on 9/24/2014.
 */
public class SharePersonDialogFragment extends DialogFragment {

    private ArrayList<IPerson> members;
    private ArrayList<IPerson> membersChosen;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState){
        members = ManagerService.getInstance().getMembers();
        membersChosen = ((ItemCreateActivity) getActivity()).getChosenMembers();
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMultiChoiceItems(getNameArray(), getChecked(), new DialogInterface.OnMultiChoiceClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i, boolean b) {
                if(b){
                    membersChosen.add(members.get(i));
                }else{
                    membersChosen.remove(members.get(i));
                }
            }
        });
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                ((ItemCreateActivity) getActivity()).updateChosenMembers();
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
            array[i] = membersChosen.contains(members.get(i));
        }
        return array;
    }
}
