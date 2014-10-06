package com.philpicinic.easybillsplit.contact;

import android.os.Parcelable;

import com.philpicinic.easybillsplit.dao.User;

/**
 * Created by phil on 9/9/14.
 */
public interface IPerson {

    public int getId();
    public boolean equals(IPerson person);
    public String getName();
    public void setName(String name);
    public void setId(int id);
    public void setDatabaseId(long id);

    public User createDatabaseUser();
}
