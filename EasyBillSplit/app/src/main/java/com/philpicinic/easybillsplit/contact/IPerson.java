package com.philpicinic.easybillsplit.contact;

import android.os.Parcelable;

/**
 * Created by phil on 9/9/14.
 */
public interface IPerson extends Parcelable {

    public int getId();
    public boolean equals(IPerson person);
}
