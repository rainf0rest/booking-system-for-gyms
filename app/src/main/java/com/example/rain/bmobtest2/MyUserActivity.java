package com.example.rain.bmobtest2;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.UpdateListener;

/**
 * Created by rain on 2017/4/23.
 */

public class MyUserActivity extends Activity {

    private TextView userDataTextView;
    private Button editbtn, editPasswordbtn;
    private Handler wHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_user);

        userDataTextView = (TextView) findViewById(R.id.userDataText);
        editbtn = (Button) findViewById(R.id.editbtnofUserData);
        editPasswordbtn = (Button) findViewById(R.id.editbtnofpassword);

        User bmobUser = BmobUser.getCurrentUser(User.class);

        userDataTextView.setText("");
        userDataTextView.append("用户名：" + bmobUser.getUsername() + "\n");
        userDataTextView.append("用户邮箱：" + bmobUser.getEmail() + "\n");
        userDataTextView.append("用户余额：" + bmobUser.getMoney() + "\n");

        editbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText(MyUserActivity.this, "suc", Toast.LENGTH_SHORT).show();
                showDia();
                //update();
            }
        });

        editPasswordbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText(MyUserActivity.this, "click passbtn", Toast.LENGTH_SHORT).show();
                showDiaOfPassword();
            }
        });

        wHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                if (msg.what == 0x901) {
                    update();
                }else if(msg.what == 0x902) {
                    Toast.makeText(MyUserActivity.this, "修改密码成功", Toast.LENGTH_SHORT).show();
                }
            }


        };
    }

    private void showDia() {
        final View view = View.inflate(MyUserActivity.this, R.layout.dialog_of_usedata, null);
        //Toast.makeText(MainActivity.this, "edit", Toast.LENGTH_SHORT).show();
        new AlertDialog.Builder(MyUserActivity.this)
                .setIcon(R.drawable.edit)
                .setTitle("编辑个人信息")
                .setView(view)
                .setPositiveButton("yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        EditText tempname = (EditText) view.findViewById(R.id.userData_username);
                        EditText tempemail = (EditText) view.findViewById(R.id.userData_useremail);
                        //Toast.makeText(MyUserActivity.this, "name:" + tempname.getText().toString() + "\nemail:" + tempemail.getText().toString(), Toast.LENGTH_SHORT).show();
                        String newName = tempname.getText().toString();
                        String newEmail = tempemail.getText().toString();
                        if(TextUtils.isEmpty(newEmail) || TextUtils.isEmpty(newName)) {
                            Toast.makeText(MyUserActivity.this, "用户名或邮箱不能为空", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        else {
                            User user = BmobUser.getCurrentUser(User.class);
                            user.setUsername(newName);
                            user.setEmail(newEmail);
                            user.update(user.getObjectId(), new UpdateListener() {
                                @Override
                                public void done(BmobException e) {
                                    if(e == null) {
                                        Message msg = new Message();
                                        msg.what = 0x901;
                                        wHandler.sendMessage(msg);
                                    }
                                }
                            });
                        }
                    }
                })
                .setNegativeButton("no", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .create()
                .show();

    }

    private void showDiaOfPassword() {
        final View view = View.inflate(MyUserActivity.this, R.layout.dialog_of_password, null);
        //Toast.makeText(MainActivity.this, "edit", Toast.LENGTH_SHORT).show();
        new AlertDialog.Builder(MyUserActivity.this)
                .setIcon(R.drawable.password)
                .setTitle("修改密码")
                .setView(view)
                .setPositiveButton("yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        EditText tempold = (EditText) view.findViewById(R.id.userData_user_oldPassword);
                        EditText tempnew = (EditText) view.findViewById(R.id.userData_user_newPassword);
                        //Toast.makeText(MyUserActivity.this, "name:" + tempname.getText().toString() + "\nemail:" + tempemail.getText().toString(), Toast.LENGTH_SHORT).show();
                        String oldp = tempold.getText().toString();
                        String newp = tempnew.getText().toString();
                        if(TextUtils.isEmpty(newp) || TextUtils.isEmpty(oldp)) {
                            Toast.makeText(MyUserActivity.this, "旧密码或新密码不能为空", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        else {
                            User user = BmobUser.getCurrentUser(User.class);
                            user.updateCurrentUserPassword(oldp, newp, new UpdateListener() {
                                @Override
                                public void done(BmobException e) {
                                    if(e == null){
                                        Message msg = new Message();
                                        msg.what = 0x902;
                                        wHandler.sendMessage(msg);
                                    }else {
                                        Toast.makeText(MyUserActivity.this, "修改密码失败，旧密码出错或新密码格式有误", Toast.LENGTH_SHORT).show();
                                    }

                                }
                            });
                        }
                    }
                })
                .setNegativeButton("no", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .create()
                .show();

    }

    private void update() {
        User bmobUser = BmobUser.getCurrentUser(User.class);

        userDataTextView.setText("");
        userDataTextView.append("用户名：" + bmobUser.getUsername() + "\n");
        userDataTextView.append("用户邮箱：" + bmobUser.getEmail() + "\n");
        userDataTextView.append("用户余额：" + bmobUser.getMoney() + "\n");
        Toast.makeText(MyUserActivity.this, "用户信息更新成功", Toast.LENGTH_SHORT).show();
    }

}
