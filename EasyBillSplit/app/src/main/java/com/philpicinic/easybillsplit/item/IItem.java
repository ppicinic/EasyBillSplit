package com.philpicinic.easybillsplit.item;

import com.philpicinic.easybillsplit.contact.IPerson;

import java.math.BigDecimal;

/**
 * Created by Phil on 9/9/2014.
 */
public interface IItem {

    public BigDecimal total();
    public boolean hasPerson(IPerson person);
    public String getName();
    public void setName(String name);
    public BigDecimal getPrice();
    public void setPrice(String price);
    public void setPerson(IPerson person);
}
