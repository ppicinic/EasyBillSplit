package com.philpicinic.easybillsplit.contact;

import android.os.Parcel;
import android.os.Parcelable;


import org.w3c.dom.Text;

import java.util.ArrayList;

/**
 * Created by phil on 9/9/14.
 */
public class TextPerson implements IPerson {

    private String name;
    private int id;

    public TextPerson(){
        name = "";
        id = -1;
    }

    public TextPerson(String name, int id){
        this.name = name;
        this.id = id;
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

    public String toString(){
        return name;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(name);
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
