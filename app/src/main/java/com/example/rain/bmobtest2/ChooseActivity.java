package com.example.rain.bmobtest2;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;

/**
 * Created by rain on 2017/4/23.
 */

public class ChooseActivity extends Activity {

    private TextView noteTextView;
    private Button toBookbtn, toRechargebtn, toMyUserbtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.choose);

        noteTextView = (TextView) findViewById(R.id.noteOfPE);
        toBookbtn = (Button) findViewById(R.id.toBook);
        toRechargebtn = (Button) findViewById(R.id.toRecharge);
        toMyUserbtn = (Button) findViewById(R.id.toMyUser);

        toBookbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(ChooseActivity.this, "success to Book System!", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(ChooseActivity.this, BookActivity.class);
                startActivity(intent);
            }
        });

        toRechargebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(ChooseActivity.this, "success to Recharge System!", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(ChooseActivity.this, RechargeActivity.class);
                startActivity(intent);
            }
        });

        toMyUserbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(ChooseActivity.this, "success to User Data System!", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(ChooseActivity.this, MyUserActivity.class);
                startActivity(intent);
            }
        });

    }

}
