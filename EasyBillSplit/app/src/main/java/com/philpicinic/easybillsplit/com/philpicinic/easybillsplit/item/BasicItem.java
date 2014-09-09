package com.philpicinic.easybillsplit.com.philpicinic.easybillsplit.item;

import com.philpicinic.easybillsplit.contact.IPerson;

import java.math.BigDecimal;

/**
 * Created by Phil on 9/9/2014.
 */
public class BasicItem implements IItem {

    private String name;
    private BigDecimal price;
    private IPerson person;

    public BasicItem(String name, String price, IPerson person){
        this.name = name;
        this.price = new BigDecimal(price);
        this.price = this.price.setScale(2, BigDecimal.ROUND_HALF_EVEN);
        this.person = person;
        this.person.addItem(this);
    }

    public BigDecimal total(){
        return price;
    }

    public BigDecimal perTotal(){
        return price;
    }

    public String toString(){
        return name + " " + price.toString();
    }
}
