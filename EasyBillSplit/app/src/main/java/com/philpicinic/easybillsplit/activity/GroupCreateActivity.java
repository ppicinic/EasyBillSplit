package com.philpicinic.easybillsplit.activity;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.philpicinic.easybillsplit.R;
import com.philpicinic.easybillsplit.contact.ContactPerson;
import com.philpicinic.easybillsplit.contact.IPerson;
import com.philpicinic.easybillsplit.contact.TextPerson;
import com.philpicinic.easybillsplit.dao.DaoMaster;
import com.philpicinic.easybillsplit.dao.DaoSession;
import com.philpicinic.easybillsplit.dao.User;
import com.philpicinic.easybillsplit.dao.UserDao;
import com.philpicinic.easybillsplit.dao.UserGroup;
import com.philpicinic.easybillsplit.dao.UserGroupDao;
import com.philpicinic.easybillsplit.dialogs.ContactSearchFragment;
import com.philpicinic.easybillsplit.dialogs.GroupSaveDialog;
import com.philpicinic.easybillsplit.dialogs.MemberEditDialog;
import com.philpicinic.easybillsplit.service.ManagerService;

import java.util.ArrayList;


public class GroupCreateActivity extends ActionBarActivity {

    public static final String PERSON_ID = "person_id";

    private static final int EDIT_ACTION = 0;
    private static final int DELETE_ACTION = 1;
    private static final int CANCEL_ACTION = 2;

    private ArrayList<IPerson> members;
    private ArrayAdapter<IPerson> aa;
    private ContactSearchFragment contactDialog;

    private boolean hasName;
    private String groupName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_create);
        Bundle args = getIntent().getBundleExtra("BUNDLE");
        if(args.getBoolean(GroupSelectActivity.HAS_NAME)){
            groupName = args.getString(GroupSelectActivity.GROUP_NAME);
            if(groupName.length() > 0) {
                hasName = true;
                setTitle(groupName);
            }
        }
//        contactDialog = new ContactSearchFragment();
        members = ManagerService.getInstance().getMembers();
        if(members.size() == 0) {
            members.add(new TextPerson(getString(R.string.me), ManagerService.getInstance().getCurrentId()));
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

        Button importBtn = (Button) findViewById(R.id.import_contact_btn);
        importBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                contactDialog = new ContactSearchFragment();
                contactDialog.show(getSupportFragmentManager().beginTransaction(), null);
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
                    GroupSaveDialog dialog = new GroupSaveDialog();
                    Bundle args = new Bundle();
                    if(!hasName){
                        StringBuilder sb = new StringBuilder();
                        for(IPerson person : members){
                            sb.append(person.toString());
                            sb.append(", ");
                        }
                        sb.deleteCharAt(sb.length() - 1);
                        sb.deleteCharAt(sb.length() - 1);
                        groupName = sb.toString();
                    }
                    args.putString(GroupSelectActivity.GROUP_NAME, groupName);
                    dialog.setArguments(args);
                    dialog.show(getSupportFragmentManager().beginTransaction(), null);
                }
            }
        });

    }

    public void continueToItems(){
        Intent intent = new Intent(getApplicationContext(), ItemCreateActivity.class);
        startActivity(intent);
    }

    public void saveGroup(){
//        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(this, "group-db", null);
//        SQLiteDatabase db = helper.getWritableDatabase();
        DaoMaster daoMaster = ManagerService.getInstance().getDaoMaster();
        DaoSession daoSession = daoMaster.newSession();
        UserGroupDao groupDao = daoSession.getUserGroupDao();
        UserGroup group = new UserGroup();
        group.setName(groupName);
        long groupId = groupDao.insert(group);
        UserDao userDao = daoSession.getUserDao();
        ArrayList<User> users = new ArrayList<User>(members.size());
        for(IPerson person : members){
            User user = new User();
            user.setName(person.getName());
            user.setUserId(person.getId());
            user.setType(0);
            userDao.insert(user);
        }
//        for()
        finish();
//        GroupSelectActivity.getInstance().startItems();
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo){
        super.onCreateContextMenu(menu, v, menuInfo);
        AdapterView.AdapterContextMenuInfo adapterContextMenuInfo = (AdapterView.AdapterContextMenuInfo)menuInfo;
        if(members.get(adapterContextMenuInfo.position) instanceof TextPerson) {
            menu.add(Menu.NONE, EDIT_ACTION, Menu.NONE, getString(R.string.edit));
        }
        menu.add(Menu.NONE, DELETE_ACTION, Menu.NONE, getString(R.string.delete));
        menu.add(Menu.NONE, CANCEL_ACTION, Menu.NONE, getString(R.string.cancel));
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
        MemberEditDialog dialog = new MemberEditDialog();
        Bundle args = new Bundle();
        args.putInt(PERSON_ID, position);
        dialog.setArguments(args);
        dialog.show(getSupportFragmentManager().beginTransaction(), null);
    }

    public void addContactMember(long id, int number_id, String name){
        ContactPerson person = new ContactPerson(ManagerService.getInstance().getCurrentId(),
                id, number_id, name);
        members.add(person);
        aa.notifyDataSetChanged();
    }
}
