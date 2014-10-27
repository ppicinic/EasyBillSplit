package com.philpicinic.easybillsplit.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.philpicinic.easybillsplit.R;
import com.philpicinic.easybillsplit.contact.MyGroup;
import com.philpicinic.easybillsplit.dao.UserGroup;
import com.philpicinic.easybillsplit.dialogs.GroupNameDialog;
import com.philpicinic.easybillsplit.service.DatabaseService;
import com.philpicinic.easybillsplit.service.ManagerService;

import java.util.ArrayList;
import java.util.List;


public class GroupSelectActivity extends BaseActionBarActivity {

    public static final String HAS_NAME = "HAS_NAME";
    public static final String GROUP_NAME = "GROUP_NAME";
    private static final int VIEW_ACTION = 0;
    private static final int DELETE_ACTION = 2;
    private static final int CANCEL_ACTION = 3;

    private static GroupSelectActivity instance;

    private ArrayAdapter<MyGroup> aa;
    private List<MyGroup> groups;
    private ListView listView;
    private TextView selectText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        instance = this;
        setContentView(R.layout.activity_group_select);
        setTitle(R.string.group_select_activity_name);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeButtonEnabled(true);

        groups = DatabaseService.getInstance().getGroups();
        selectText = (TextView) findViewById(R.id.select_group_text);
        if(groups.size() == 0){
            selectText.setText(getString(R.string.no_groups));
        }
        aa = new ArrayAdapter<MyGroup>(this, android.R.layout.simple_list_item_1, groups);
        listView = (ListView) findViewById(R.id.group_list);
        listView.setAdapter(aa);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                long groupId = groups.get(i).getId();
                ManagerService.getInstance().startWithGroup(groupId);
                Intent intent = new Intent(getApplicationContext(), ItemCreateActivity.class);
                startActivity(intent);
            }
        });
        registerForContextMenu(listView);
        Button btn = (Button)findViewById(R.id.create_group_btn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                GroupNameDialog dialog = new GroupNameDialog();
                dialog.show(getSupportFragmentManager().beginTransaction(), null);
            }
        });

        Button simpleSplitBtn = (Button) findViewById(R.id.simple_calculator_button);
        simpleSplitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), SimpleSplitter.class);
                startActivity(intent);
            }
        });
    }
//
//    @Override
//    protected void onResume(Bundle savedInstanceState){
//
//    }


    private List<MyGroup> transformGroup(List<UserGroup> groupsRaw){
        ArrayList<MyGroup> temp = new ArrayList<MyGroup>(groupsRaw.size());
        for(UserGroup g : groupsRaw){
            temp.add(new MyGroup(g.getId(), g.getName()));
        }
        return temp;
    }

    @Override
    public void onResume(){
        super.onResume();
        updateAdapter();
    }

    private void updateAdapter(){
        groups = DatabaseService.getInstance().getGroups();
        aa = new ArrayAdapter<MyGroup>(this, android.R.layout.simple_list_item_1, groups);
        listView.setAdapter(aa);
        if(groups.size() == 0){
            selectText.setText(getString(R.string.no_groups));
        }else{
            selectText.setText(getString(R.string.select_group));
        }
    }

    public void createGroup(){
        ManagerService.getInstance().reset();
        Intent intent = new Intent(getApplicationContext(), GroupCreateActivity.class);
        startActivity(intent);
    }

    public void createGroup(String name){
        ManagerService.getInstance().reset();
        Intent intent = new Intent(getApplicationContext(), GroupCreateActivity.class);
        intent.putExtra(HAS_NAME, true);
        intent.putExtra(GROUP_NAME, name);
        startActivity(intent);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo){
        super.onCreateContextMenu(menu, v, menuInfo);
        AdapterView.AdapterContextMenuInfo adapterContextMenuInfo = (AdapterView.AdapterContextMenuInfo)menuInfo;
        menu.add(Menu.NONE, VIEW_ACTION, Menu.NONE, getString(R.string.view));
        menu.add(Menu.NONE, DELETE_ACTION, Menu.NONE, getString(R.string.delete));
        menu.add(Menu.NONE, CANCEL_ACTION, Menu.NONE, getString(R.string.cancel));
    }

    @Override
    public boolean onContextItemSelected(MenuItem item){
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        switch(item.getItemId()){
            case VIEW_ACTION:
                long groupId = groups.get(info.position).getId();
                ManagerService.getInstance().startWithGroup(groupId);
                Intent intent = new Intent(getApplicationContext(), ItemCreateActivity.class);
                startActivity(intent);
                return true;
            case DELETE_ACTION:
                DatabaseService.getInstance().deleteGroup(groups.get(info.position).getId());
                updateAdapter();
                return true;
            default:
                return false;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.group_select, menu);
        return true;
    }

    public void startItems(long groupId){
        ManagerService.getInstance().startWithGroup(groupId);
        Intent intent = new Intent(getApplicationContext(), ItemCreateActivity.class);
        startActivity(intent);
    }

    public static GroupSelectActivity getInstance(){
        return instance;
    }
}
