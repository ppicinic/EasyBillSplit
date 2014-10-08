package com.philpicinic.easybillsplit.contact;

import android.os.Parcel;
import android.os.Parcelable;

import com.philpicinic.easybillsplit.dao.User;

/**
 * Created by phil on 9/9/14.
 */
public class TextPerson implements IPerson {

    private String name;
    private int id;
    private long databaseId;

    public TextPerson(){
        name = "";
        id = -1;
        databaseId = -1;
    }

    public TextPerson(String name, int id){
        this.name = name;
        this.id = id;
        databaseId = -1;
    }

    private TextPerson(Parcel in){
        name = in.readString();
    }

    public String getName(){
        return name;
    }

    public void setName(String name){
        this.name = name;
    }

    public int getId(){
        return id;
    }

    public boolean equals(IPerson person){
        return id == person.getId();
    }

    public User createDatabaseUser(){
        User user = new User();
        System.out.println(name);
        System.out.println(this.databaseId);
        if(databaseId != -1){
            user.setId(databaseId);
        }
        user.setUserId(id);
        user.setType(1);
        user.setName(name);
        return user;
    }

    public void setId(int id){
        this.id = id;
    }

    public void setDatabaseId(long databaseId){
        this.databaseId = databaseId;
    }

    @Override
    public String toString(){
        return name;
    }

    public static final Parcelable.Creator<TextPerson> CREATOR = new Parcelable.Creator<TextPerson>() {
        public TextPerson createFromParcel(Parcel in) {
            return new TextPerson(in);
        }

        public TextPerson[] newArray(int size) {
            return new TextPerson[size];

        }
    };
}
