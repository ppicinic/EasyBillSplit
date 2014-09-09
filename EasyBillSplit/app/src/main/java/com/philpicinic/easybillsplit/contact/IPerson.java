package com.philpicinic.easybillsplit.contact;

import android.os.Parcelable;

import com.philpicinic.easybillsplit.com.philpicinic.easybillsplit.item.IItem;

/**
 * Created by phil on 9/9/14.
 */
public interface IPerson extends Parcelable {

    public void addItem(IItem item);
}
