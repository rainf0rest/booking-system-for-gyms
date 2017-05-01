package com.example.rain.bmobtest2;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;

/**
 * Created by rain on 2017/4/21.
 */

public class SignupActivity extends Activity {

    private Button regbtn;
    //private EditText userName, userPassword, userEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup_new);

        regbtn = (Button) findViewById(R.id.btn_register);
        final EditText userName, userPassword, userEmail;
        userName = (EditText) findViewById(R.id.et_username);
        userPassword = (EditText) findViewById(R.id.et_password);
        userEmail = (EditText) findViewById(R.id.et_email);

        regbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = userName.getText().toString();
                String password = userPassword.getText().toString();
                String Email = userEmail.getText().toString();

                if(TextUtils.isEmpty(name) || TextUtils.isEmpty(password)) {
                    Toast.makeText(SignupActivity.this, "用户名或密码不能为空", Toast.LENGTH_SHORT).show();
                    return;
                }

                //BmobUser bmobUser = new BmobUser();
                User bmobUser = new User();
                bmobUser.setUsername(name);
                bmobUser.setPassword(password);
                bmobUser.setEmail(Email);
                bmobUser.setMoney(0);

                bmobUser.signUp(new SaveListener<User>() {
                    @Override
                    public void done(User s, BmobException e) {
                        if(e==null){
                            Toast.makeText(SignupActivity.this, "注册成功", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(SignupActivity.this, ChooseActivity.class);
                            startActivity(intent);
                        }else{
                            Toast.makeText(SignupActivity.this, "注册失败，请重试", Toast.LENGTH_SHORT).show();
                        }
                    }
                });



            }
        });
    }

}
