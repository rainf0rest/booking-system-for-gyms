package com.example.rain.bmobtest2;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

/**
 * Created by rain on 2017/4/21.
 */

public class BookActivity extends Activity {

    private Button bookbtn, bookedbtn, findbtn;
    private EditText dateEditText;
    private TextView textView;
    ImageView imageBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.book);

        bookbtn = (Button) findViewById(R.id.bookbtn);
        bookedbtn = (Button) findViewById(R.id.bookedbtn);
        findbtn = (Button) findViewById(R.id.findbtn);
        dateEditText = (EditText) findViewById(R.id.dateEdit);
        textView = (TextView) findViewById(R.id.test);
        imageBack = (ImageView) findViewById(R.id.backImageBack);

        Equipment equipment = new Equipment();

        imageBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                Toast.makeText(BookActivity.this, "注销成功！", Toast.LENGTH_SHORT).show();
            }
        });


        findbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                textView.setText(" ");
                BmobQuery<Equipment> query = new BmobQuery<Equipment>();
                String name = dateEditText.getText().toString();
                query.addWhereEqualTo("EqName", name);
                query.findObjects(new FindListener<Equipment>() {
                    @Override
                    public void done(List<Equipment> object, BmobException e) {
                        if(e==null){
                            //toast("查询成功：共"+object.size()+"条数据。");
                            Toast.makeText(BookActivity.this, "查询成功：共"+object.size()+"条数据。", Toast.LENGTH_SHORT).show();
                            for (Equipment equipment : object) {
                                textView.append("name:" + equipment.getEqName() + "\n");
                                textView.append("ID:" + equipment.getEqID() + "\n");
                                textView.append("InID:" + equipment.getEqInID() + "\n");
                                textView.append("number:" + equipment.getEqNumber() + "\n");
                                textView.append("free:" + equipment.getEqfree() + "\n");
                                textView.append("price:" + equipment.getEqprice() + "\n");
                            }
                        }else{
                            //Log.i("bmob","失败："+e.getMessage()+","+e.getErrorCode());
                            Toast.makeText(BookActivity.this, "查询失败。", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });

    }

}
