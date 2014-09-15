package com.philpicinic.easybillsplit.service;

import com.philpicinic.easybillsplit.activity.ItemCreateActivity;
import com.philpicinic.easybillsplit.contact.IPerson;
import com.philpicinic.easybillsplit.item.IItem;

import java.util.ArrayList;

/**
 * Created by phil on 9/15/14.
 */
public class ManagerService {

    private static ManagerService instance = new ManagerService();

    private ArrayList<IPerson> members;
    private ArrayList<IItem> items;

    private ManagerService(){
        members = new ArrayList<IPerson>();
        items = new ArrayList<IItem>();
    }

    public static ManagerService getInstance(){
        return instance;
    }

    public ArrayList<IPerson> getMembers(){
        return members;
    }

    public ArrayList<IItem> getItems(){
        return items;
    }

    public void reset(){
        members = new ArrayList<IPerson>();
        items = new ArrayList<IItem>();
    }
}
