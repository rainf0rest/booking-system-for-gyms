package com.example.rain.bmobtest2;

import cn.bmob.v3.BmobObject;

/**
 * Created by rain on 2017/4/21.
 */

public class Equipment extends BmobObject {

    private int EqID;
    private int EqInID;
    private int EqNumber;
    private String EqName;
    private int Eqfree;
    private int Eqprice;

    public int getEqID() {
        return EqID;
    }

    public void setEqID(int eqID) {
        EqID = eqID;
    }

    public int getEqInID() {
        return EqInID;
    }

    public void setEqInID(int eqInID) {
        EqInID = eqInID;
    }

    public int getEqNumber() {
        return EqNumber;
    }

    public void setEqNumber(int eqNumber) {
        EqNumber = eqNumber;
    }

    public String getEqName() {
        return EqName;
    }

    public void setEqName(String eqName) {
        EqName = eqName;
    }

    public int getEqfree() {
        return Eqfree;
    }

    public void setEqfree(int eqfree) {
        Eqfree = eqfree;
    }

    public int getEqprice() {
        return Eqprice;
    }

    public void setEqprice(int eqprice) {
        Eqprice = eqprice;
    }
}
