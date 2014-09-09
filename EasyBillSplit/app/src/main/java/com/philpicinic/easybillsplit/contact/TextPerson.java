package com.philpicinic.easybillsplit.contact;

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

    public void setName(String name){
        this.name = name;
    }

    public String toString(){
        return name;
    }

}
