package com.philpicinic.easybillsplit.item;

import com.philpicinic.easybillsplit.contact.IPerson;

import java.math.BigDecimal;

/**
 * Created by Phil on 9/9/2014.
 */
public interface IItem {

    public BigDecimal total();
    public BigDecimal personTotal();
    public boolean hasPerson(IPerson person);
    public String getName();
    public void setName(String name);
    public BigDecimal getPrice();
    public IPerson getPerson();
    public void setPrice(String price);
    public void setPerson(IPerson person);
    public boolean deletePerson(IPerson person);

    public void attachSMS(StringBuilder sb);
}
