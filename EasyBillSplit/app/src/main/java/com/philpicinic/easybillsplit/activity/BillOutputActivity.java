package com.philpicinic.easybillsplit.activity;

import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.telephony.SmsManager;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.philpicinic.easybillsplit.R;
import com.philpicinic.easybillsplit.adapters.BillAdapter;
import com.philpicinic.easybillsplit.contact.ContactPerson;
import com.philpicinic.easybillsplit.contact.IPerson;
import com.philpicinic.easybillsplit.item.IItem;
import com.philpicinic.easybillsplit.service.DatabaseService;
import com.philpicinic.easybillsplit.service.ManagerService;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.logging.Handler;

public class BillOutputActivity extends BaseActionBarActivity {

    public static final String PERSON_ID = "person_id";

    private ArrayList<IItem> items;
    private ArrayList<IPerson> members;

    private static final int VIEW_ACTION = 1;
    private static final int TEXT_ACTION = 2;
    private static final int CANCEL_ACTION = 3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bill_output);

        items = ManagerService.getInstance().getItems();
        members = ManagerService.getInstance().getMembers();

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        BillAdapter billAdapter = new BillAdapter(this, R.layout.person_bill_amt_layout, members);

        ListView listView = (ListView) findViewById(R.id.person_bill_amt);
        listView.setAdapter(billAdapter);

        BigDecimal totalAmount = ManagerService.getInstance().getTotalCost();
        TextView totalText = (TextView) findViewById(R.id.total_amt);
        totalText.setText(totalAmount.toString());

        registerForContextMenu(listView);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo){
        super.onCreateContextMenu(menu, v, menuInfo);
        AdapterView.AdapterContextMenuInfo adapterContextMenuInfo = (AdapterView.AdapterContextMenuInfo)menuInfo;
        menu.add(Menu.NONE, VIEW_ACTION, Menu.NONE, getString(R.string.view));
        if(members.get(adapterContextMenuInfo.position) instanceof ContactPerson){
            menu.add(Menu.NONE, TEXT_ACTION, Menu.NONE, getString(R.string.text));
        }
        menu.add(Menu.NONE, CANCEL_ACTION, Menu.NONE, getString(R.string.cancel));
    }

    @Override
    public boolean onContextItemSelected(MenuItem item){
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        switch(item.getItemId()){
            case VIEW_ACTION:
                int id = members.get(info.position).getId();
                Intent intent = new Intent(this, PersonBillActivity.class);
                intent.putExtra(PERSON_ID, id);
                startActivity(intent);
                return true;
            case TEXT_ACTION:
                final ContactPerson person = (ContactPerson) members.get(info.position);
                if(ManagerService.getInstance().hasAnyItems(person)){
                    final String message = ManagerService.getInstance().getSMSBill(person);
                    final BillOutputActivity activity = this;
                    new Thread(new Runnable() {
                        @Override

                        public void run() {
                            SmsManager smsManager = SmsManager.getDefault();
                            ContentResolver cr = getContentResolver();
                            Cursor cursor = cr.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                                    null,
                                    ContactsContract.CommonDataKinds.Phone._ID + "=" + person.getNumberId(),
                                    null,
                                    null);
                            cursor.moveToFirst();
                            String number = cursor.getString(cursor.
                                    getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                            System.out.println("happens");
                            System.out.println(message);

                            if(message.length() < 151){
                                smsManager.sendTextMessage(number, null, message, null, null);
                            }else{
                                String[] lines = message.split("\n");
                                ArrayList<String> texts = new ArrayList<String>();
                                StringBuilder currentMessage = new StringBuilder();
                                for(String line : lines){
                                    if(currentMessage.length() + line.length() < 159) {
                                        currentMessage.append(line);
                                        currentMessage.append("\n");
                                    }else{
                                        currentMessage.deleteCharAt(currentMessage.length() - 1);
                                        texts.add(currentMessage.toString());
                                        currentMessage = new StringBuilder();
                                        currentMessage.append(line);
                                        currentMessage.append("\n");
                                    }
                                }
                                currentMessage.deleteCharAt(currentMessage.length() - 1);
                                texts.add(currentMessage.toString());
                                smsManager.sendMultipartTextMessage(number, null, texts, null, null);
                            }
//                            android.os.Handler handler = new android.os.Handler();
                            activity.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    activity.SMSToast(person.getName());
                                }
                            });

                        }
                    }).start();
                }
                return true;
            default:
                return false;
        }
    }

    public void SMSToast(String name){
        String msg = "Successfully sent SMS to " + name + ".";
        Toast toast = Toast.makeText(this, msg, Toast.LENGTH_LONG);
        toast.show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.bill_output, menu);
        return true;
    }

    public void checkPersonBill(int id){
        Intent intent = new Intent(this, PersonBillActivity.class);
        intent.putExtra(PERSON_ID, id);
        startActivity(intent);
    }
}
