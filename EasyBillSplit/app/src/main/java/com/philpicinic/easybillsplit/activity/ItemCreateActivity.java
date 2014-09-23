package com.philpicinic.easybillsplit.activity;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
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
import android.widget.Spinner;

import com.philpicinic.easybillsplit.R;
import com.philpicinic.easybillsplit.adapters.ItemAdapter;
import com.philpicinic.easybillsplit.item.BasicItem;
import com.philpicinic.easybillsplit.item.IItem;
import com.philpicinic.easybillsplit.contact.IPerson;
import com.philpicinic.easybillsplit.service.ManagerService;

import java.util.ArrayList;

public class ItemCreateActivity extends ActionBarActivity {

    private static final byte EDIT_ACTION = 0;
    private static final byte DELETE_ACTION = 1;
    private static final byte CANCEL_ACTION = 2;

    private ArrayList<IPerson> members;
    private ArrayList<IItem> items;
    private ItemAdapter itemAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_create);
        items = ManagerService.getInstance().getItems();
        members = ManagerService.getInstance().getMembers();

        final Spinner itemMemberChoice = (Spinner) findViewById(R.id.item_member_join);
        ArrayAdapter<IPerson> aa = new ArrayAdapter<IPerson>(this, android.R.layout.simple_spinner_item, members);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        itemMemberChoice.setAdapter(aa);

        final EditText nameText = (EditText) findViewById(R.id.item_name);
        final EditText priceText = (EditText) findViewById(R.id.item_price);

        ListView itemList = (ListView) findViewById(R.id.item_list);
        itemAdapter = new ItemAdapter(this, R.layout.item_bill_amt_layout, items);
        registerForContextMenu(itemList);
        itemList.setAdapter(itemAdapter);

        Button addBtn = (Button) findViewById(R.id.add_item_btn);
        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = nameText.getText().toString();
                String price = priceText.getText().toString();
                IPerson person = (IPerson) itemMemberChoice.getSelectedItem();
                if(name != null && name.length() > 0 && price != null && price.length() > 0) {
                    IItem item = new BasicItem(name, price, person);
                    items.add(item);
                    itemAdapter.notifyDataSetChanged();
                    nameText.setText("");
                    priceText.setText("");
                    priceText.clearFocus();
                }
            }
        });

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
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.item_edit_layout);
        final IItem item = ManagerService.getInstance().getItems().get(position);
        final EditText nameText = (EditText) dialog.findViewById(R.id.item_name);
        nameText.setText(item.toString());
        final Spinner memberSpinner = (Spinner) dialog.findViewById(R.id.item_member_join);
        ArrayList<IPerson> members = ManagerService.getInstance().getMembers();
        ArrayAdapter<IPerson> aa = new ArrayAdapter<IPerson>(this, android.R.layout.simple_spinner_dropdown_item, members);
        memberSpinner.setAdapter(aa);
        final EditText priceText = (EditText) dialog.findViewById(R.id.item_price);
        priceText.setText(item.getPrice().toString());
        Button submitBtn = (Button) dialog.findViewById(R.id.submit_btn);
        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.println(nameText.getText().toString());
                System.out.println(((IPerson)memberSpinner.getSelectedItem()).getId());
                System.out.println(priceText.getText().toString());
                item.setName(nameText.getText().toString());
                item.setPerson((IPerson) memberSpinner.getSelectedItem());
                item.setPrice(priceText.getText().toString());
                itemAdapter.notifyDataSetChanged();
                dialog.cancel();
            }
        });

        Button cancelBtn = (Button) dialog.findViewById(R.id.cancel_btn);
        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.cancel();
            }
        });

        dialog.show();
    }
}
