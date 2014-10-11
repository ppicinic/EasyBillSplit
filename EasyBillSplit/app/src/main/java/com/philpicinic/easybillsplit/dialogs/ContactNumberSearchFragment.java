package com.philpicinic.easybillsplit.dialogs;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v4.widget.CursorAdapter;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;

import com.philpicinic.easybillsplit.R;
import com.philpicinic.easybillsplit.activity.GroupCreateActivity;
import com.philpicinic.easybillsplit.adapters.NumberAdapter;

/**
 * Created by Phil on 10/1/2014.
 */
public class ContactNumberSearchFragment extends DialogFragment implements
        LoaderManager.LoaderCallbacks<Cursor>, AdapterView.OnItemClickListener{

    @SuppressLint("InlinedApi")
    private final static String[] FROM_COLUMNS = {
            ContactsContract.CommonDataKinds.Phone.NUMBER,
            ContactsContract.CommonDataKinds.Phone.TYPE
    };

    public static final int[] TO_IDS = {
            R.id.contact_number,
            R.id.contact_type
    };

    @SuppressLint("InlinedApi")
    private static final String[] PROJECTION =
            {
                    ContactsContract.CommonDataKinds.Phone.NUMBER,
                    ContactsContract.CommonDataKinds.Phone.TYPE
            };

    private static final int CONTACT_ID_INDEX = 0;
    private static final int LOOKUP_KEY_INDEX = 1;

    @SuppressLint("InlinedApi")
    private static final String SELECTION =
            ContactsContract.Contacts.HAS_PHONE_NUMBER + ">0";


    private long mContactId;
    private String mContactKey;
    private Uri mContactUri;
    private CursorAdapter mCursorAdapter;
    private ListView mContactList;
    private View v;
    private EditText searchBar;
    private String mSearchString;
    private String[] mSelectionArgs = { ">0" };
    private long id;
    private String name;

    public ContactNumberSearchFragment() {}

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState){
        v = View.inflate(getActivity(), R.layout.contact_number_list, null);
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setView(v);
        return builder.create();
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState){
        super.onActivityCreated(savedInstanceState);
        id = getArguments().getLong("ID");
        name = getArguments().getString("NAME");
//        key = getArguments().getString("KEY");
        mContactList = (ListView) v.findViewById(R.id.contact_list_view);
        mCursorAdapter = new NumberAdapter(
                getActivity(),
                R.layout.contact_number_item,
                FROM_COLUMNS,
                TO_IDS,
                0);
        mContactList.setAdapter(mCursorAdapter);

        mContactList.setOnItemClickListener(this);
        getLoaderManager().initLoader(0, null, this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View item, int position, long rowID){
        Cursor cursor = mCursorAdapter.getCursor();
        cursor.moveToPosition(position);
        int number_id = cursor.getInt(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone._ID));
        GroupCreateActivity activity = (GroupCreateActivity) getActivity();
        activity.addContactMember(id, number_id, name);
        getDialog().cancel();
    }

    @Override
    public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
        Uri contentUri = ContactsContract.CommonDataKinds.Phone.CONTENT_URI;
        return new CursorLoader(
                getActivity(),
                contentUri,
                null,
                ContactsContract.CommonDataKinds.Phone.CONTACT_ID + "=" + id,
                null,
                null
        );
    }

    @Override
    public void onLoadFinished(Loader<Cursor> cursorLoader, Cursor cursor) {
        mCursorAdapter.swapCursor(cursor);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> cursorLoader) {
        mCursorAdapter.swapCursor(null);
    }
}
