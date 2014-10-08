package com.philpicinic.easybillsplit.contact;

/**
 * Created by Phil on 10/6/2014.
 */
public class MyGroup {

    private long id;
    private String name;

    public MyGroup(long id, String name){
        this.id = id;
        this.name = name;
    }

    public long getId(){
        return id;
    }

    public String getName(){
        return name;
    }

    @Override
    public String toString(){
        return name;
    }
}
