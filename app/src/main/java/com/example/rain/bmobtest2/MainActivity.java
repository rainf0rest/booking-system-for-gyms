package com.example.rain.bmobtest2;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;

public class MainActivity extends AppCompatActivity {

    private Button loginbtn;
    TextView regbtn;
    private EditText nameEditText, passEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bmob.initialize(this, "e038ac3ba7a96b5c39372ee8589e1426");

        setContentView(R.layout.login_new);

        loginbtn = (Button) findViewById(R.id.btn_login);
        regbtn = (TextView) findViewById(R.id.btn_register);
        nameEditText = (EditText) findViewById(R.id.et_username);
        passEditText = (EditText) findViewById(R.id.et_password);

        //BmobUser bmobUser = new BmobUser();

        regbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, SignupActivity.class);
                startActivity(intent);
                //Intent intent = new Intent(MainActivity.this, WorkActivity.class);
            }
        });

        loginbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = nameEditText.getText().toString();
                String password = passEditText.getText().toString();

                //判断非空
                if(TextUtils.isEmpty(name) || TextUtils.isEmpty(password)) {
                    Toast.makeText(MainActivity.this, "用户名或密码不能为空", Toast.LENGTH_SHORT).show();
                    return;
                }

                //BmobUser user = new BmobUser();
                User user = new User();
                user.setUsername(name);
                user.setPassword(password);

                user.login(new SaveListener<BmobUser>() {
                    @Override
                    public void done(BmobUser bmobUser, BmobException e) {
                        if(e==null){
                            Toast.makeText(MainActivity.this, "success", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(MainActivity.this, ChooseActivity.class);
                            startActivity(intent);
                            //toast("登录成功:");
                            //通过BmobUser user = BmobUser.getCurrentUser()获取登录成功后的本地用户信息
                            //如果是自定义用户对象MyUser，可通过MyUser user = BmobUser.getCurrentUser(MyUser.class)获取自定义用户信息
                        }else{
                            //loge(e);
                            Toast.makeText(MainActivity.this, "fail", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

            }
        });

    }
}