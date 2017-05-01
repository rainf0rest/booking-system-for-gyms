package com.example.rain.bmobtest2;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;

/**
 * Created by rain on 2017/5/1.
 */

public class NewUserDataActivity extends Activity {

    private int head;//头像id本地
    private Button ed_data, ed_password;
    private TextView nick, money, email;
    private ImageView headImage;
    private Handler wHandler;
    private int[] images = new int[] {
            R.drawable.head,
            R.drawable.head1,
            R.drawable.head2,
            R.drawable.head3,
            R.drawable.head4,
            R.drawable.head5
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_data_layout_new);

        ed_data = (Button) findViewById(R.id.btn_edit_data);
        ed_password = (Button) findViewById(R.id.btn_edit_password);
        headImage = (ImageView) findViewById(R.id.ImageView_set_head);

        nick = (TextView) findViewById(R.id.textview_set_nick);
        money = (TextView) findViewById(R.id.textview_set_money);
        email = (TextView) findViewById(R.id.textview_set_email);

        refresh();

        ed_data.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDia();
            }
        });

        ed_password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDiaOfPassword();
            }
        });

        wHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                if (msg.what == 0x901) {
                    refresh();
                    //日志
                    String logid = msg.getData().getString("data");
                    String logContent = msg.getData().getString("cont");
                    AppLog appLog = new AppLog();
                    appLog.setUserID(logid);
                    appLog.setOperate("用户修改个人资料:" + logContent);
                    appLog.save(new SaveListener<String>() {
                        @Override
                        public void done(String s, BmobException e) {

                        }
                    });
                }else if(msg.what == 0x902) {
                    //日志
                    String logid = msg.getData().getString("data");
                    //String logContent = msg.getData().getString("cont");
                    AppLog appLog = new AppLog();
                    appLog.setUserID(logid);
                    appLog.setOperate("用户修改账号密码");
                    appLog.save(new SaveListener<String>() {
                        @Override
                        public void done(String s, BmobException e) {

                        }
                    });
                    Toast.makeText(NewUserDataActivity.this, "修改密码成功", Toast.LENGTH_SHORT).show();
                }
            }


        };

        money.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(NewUserDataActivity.this, RechargeActivity.class);
                startActivity(intent);
            }
        });

        headImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                User user = BmobUser.getCurrentUser(User.class);
                int i = user.getHeadID();
                i = (i + 1)%images.length;
                headImage.setImageResource(images[i]);
                user.setHeadID(i);
                user.update(user.getObjectId(), new UpdateListener() {
                    @Override
                    public void done(BmobException e) {
                        if(e == null) {

                        }
                    }
                });
            }
        });


    }

    private void refresh() {
        User user = BmobUser.getCurrentUser(User.class);
        headImage.setImageResource(images[user.getHeadID()]);
        nick.setText(user.getUsername());
        money.setText(user.getMoney()+ "元");
        email.setText(user.getEmail());
    }

    private void showDia() {
        final View view = View.inflate(NewUserDataActivity.this, R.layout.dialog_of_usedata, null);
        EditText tempname1 = (EditText) view.findViewById(R.id.userData_username);
        EditText tempemail1 = (EditText) view.findViewById(R.id.userData_useremail);
        User user = BmobUser.getCurrentUser(User.class);
        tempname1.setText(user.getUsername());
        tempemail1.setText(user.getEmail());
        //Toast.makeText(MainActivity.this, "edit", Toast.LENGTH_SHORT).show();
        new AlertDialog.Builder(NewUserDataActivity.this)
                .setIcon(R.drawable.edit)
                .setTitle("编辑个人资料")
                .setView(view)
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        EditText tempname = (EditText) view.findViewById(R.id.userData_username);
                        EditText tempemail = (EditText) view.findViewById(R.id.userData_useremail);
                        //Toast.makeText(MyUserActivity.this, "name:" + tempname.getText().toString() + "\nemail:" + tempemail.getText().toString(), Toast.LENGTH_SHORT).show();
                        String newName = tempname.getText().toString();
                        String newEmail = tempemail.getText().toString();
                        if(TextUtils.isEmpty(newEmail) || TextUtils.isEmpty(newName)) {
                            Toast.makeText(NewUserDataActivity.this, "用户名或邮箱不能为空", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        else {
                            User user = BmobUser.getCurrentUser(User.class);
                            user.setUsername(newName);
                            user.setEmail(newEmail);
                            final String logid = user.getObjectId();
                            final String logContent = "n:" + newName + "\ne:" + newEmail;
                            user.update(user.getObjectId(), new UpdateListener() {
                                @Override
                                public void done(BmobException e) {
                                    if(e == null) {
                                        Message msg = new Message();
                                        msg.what = 0x901;
                                        Bundle bundle = new Bundle();
                                        bundle.putString("data", logid);
                                        bundle.putString("cont", logContent);
                                        msg.setData(bundle);
                                        wHandler.sendMessage(msg);
                                    }
                                }
                            });
                        }
                    }
                })
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .create()
                .show();

    }

    private void showDiaOfPassword() {
        final View view = View.inflate(NewUserDataActivity.this, R.layout.dialog_of_password, null);
        //Toast.makeText(MainActivity.this, "edit", Toast.LENGTH_SHORT).show();
        new AlertDialog.Builder(NewUserDataActivity.this)
                .setIcon(R.drawable.password)
                .setTitle("修改密码")
                .setView(view)
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        EditText tempold = (EditText) view.findViewById(R.id.userData_user_oldPassword);
                        EditText tempnew = (EditText) view.findViewById(R.id.userData_user_newPassword);
                        //Toast.makeText(MyUserActivity.this, "name:" + tempname.getText().toString() + "\nemail:" + tempemail.getText().toString(), Toast.LENGTH_SHORT).show();
                        String oldp = tempold.getText().toString();
                        String newp = tempnew.getText().toString();
                        if(TextUtils.isEmpty(newp) || TextUtils.isEmpty(oldp)) {
                            Toast.makeText(NewUserDataActivity.this, "旧密码或新密码不能为空", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        else {
                            User user = BmobUser.getCurrentUser(User.class);
                            final String logid = user.getObjectId();
                            user.updateCurrentUserPassword(oldp, newp, new UpdateListener() {
                                @Override
                                public void done(BmobException e) {
                                    if(e == null){
                                        Message msg = new Message();
                                        msg.what = 0x902;
                                        Bundle bundle = new Bundle();
                                        bundle.putString("data", logid);
                                        msg.setData(bundle);
                                        wHandler.sendMessage(msg);
                                    }else {
                                        Toast.makeText(NewUserDataActivity.this, "修改密码失败，旧密码出错或新密码格式有误", Toast.LENGTH_SHORT).show();
                                    }

                                }
                            });
                        }
                    }
                })
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .create()
                .show();

    }



}
