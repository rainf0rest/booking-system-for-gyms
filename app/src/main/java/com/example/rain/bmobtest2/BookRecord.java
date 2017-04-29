package com.example.rain.bmobtest2;

import cn.bmob.v3.BmobObject;

/**
 * Created by rain on 2017/4/25.
 */

public class BookRecord extends BmobObject {
    private String UserID;
    private String EqID;
    private String Time;
    private String date;
    private String EqName;

    public String getEqName() {
        return EqName;
    }

    public void setEqName(String eqName) {
        EqName = eqName;
    }

    public String getUserID() {
        return UserID;
    }

    public void setUserID(String userID) {
        UserID = userID;
    }

    public String getEqID() {
        return EqID;
    }

    public void setEqID(String eqID) {
        EqID = eqID;
    }

    public String getTime() {
        return Time;
    }

    public void setTime(String time) {
        Time = time;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
