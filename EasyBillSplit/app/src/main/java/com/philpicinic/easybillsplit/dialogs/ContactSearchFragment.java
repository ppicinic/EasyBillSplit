package com.philpicinic.easybillsplit.dialogs;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v4.widget.SimpleCursorAdapter;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;

import com.philpicinic.easybillsplit.R;

/**
 * Created by Phil on 10/1/2014.
 */
public class ContactSearchFragment extends DialogFragment implements
        LoaderManager.LoaderCallbacks<Cursor>, AdapterView.OnItemClickListener{

    @SuppressLint("InlinedApi")
    private final static String[] FROM_COLUMNS = {
            Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB ?
                    ContactsContract.Contacts.DISPLAY_NAME_PRIMARY :
                    ContactsContract.Contacts.DISPLAY_NAME
    };

    public static final int[] TO_IDS = {
            android.R.id.text1
    };

    @SuppressLint("InlinedApi")
    private static final String[] PROJECTION =
            {
                    ContactsContract.Contacts._ID,
                    ContactsContract.Contacts.LOOKUP_KEY,
                    Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB ?
                            ContactsContract.Contacts.DISPLAY_NAME_PRIMARY :
                            ContactsContract.Contacts.DISPLAY_NAME
            };

    private static final int CONTACT_ID_INDEX = 0;
    private static final int LOOKUP_KEY_INDEX = 1;

    @SuppressLint("InlinedApi")
    private static final String SELECTION =
                    ContactsContract.Contacts.HAS_PHONE_NUMBER + ">0";


    private long mContactId;
    private String mContactKey;
    private Uri mContactUri;
    private SimpleCursorAdapter mCursorAdapter;
    private ListView mContactList;
    private View v;
    private EditText searchBar;
    private String mSearchString;
    private String[] mSelectionArgs = { ">0" };
    private ContactSearchFragment instance;

    public ContactSearchFragment() {}

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState){
        mSearchString = "";
        instance = this;
        v = View.inflate(getActivity(), R.layout.contact_list, null);
        searchBar = (EditText) v.findViewById(R.id.search_bar);
        searchBar.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
                mSearchString = searchBar.getText().toString();
                initLoad();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setView(v);
        return builder.create();
    }

    private void initLoad(){
        getLoaderManager().restartLoader(0, null, this);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState){
        super.onActivityCreated(savedInstanceState);
        mContactList = (ListView) v.findViewById(R.id.contact_list_view);
        mCursorAdapter = new SimpleCursorAdapter(
                getActivity(),
                R.layout.contact_item,
                null,
                FROM_COLUMNS,
                TO_IDS,
                0);
        mContactList.setAdapter(mCursorAdapter);

//        mContactList.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                System.out.println("test");
//            }
//        });
//        mContactList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//                System.out.println("happens");
//            }
//        });
        mContactList.setOnItemClickListener(this);
        getLoaderManager().initLoader(0, null, this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View item, int position, long rowID){
        Cursor cursor = mCursorAdapter.getCursor();
        cursor.moveToPosition(position);
        mContactId = cursor.getLong(CONTACT_ID_INDEX);
        mContactKey = cursor.getString(LOOKUP_KEY_INDEX);
        String name = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
        mContactUri = ContactsContract.Contacts.getLookupUri(mContactId, mContactKey);
        Bundle args = new Bundle();
        args.putLong("ID", mContactId);
        args.putString("NAME", name);
        ContactNumberSearchFragment newDialog = new ContactNumberSearchFragment();
        newDialog.setArguments(args);
        getDialog().cancel();
        newDialog.show(getFragmentManager().beginTransaction(), null);

    }

    @Override
    public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
        Uri contentUri;
        if(mSearchString.length() == 0){
            contentUri = ContactsContract.Contacts.CONTENT_URI;
        }else {
            contentUri = Uri.withAppendedPath(
                    ContactsContract.Contacts.CONTENT_FILTER_URI,
                    Uri.encode(mSearchString)
            );
        }
        return new CursorLoader(
                getActivity(),
                contentUri,
                PROJECTION,
                SELECTION,
                null,
                PROJECTION[2] + " ASC"
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
