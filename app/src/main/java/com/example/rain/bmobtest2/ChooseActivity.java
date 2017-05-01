package com.example.rain.bmobtest2;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

/**
 * Created by rain on 2017/4/23.
 */

public class ChooseActivity extends Activity {

    private TextView noteTextView;
    private Button toBookbtn, toRechargebtn, toMyUserbtn;
    private Handler myWorkHandle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.choose);

        noteTextView = (TextView) findViewById(R.id.noteOfPE);
        toBookbtn = (Button) findViewById(R.id.toBook);
        toRechargebtn = (Button) findViewById(R.id.toRecharge);
        toMyUserbtn = (Button) findViewById(R.id.toMyUser);

        noteTextView.setMovementMethod(ScrollingMovementMethod.getInstance());

        BmobQuery<Note> query = new BmobQuery<>();
        query.addWhereEqualTo("Available", true);
        query.findObjects(new FindListener<Note>() {
            @Override
            public void done(List<Note> list, BmobException e) {
                if(e == null) {
                    Message msg = myWorkHandle.obtainMessage();
                    msg.obj = list;
                    msg.what = 0x601;
                    myWorkHandle.sendMessage(msg);

                }
                else {

                }
            }
        });

        myWorkHandle = new Handler() {
            @Override
            public void handleMessage (Message msg){
                super.handleMessage(msg);
                if (msg.what == 0x601) {
                    List<Note> list = (List<Note>) msg.obj;
                    noteTextView.setText("");
                    int i = 1;
                    for(Note note: list) {
                        noteTextView.append("通知" + i + ":");
                        noteTextView.append(note.getContent());
                        noteTextView.append("\n");
                        i++;
                    }
                }
            }
        };

        toBookbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText(ChooseActivity.this, "success to Book System!", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(ChooseActivity.this, BookActivity.class);
                startActivity(intent);
            }
        });

        toRechargebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText(ChooseActivity.this, "success to Recharge System!", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(ChooseActivity.this, RechargeActivity.class);
                startActivity(intent);
            }
        });

        toMyUserbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText(ChooseActivity.this, "success to User Data System!", Toast.LENGTH_SHORT).show();
                //Intent intent = new Intent(ChooseActivity.this, MyUserActivity.class);
                Intent intent = new Intent(ChooseActivity.this, NewUserDataActivity.class);
                startActivity(intent);
            }
        });

    }

}
