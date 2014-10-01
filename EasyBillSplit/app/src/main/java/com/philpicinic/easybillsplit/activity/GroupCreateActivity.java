package com.philpicinic.easybillsplit.activity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
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

import com.philpicinic.easybillsplit.R;
import com.philpicinic.easybillsplit.contact.IPerson;
import com.philpicinic.easybillsplit.contact.TextPerson;
import com.philpicinic.easybillsplit.service.ManagerService;

import java.util.ArrayList;


public class GroupCreateActivity extends ActionBarActivity {

    private static final int EDIT_ACTION = 0;
    private static final int DELETE_ACTION = 1;
    private static final int CANCEL_ACTION = 2;

    private ArrayList<IPerson> members;
    private ArrayAdapter<IPerson> aa;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_create);
        members = ManagerService.getInstance().getMembers();
        if(members.size() == 0) {
            members.add(new TextPerson("Me", ManagerService.getInstance().getCurrentId()));
        }

        final EditText name = (EditText) findViewById(R.id.member_name);

        aa = new ArrayAdapter<IPerson>(this, android.R.layout.simple_list_item_1, members);

        Button btn = (Button) findViewById(R.id.add_member_btn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String result = name.getText().toString();
                if(result != null && result.length() > 0) {
                    members.add(new TextPerson(result, ManagerService.getInstance().getCurrentId()));
                    name.setText("");
                    aa.notifyDataSetChanged();
                }
            }
        });

        ListView membersList = (ListView) findViewById(R.id.member_list);
        membersList.setAdapter(aa);
        registerForContextMenu(membersList);

        Button continueBtn = (Button) findViewById(R.id.create_finish_btn);
        continueBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(members.size() > 0){
                    Intent intent = new Intent(getApplicationContext(), ItemCreateActivity.class);
                    startActivity(intent);
                }
            }
        });

    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo){
        super.onCreateContextMenu(menu, v, menuInfo);
        menu.add(Menu.NONE, EDIT_ACTION, Menu.NONE, "Edit");
        menu.add(Menu.NONE, DELETE_ACTION, Menu.NONE, "Delete");
        menu.add(Menu.NONE, CANCEL_ACTION, Menu.NONE, "Cancel");
    }

    @Override
    public boolean onContextItemSelected(MenuItem item){
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        switch(item.getItemId()){
            case EDIT_ACTION:
                showEditDialog(info.position);
                return true;
            case DELETE_ACTION:
                ManagerService.getInstance().deleteMember(members.get(info.position));
                members.remove(info.position);
                aa.notifyDataSetChanged();
                return true;
            default:
                return false;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.group_create, menu);
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

    public void showEditDialog(int position){
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.person_edit_layout);
        final IPerson person = members.get(position);
        final EditText nameText = (EditText) dialog.findViewById(R.id.member_name);
        nameText.setText(person.getName());

        Button submitBtn = (Button) dialog.findViewById(R.id.submit_btn);
        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                person.setName(nameText.getText().toString());
                aa.notifyDataSetChanged();
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
