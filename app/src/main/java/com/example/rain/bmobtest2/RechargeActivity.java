package com.example.rain.bmobtest2;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

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
            }
        });

    }

}
