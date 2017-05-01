package com.example.rain.bmobtest2;

import android.app.Activity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;

/**
 * Created by rain on 2017/4/23.
 */

public class RechargeActivity extends Activity {

    private Button rechargebtn;
    private EditText rechargeMoney;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_recharge_layout);

        rechargebtn = (Button) findViewById(R.id.btn_recharge_ok);
        rechargeMoney = (EditText) findViewById(R.id.et_recharge_money);

        rechargebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String temp = rechargeMoney.getText().toString();
                if(TextUtils.isEmpty(temp)) {
                    Toast.makeText(RechargeActivity.this, "充值不能为空", Toast.LENGTH_SHORT).show();
                }
                else {
                    int money = Integer.parseInt(temp);

                    final User newUser = BmobUser.getCurrentUser(User.class);
                    final String logid = newUser.getObjectId();
                    final String logContent = temp + "元";
                    newUser.setMoney(newUser.getMoney() + money);
                    newUser.update(newUser.getObjectId(), new UpdateListener() {
                        @Override
                        public void done(BmobException e) {
                            if(e==null){
                                //日志
                                AppLog appLog = new AppLog();
                                appLog.setUserID(logid);
                                appLog.setOperate("用户充值：" + logContent);
                                appLog.save(new SaveListener<String>() {
                                    @Override
                                    public void done(String s, BmobException e) {

                                    }
                                });
                                Toast.makeText(RechargeActivity.this, "充值成功", Toast.LENGTH_SHORT).show();
                            }else{
                                Toast.makeText(RechargeActivity.this, "充值失败"  + newUser.getObjectId(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }




            }
        });

    }

}
