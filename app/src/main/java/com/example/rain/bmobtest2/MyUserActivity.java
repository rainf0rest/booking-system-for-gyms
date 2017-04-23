package com.example.rain.bmobtest2;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

import cn.bmob.v3.BmobUser;

/**
 * Created by rain on 2017/4/23.
 */

public class MyUserActivity extends Activity {

    private TextView userDataTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_user);

        userDataTextView = (TextView) findViewById(R.id.userDataText);

        BmobUser bmobUser = BmobUser.getCurrentUser();

        userDataTextView.setText("");
        userDataTextView.append("用户名：" + bmobUser.getUsername() + "\n");
        userDataTextView.append("用户邮箱：" + bmobUser.getEmail() + "\n");
        //userDataTextView.append("用户余额：" + bmobUser.getUsername() + "\n");


    }

}
