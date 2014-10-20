package com.philpicinic.easybillsplit.util;

/**
 * Created by phil on 10/20/14.
 */
public class NumberChecker {

    public static boolean isNumeric(String str){
        return str.matches("-?\\d+(\\.\\d*)?");  //match a number with optional '-' and decimal.
    }
}
