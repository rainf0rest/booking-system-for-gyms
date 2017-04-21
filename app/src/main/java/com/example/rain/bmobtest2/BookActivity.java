package com.example.rain.bmobtest2;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    private ListView bookListView;
    private AlertDialog.Builder builder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.book);

        bookbtn = (Button) findViewById(R.id.bookbtn);
        bookedbtn = (Button) findViewById(R.id.bookedbtn);
        findbtn = (Button) findViewById(R.id.findbtn);
        dateEditText = (EditText) findViewById(R.id.eqID);
        textView = (TextView) findViewById(R.id.test);
        imageBack = (ImageView) findViewById(R.id.backImageBack);
        bookListView = (ListView) findViewById(R.id.bookList);

        Equipment equipment = new Equipment();

        imageBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                Toast.makeText(BookActivity.this, "注销成功！", Toast.LENGTH_SHORT).show();
            }
        });

        final List<Map<String, Object>> listems = new ArrayList<Map<String, Object>>();


        findbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listems.clear();
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
                                Map<String, Object> listem = new HashMap<String, Object>();
                                listem.put("ID", equipment.getEqID());
                                listem.put("InID", equipment.getEqInID());
                                listem.put("Name", equipment.getEqName());
                                //listem.put("Number", equipment.getEqNumber());
                                listem.put("Free", equipment.getEqfree());
                                listem.put("Price", equipment.getEqprice());
                                listems.add(listem);
                            }
                            SimpleAdapter simplead = new SimpleAdapter(BookActivity.this, listems,
                                    R.layout.book_list_item, new String[] { "ID", "InID", "Name", "Price", "Free" },
                                    new int[] {R.id.eqID,R.id.eqInID,R.id.eqName, R.id.eqPrice, R.id.eqFree});
                            bookListView.setAdapter(simplead);

                            bookListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                @Override
                                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                                        showBookDia(i);
                                }
                            });
                        }else{
                            //Log.i("bmob","失败："+e.getMessage()+","+e.getErrorCode());
                            Toast.makeText(BookActivity.this, "查询失败。", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });





        /*

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
        });*/

    }

    private void showBookDia(final int postion) {
        builder = new AlertDialog.Builder(this);
        builder.setIcon(R.drawable.ok);
        builder.setTitle(R.string.operate);

        final String[] Items={"7-8","8-9","9-10","10-11","11-12","12-13","13-14",
                "14-15", "15-16", "16-17", "17-18", "18-19", "19-20", "20-21", "21-22"};
        builder.setItems(Items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                //Toast.makeText(getApplicationContext(), "You clicked "+Items[i], Toast.LENGTH_SHORT).show();
                Toast.makeText(BookActivity.this, "预约成功："+""+ "时间为：" + Items[i], Toast.LENGTH_SHORT).show();
            }
        });

        builder.setCancelable(true);
        AlertDialog dialog=builder.create();
        dialog.show();
    }

}
