package com.philpicinic.easybillsplit.item;

import com.philpicinic.easybillsplit.contact.IPerson;

import java.math.BigDecimal;
import java.util.ArrayList;

/**
 * Created by Phil on 9/25/2014.
 */
public class SharedItem implements IItem{

    private BigDecimal price;
    private String name;
    private ArrayList<IPerson> members;

    public SharedItem(String name, String price, ArrayList<IPerson> members){
        this.name = name;
        this.price = new BigDecimal(price);
        this.price = this.price.setScale(2, BigDecimal.ROUND_HALF_EVEN);
        this.members = members;
    }

    @Override
    public BigDecimal total() {
        return price;
    }

    @Override
    public boolean hasPerson(IPerson person) {
        for(IPerson p : members){
            if(p.equals(person)){
                return true;
            }
        }
        return false;
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

    @Override
    public IPerson getPerson() {
        throw new UnsupportedOperationException();
    }

    @Override
    public void setPrice(String price) {
        this.price = new BigDecimal(price);
        this.price = this.price.setScale(2, BigDecimal.ROUND_HALF_EVEN);
    }

    @Override
    public boolean deletePerson(IPerson person){
        if(members.contains(person)){
            members.remove(person);
            if(members.size() == 0){
                return true;
            }
        }
        return false;
    }

    @Override
    public BigDecimal personTotal(){
        BigDecimal result = this.price.divide(new BigDecimal(members.size()), 2,
                BigDecimal.ROUND_HALF_EVEN);
        return result;
    }

    public ArrayList<IPerson> getMembers(){
        return members;
    }

    public void setMembers(ArrayList<IPerson> members){
        this.members = members;
    }

    @Override
    public void setPerson(IPerson person) {
        throw new UnsupportedOperationException();
    }

    @Override
    public String toString(){
        return name;
    }
}
