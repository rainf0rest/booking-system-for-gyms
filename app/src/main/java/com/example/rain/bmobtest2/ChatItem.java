package com.example.rain.bmobtest2;

import cn.bmob.v3.BmobObject;

/**
 * Created by rain on 2017/5/9.
 */

public class ChatItem extends BmobObject {
    private String UserID, UserName, Content;
    private int  UserHead, Id;

    public String getUserID() {
        return UserID;
    }

    public void setUserID(String userID) {
        UserID = userID;
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        this.Id = id;
    }

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String userName) {
        UserName = userName;
    }

    public int getUserHead() {
        return UserHead;
    }

    public void setUserHead(int userHead) {
        UserHead = userHead;
    }

    public String getContent() {
        return Content;
    }

    public void setContent(String content) {
        Content = content;
    }
}
