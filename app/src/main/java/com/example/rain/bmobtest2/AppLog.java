package com.example.rain.bmobtest2;

import cn.bmob.v3.BmobObject;

/**
 * Created by rain on 2017/5/1.
 */

public class AppLog extends BmobObject {
    String UserID;
    String Operate;

    public String getUserID() {
        return UserID;
    }

    public void setUserID(String userID) {
        UserID = userID;
    }

    public String getOperate() {
        return Operate;
    }

    public void setOperate(String operate) {
        Operate = operate;
    }
}
