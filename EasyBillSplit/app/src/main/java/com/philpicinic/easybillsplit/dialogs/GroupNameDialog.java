package com.philpicinic.easybillsplit.dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.text.InputType;
import android.widget.EditText;

import com.philpicinic.easybillsplit.R;
import com.philpicinic.easybillsplit.activity.GroupSelectActivity;

/**
 * Created by Phil on 10/6/2014.
 */
public class GroupNameDialog extends DialogFragment {

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState){
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(R.string.group_name_dialog);
        final EditText nameText = new EditText(getActivity());
        nameText.setInputType(InputType.TYPE_TEXT_FLAG_CAP_WORDS);
        builder.setView(nameText);
        builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                ((GroupSelectActivity) getActivity()).createGroup();
            }
        });
        builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                ((GroupSelectActivity) getActivity()).createGroup(nameText.getText().toString());
            }
        });
        return builder.create();
    }
}
