package com.example.rain.bmobtest2;

import cn.bmob.v3.BmobObject;

/**
 * Created by rain on 2017/4/25.
 */

public class Bookrecord extends BmobObject {
    private String UserID;
    private int EqID;
    private int EqInID;
    private String Time;

    public String getUserID() {
        return UserID;
    }

    public void setUserID(String userID) {
        UserID = userID;
    }

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

    public String getTime() {
        return Time;
    }

    public void setTime(String time) {
        Time = time;
    }
}
