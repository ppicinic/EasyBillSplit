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
import com.philpicinic.easybillsplit.contact.IPerson;
import com.philpicinic.easybillsplit.service.ManagerService;

/**
 * Created by Phil on 10/1/2014.
 */
public class MemberEditDialog extends DialogFragment {

    private IPerson person;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState){
        person = ManagerService.getInstance().getMembers()
                .get(getArguments().getInt(GroupCreateActivity.PERSON_ID));
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        final EditText nameText = new EditText(getActivity());
        nameText.setInputType(InputType.TYPE_TEXT_FLAG_CAP_WORDS);
//        nameText.setHint();
        nameText.setText(person.getName());
        builder.setView(nameText);
        builder.setNegativeButton(R.string.cancel, null);
        builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                person.setName(nameText.getText().toString());
            }
        });
        return builder.create();
    }
}
