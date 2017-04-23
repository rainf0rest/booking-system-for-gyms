package com.example.rain.bmobtest2;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
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
        setContentView(R.layout.recharge);

        rechargebtn = (Button) findViewById(R.id.rechargeOk);
        rechargeMoney = (EditText) findViewById(R.id.rechargeMoney);

        rechargebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int money = Integer.parseInt(rechargeMoney.getText().toString());
                Toast.makeText(RechargeActivity.this, "success to recharge " + money + " 元！", Toast.LENGTH_SHORT).show();
                /*
                BmobUser user = BmobUser.getCurrentUser();
                User newUser = new User();
                newUser.setMoney(newUser.getMoney() + money);
                newUser.update(user.getObjectId(), new UpdateListener() {
                    @Override
                    public void done(BmobException e) {
                        if(e==null){
                            Toast.makeText(RechargeActivity.this, "success to recharge ", Toast.LENGTH_SHORT).show();
                        }else{
                            Toast.makeText(RechargeActivity.this, "fail to recharge ", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                 */


            }
        });

    }

}
