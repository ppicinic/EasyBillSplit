package com.philpicinic.easybillsplit.item;

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
    }

    public BigDecimal total(){
        return price;
    }

    @Override
    public boolean hasPerson(IPerson person) {
        return this.person.equals(person);
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
    public BigDecimal getPrice() {
        return price;
    }

    public IPerson getPerson(){
        return person;
    }

    @Override
    public void setPrice(String price) {
        this.price = new BigDecimal(price);
        this.price = this.price.setScale(2, BigDecimal.ROUND_HALF_EVEN);
    }

    @Override
    public boolean deletePerson(IPerson person){
        return this.person.equals(person);
    }

    public void setPerson(IPerson person){
        this.person = person;
    }

    public String toString(){
        return name;
    }
}
