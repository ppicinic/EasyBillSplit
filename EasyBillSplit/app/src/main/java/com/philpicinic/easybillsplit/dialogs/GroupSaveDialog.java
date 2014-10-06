package com.philpicinic.easybillsplit.dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.text.InputType;
import android.widget.EditText;

import com.philpicinic.easybillsplit.R;
import com.philpicinic.easybillsplit.activity.GroupCreateActivity;
import com.philpicinic.easybillsplit.activity.GroupSelectActivity;

/**
 * Created by Phil on 10/6/2014.
 */
public class GroupSaveDialog extends DialogFragment {

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState){
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(getArguments().getString(GroupSelectActivity.GROUP_NAME));
        builder.setMessage(R.string.group_save);
        builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                ((GroupCreateActivity)getActivity()).continueToItems();
            }
        });
        builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                ((GroupCreateActivity)getActivity()).saveGroup();
            }
        });
        return builder.create();
    }
}
