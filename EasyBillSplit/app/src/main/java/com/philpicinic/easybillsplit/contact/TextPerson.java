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

    public TextPerson(){
        name = "";
    }

    public TextPerson(String name){
        this.name = name;
    }

    private TextPerson(Parcel in){
        name = in.readString();
    }

    public void setName(String name){
        this.name = name;
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
