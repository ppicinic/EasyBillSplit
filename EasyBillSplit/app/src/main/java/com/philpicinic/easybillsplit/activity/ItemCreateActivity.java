package com.philpicinic.easybillsplit.activity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBarActivity;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;

import com.philpicinic.easybillsplit.R;
import com.philpicinic.easybillsplit.adapters.ItemAdapter;
import com.philpicinic.easybillsplit.contact.IPerson;
import com.philpicinic.easybillsplit.dialogs.ItemEditDialog;
import com.philpicinic.easybillsplit.dialogs.SharedItemEditDialog;
import com.philpicinic.easybillsplit.fragments.SharedItemFragment;
import com.philpicinic.easybillsplit.fragments.SingleItemFragment;
import com.philpicinic.easybillsplit.item.BasicItem;
import com.philpicinic.easybillsplit.item.IItem;
import com.philpicinic.easybillsplit.item.SharedItem;
import com.philpicinic.easybillsplit.service.ManagerService;

import java.util.ArrayList;

public class ItemCreateActivity extends ActionBarActivity {

    public static final String ITEM_ID = "item_id";
    public static final String SINGLE_ITEM = "item_id";
    public static final String SHARED_ITEM = "item_id";

    private static final byte EDIT_ACTION = 0;
    private static final byte DELETE_ACTION = 1;
    private static final byte CANCEL_ACTION = 2;

    private ArrayList<IPerson> members;
    private ArrayList<IItem> items;
    private ItemAdapter itemAdapter;
    private SingleItemFragment singleItemFragment;
    private SharedItemFragment sharedItemFragment;
    private int type;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_create);

        singleItemFragment = new SingleItemFragment();
        android.support.v4.app.FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        Fragment f = fm.findFragmentByTag(SHARED_ITEM);
        if(f != null) {
            ft.remove(f);
        }
        f = fm.findFragmentByTag(SINGLE_ITEM);
        if(f != null){
            ft.remove(f);
        }
        ft.add(R.id.item_fragment, singleItemFragment, SINGLE_ITEM).commit();

        type = 0;

        sharedItemFragment = new SharedItemFragment();

        items = ManagerService.getInstance().getItems();



        ListView itemList = (ListView) findViewById(R.id.item_list);
        itemAdapter = new ItemAdapter(this, R.layout.item_bill_amt_layout, items);
        registerForContextMenu(itemList);
        itemList.setAdapter(itemAdapter);



        Button continueBtn = (Button) findViewById(R.id.item_finish_btn);
        continueBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(items.size() > 0) {
                    Intent intent = new Intent(getApplicationContext(), BillFinalActionActivity.class);
                    startActivity(intent);
                }
            }
        });

        final RadioButton singleBtn = (RadioButton) findViewById(R.id.single_radio_btn);
        singleBtn.setChecked(true);

        final RadioButton sharedBtn = (RadioButton) findViewById(R.id.shared_radio_btn);
        RadioGroup viewSwitcher = (RadioGroup) findViewById(R.id.item_type_btn);
        viewSwitcher.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                if(sharedBtn.isChecked()){
                    android.support.v4.app.FragmentManager fm = getSupportFragmentManager();
                    FragmentTransaction ft = fm.beginTransaction();
                    Fragment f = fm.findFragmentByTag(SHARED_ITEM);
                    if(f != null){
                        ft.remove(f);
                    }
                    f = fm.findFragmentByTag(SINGLE_ITEM);
                    if(f != null){
                        ft.remove(f);
                    }
                    ft.add(R.id.item_fragment, sharedItemFragment, SHARED_ITEM);
                    ft.commit();
                }else if(singleBtn.isChecked()){
                    android.support.v4.app.FragmentManager fm = getSupportFragmentManager();
                    FragmentTransaction ft = fm.beginTransaction();
                    Fragment f = fm.findFragmentByTag(SHARED_ITEM);
                    if(f != null){
                        ft.remove(f);
                    }
                    f = fm.findFragmentByTag(SINGLE_ITEM);
                    if(f != null){
                        ft.remove(f);
                    }
                    ft.add(R.id.item_fragment, singleItemFragment, SINGLE_ITEM);
                    ft.commit();
                }
            }
        });
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        menu.add(Menu.NONE, EDIT_ACTION, Menu.NONE, "Edit");
        menu.add(Menu.NONE, DELETE_ACTION, Menu.NONE, "Delete");
        menu.add(Menu.NONE, CANCEL_ACTION, Menu.NONE, "Cancel");
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        switch (item.getItemId()) {
            case EDIT_ACTION:
                // Edit item name and price in pop-up dialog
                showEditDialog(info.position);
                return true;
            case DELETE_ACTION:
                items.remove(info.position);
                itemAdapter.notifyDataSetChanged();
                return true;
            default:
                return super.onContextItemSelected(item);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.item_create, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void showEditDialog(int position){
        if(items.get(position) instanceof BasicItem) {
//            final Dialog dialog = new Dialog(this);
//            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
//            dialog.setContentView(R.layout.item_edit_layout);
//            final IItem item = ManagerService.getInstance().getItems().get(position);
//            final EditText nameText = (EditText) dialog.findViewById(R.id.item_name);
//            nameText.setText(item.toString());
//            final Spinner memberSpinner = (Spinner) dialog.findViewById(R.id.item_member_join);
//            ArrayList<IPerson> members = ManagerService.getInstance().getMembers();
//            ArrayAdapter<IPerson> aa = new ArrayAdapter<IPerson>(this, android.R.layout.simple_spinner_dropdown_item, members);
//            memberSpinner.setAdapter(aa);
//            memberSpinner.setSelection(members.indexOf(item.getPerson()));
//            final EditText priceText = (EditText) dialog.findViewById(R.id.item_price);
//            priceText.setText(item.getPrice().toString());
//            Button submitBtn = (Button) dialog.findViewById(R.id.submit_btn);
//            submitBtn.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    item.setName(nameText.getText().toString());
//                    item.setPerson((IPerson) memberSpinner.getSelectedItem());
//                    item.setPrice(priceText.getText().toString());
//                    itemAdapter.notifyDataSetChanged();
//                    dialog.cancel();
//                }
//            });
//
//            Button cancelBtn = (Button) dialog.findViewById(R.id.cancel_btn);
//            cancelBtn.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    dialog.cancel();
//                }
//            });
//
//            dialog.show();
            ItemEditDialog dialog = new ItemEditDialog();
            Bundle args = new Bundle();
            args.putInt(ITEM_ID, position);
            dialog.setArguments(args);
            dialog.show(getSupportFragmentManager().beginTransaction(), null);
        }else if(items.get(position) instanceof SharedItem){
            SharedItemEditDialog dialog = new SharedItemEditDialog();
            Bundle args = new Bundle();
            args.putInt(ITEM_ID, position);
            dialog.setArguments(args);
            dialog.show(getSupportFragmentManager().beginTransaction(), null);
        }
    }

    public void addItem(IItem item){
        items.add(item);
        itemAdapter.notifyDataSetChanged();
    }

    public void updateChosenMembers(){
        sharedItemFragment.updateMembers();
    }

    public ArrayList<IPerson> getChosenMembers(){
        return sharedItemFragment.getChosenMembers();
    }

    public void itemNotify(){
        itemAdapter.notifyDataSetChanged();
    }
}
