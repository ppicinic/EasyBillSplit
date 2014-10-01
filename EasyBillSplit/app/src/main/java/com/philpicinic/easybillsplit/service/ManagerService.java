package com.philpicinic.easybillsplit.service;

import com.philpicinic.easybillsplit.activity.ItemCreateActivity;
import com.philpicinic.easybillsplit.contact.IPerson;
import com.philpicinic.easybillsplit.item.IItem;

import java.lang.reflect.Array;
import java.math.BigDecimal;
import java.util.ArrayList;

/**
 * Created by phil on 9/15/14.
 */
public class ManagerService {

    private static ManagerService instance = new ManagerService();

    private ArrayList<IPerson> members;
    private ArrayList<IItem> items;
    private BigDecimal taxAmt;
    private BigDecimal tipRate;
    private int person_id;

    private ManagerService(){
        members = new ArrayList<IPerson>();
        items = new ArrayList<IItem>();
        taxAmt = new BigDecimal("0");
        tipRate = new BigDecimal("1");
        person_id = -1;
    }

    public static ManagerService getInstance(){
        return instance;
    }

    public ArrayList<IPerson> getMembers(){
        return members;
    }

    public ArrayList<IItem> getItems(){
        return items;
    }

    public void reset(){
        members = new ArrayList<IPerson>();
        items = new ArrayList<IItem>();
    }

    public void setTaxAmt(BigDecimal tax){
        taxAmt = tax;
    }

    public void setTipRate(BigDecimal rate){
        tipRate = rate.divide(new BigDecimal("100"));
        tipRate = tipRate.add(new BigDecimal("1"));
        tipRate = tipRate.setScale(2, BigDecimal.ROUND_HALF_EVEN);
    }

    public BigDecimal calculatePerson(IPerson person){
        return calculatePersonItems(person, items);
    }

    public BigDecimal calculatePersonItems(IPerson person, ArrayList<IItem> myItems){
        BigDecimal total = new BigDecimal("0");
        BigDecimal completeTotal = new BigDecimal("0");
        for(IItem item : myItems){
            if(item.hasPerson(person)){
                total = total.add(item.personTotal());
            }
            completeTotal = completeTotal.add(item.total());
        }
        total = total.multiply(calculateTaxRate());
        total = total.multiply(tipRate);
        total = total.setScale(2, BigDecimal.ROUND_HALF_EVEN);
        return total;
    }

    private BigDecimal calculateTaxRate(){
        BigDecimal total = new BigDecimal("0");
        for(IItem item : items){
            total = total.add(item.total());
        }
        BigDecimal taxRate = taxAmt.divide(total, 5, BigDecimal.ROUND_HALF_EVEN);
        taxRate = taxRate.add(new BigDecimal("1"));
        return taxRate;
    }

    public IPerson getPerson(int id){
        for(IPerson person : members){
            if(person.getId() == id){
                return person;
            }
        }
        return null;
    }

    public ArrayList<IItem> getPersonItems(IPerson person){
        ArrayList<IItem> resultItems = new ArrayList<IItem>();
        for(IItem item : items){
            if(item.hasPerson(person)){
                resultItems.add(item);
            }
        }
        return resultItems;
    }

    public BigDecimal calculateItemForPerson(IItem item, IPerson person){
        BigDecimal total = item.personTotal();
//        total = total.multiply(calculateTaxRate());
//        total = total.multiply(tipRate);
        total = total.setScale(2, BigDecimal.ROUND_HALF_EVEN);
        return total;
    }

    public BigDecimal getTotalCost(){
        BigDecimal total = new BigDecimal("0");
        for(IItem item : items){
            total = total.add(item.total());
        }
        total = total.multiply(calculateTaxRate());
        total = total.multiply(tipRate);
        total = total.setScale(2, BigDecimal.ROUND_HALF_EVEN);
        return total;
    }

    public int getCurrentId(){
        return ++person_id;
    }
}
