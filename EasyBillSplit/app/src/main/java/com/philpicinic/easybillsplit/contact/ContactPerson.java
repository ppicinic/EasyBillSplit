package com.philpicinic.easybillsplit.contact;

import android.os.Parcel;

/**
 * Created by Phil on 10/2/2014.
 */
public class ContactPerson implements IPerson {

    private int id;
    private long contact_id;
    private int number_id;
    private String name;

    public ContactPerson(int id, long contact_id, int number_id, String name){
        this.id = id;
        this.contact_id = contact_id;
        this.number_id = number_id;
        this.name = name;
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public boolean equals(IPerson person) {
        return id == person.getId();
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString(){
        return this.name;
    }
}
