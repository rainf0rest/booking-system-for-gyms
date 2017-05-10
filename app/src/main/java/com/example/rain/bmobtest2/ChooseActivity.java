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

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.AsyncCustomEndpoints;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.CloudCodeListener;
import cn.bmob.v3.listener.FindListener;

/**
 * Created by rain on 2017/4/23.
 */

public class ChooseActivity extends Activity {

    //private TextView noteTextView;
    private Button toBookbtn, toRechargebtn, toMyUserbtn, toChat, toTest;
    private Handler myWorkHandle;
    private TipView tipView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_choose_layout);

        //noteTextView = (TextView) findViewById(R.id.textview_note);
        toBookbtn = (Button) findViewById(R.id.goto_book);
        toRechargebtn = (Button) findViewById(R.id.goto_recharge);
        toMyUserbtn = (Button) findViewById(R.id.goto_userdata);
        tipView = (TipView) findViewById(R.id.tip_view);
        toChat = (Button) findViewById(R.id.goto_chat);
        toTest = (Button) findViewById(R.id.goto_test);

        //noteTextView.setMovementMethod(ScrollingMovementMethod.getInstance());

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
                    //noteTextView.setText("");
                    List<String> tips = new ArrayList<String>();
                    int i = 1;
                    for(Note note: list) {
                        //noteTextView.append("通知" + i + ":");
                        //noteTextView.append(note.getContent());
                        //noteTextView.append("\n");
                        tips.add("通知" + i + ":" + note.getContent());
                        i++;
                    }
                    tipView.setTipList(tips);
                }
            }
        };

        toBookbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText(ChooseActivity.this, "success to Book System!", Toast.LENGTH_SHORT).show();
                //Intent intent = new Intent(ChooseActivity.this, BookActivity.class);
                Intent intent = new Intent(ChooseActivity.this, NewBookActivity.class);
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

        toChat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ChooseActivity.this, ChatActivity.class);
                startActivity(intent);
            }
        });

        toTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String cloudCodeName = "key_of_eqTime";
                JSONObject params = new JSONObject();
                    //name是上传到云端的参数名称，值是bmob，云端逻辑可以通过调用request.body.name获取这个值

                try {
                    params.put("id", "CuIi444B");
                    params.put("index", 0);
                }
                catch (JSONException e) {
                    Toast.makeText(ChooseActivity.this, e.toString(), Toast.LENGTH_SHORT).show();
                }
                //创建云端逻辑对象
                AsyncCustomEndpoints cloudCode = new AsyncCustomEndpoints();
                    //异步调用云端逻辑
                cloudCode.callEndpoint(cloudCodeName, params, new CloudCodeListener() {
                    @Override
                    public void done(Object o, BmobException e) {
                        if(e == null) {
                            if(o.toString().equals("busy")) {
                                Toast.makeText(ChooseActivity.this, "okokoko", Toast.LENGTH_SHORT).show();
                            }
                            else {
                                Toast.makeText(ChooseActivity.this, "xxxxxxxx" + "\n" + o.toString(), Toast.LENGTH_SHORT).show();
                            }

                        }else {
                            Toast.makeText(ChooseActivity.this, o.toString(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });

    }

}
