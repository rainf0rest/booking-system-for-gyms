package com.example.rain.bmobtest2;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.QueryListener;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;

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
    //static EqBookTime myEqBookTime;
    private CalThread calThread;
    String myEqID, toEquipmentID;
    boolean flag;//booked or book
    private String equipmentName;
    private final String[] Times={"7-8","8-9","9-10","10-11","11-12","12-13","13-14",
            "14-15", "15-16", "16-17", "17-18", "18-19", "19-20", "20-21", "21-22"};
    private final String[] equipmentType = {"跑步机", "哑铃"};
    private Handler myWorkHandle;

    class CalThread extends Thread {
        public Handler myHandler;

        public void run() {
            Looper.prepare();
            myHandler = new Handler() {
                @Override
                public void handleMessage(Message msg) {
                    super.handleMessage(msg);

                }
            };
            Looper.loop();
        }
    }//0x124



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.book);

        bookbtn = (Button) findViewById(R.id.bookbtn);
        bookedbtn = (Button) findViewById(R.id.bookedbtn);
        findbtn = (Button) findViewById(R.id.findbtn);
        //dateEditText = (EditText) findViewById(R.id.eqID);
        textView = (TextView) findViewById(R.id.test);
        imageBack = (ImageView) findViewById(R.id.backImageBack);
        bookListView = (ListView) findViewById(R.id.bookList);

        Equipment equipment = new Equipment();
        final List<Map<String, Object>> listems = new ArrayList<Map<String, Object>>();

        flag = true;

        bookbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                flag = true;
                Toast.makeText(BookActivity.this, "预约界面", Toast.LENGTH_SHORT).show();
                Resources res = getResources( );
                Drawable shape1 = res.getDrawable(R.drawable.shape_normal);
                Drawable shape2 = res.getDrawable(R.drawable.shape_pressed);
                bookbtn.setBackground(shape2);
                bookedbtn.setBackground(shape1);
            }
        });

        bookedbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                flag = false;
                Toast.makeText(BookActivity.this, "已约界面", Toast.LENGTH_SHORT).show();
                Resources res = getResources( );
                Drawable shape1 = res.getDrawable(R.drawable.shape_normal);
                Drawable shape2 = res.getDrawable(R.drawable.shape_pressed);
                bookbtn.setBackground(shape1);
                bookedbtn.setBackground(shape2);
            }
        });

        findbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(flag == true) {
                    //book
                    findDia();
                }
                else{
                    //booked

                }
            }
        });

        myWorkHandle = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                if(msg.what == 0x701) {
                    //book
                    int te = msg.getData().getInt("data");
                    //Toast.makeText(BookActivity.this, "选择的器材是：" + equipmentType[te], Toast.LENGTH_SHORT).show();
                    listems.clear();
                    BmobQuery<Equipment> query = new BmobQuery<Equipment>();
                    query.addWhereEqualTo("EqName", equipmentType[te]);
                    query.findObjects(new FindListener<Equipment>() {
                        @Override
                        public void done(final List<Equipment> list, BmobException e) {
                            if(e == null) {
                                for (Equipment equipment : list) {
                                    Map<String, Object> listem = new HashMap<String, Object>();
                                    listem.put("ID", "器材编号：" + equipment.getEqID());
                                    listem.put("InID", "内部编号：" + equipment.getEqInID());
                                    listem.put("Name",  equipment.getEqName());
                                    //listem.put("Number", equipment.getEqNumber());
                                    if(equipment.getEqfree() == 1)
                                        listem.put("Free", "可用");
                                    else
                                        listem.put("Free", "不可用");
                                    listem.put("Price", "器材租价：" + equipment.getEqprice() + "元");
                                    listems.add(listem);
                                }
                                SimpleAdapter simplead = new SimpleAdapter(BookActivity.this, listems,
                                        R.layout.book_list_item, new String[] { "ID", "InID", "Name", "Price", "Free" },
                                        new int[] {R.id.eqID,R.id.eqInID,R.id.eqName, R.id.eqPrice, R.id.eqFree});
                                bookListView.setAdapter(simplead);
                                bookListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                    @Override
                                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                                        //int id = object.get(i).getEqID();
                                        //int inId = object.get(i).getEqInID();
                                        String id = list.get(i).getObjectId();
                                        //Toast.makeText(BookActivity.this, "1器材id是：" + id, Toast.LENGTH_SHORT).show();
                                        String na = list.get(i).getEqName();
                                        Message msg = myWorkHandle.obtainMessage();
                                        msg.what = 0x702;
                                        Bundle bundle = new Bundle();
                                        bundle.putString("data", id);
                                        bundle.putString("eqname", na);
                                        msg.setData(bundle);
                                        myWorkHandle.sendMessage(msg);
                                        //Toast.makeText(BookActivity.this, "你选择了器材编号为：" + id + " / " + inId + "的器材", Toast.LENGTH_SHORT).show();
                                    }
                                });

                            }
                            else {

                            }
                        }
                    });

                }
                else if(msg.what == 0x702) {
                    String id = msg.getData().getString("data");
                    String na = msg.getData().getString("eqname");
                    //Toast.makeText(BookActivity.this, "0x702", Toast.LENGTH_SHORT).show();
                    //Toast.makeText(BookActivity.this, "2器材id是：" + id, Toast.LENGTH_SHORT).show();
                    getcurTimes(id, na);
                }
                else if(msg.what == 0x703) {
                    String[] temp = (String[]) msg.obj;
                    String id = msg.getData().getString("data");
                    String eqid = msg.getData().getString("eqid");
                    String na = msg.getData().getString("eqname");
                    //Toast.makeText(BookActivity.this, "0x703", Toast.LENGTH_SHORT).show();
                    //Toast.makeText(BookActivity.this, "4器材id是：" + id, Toast.LENGTH_SHORT).show();
                    showBookDia(id, temp, na, eqid);
                }
                else if(msg.what == 0x704) {
                    int i = msg.getData().getInt("data");
                    String s = msg.getData().getString("id");
                    String st = msg.getData().getString("timeid");
                    String na = msg.getData().getString("eqname");
                    User user = BmobUser.getCurrentUser(User.class);
                    EqBookTime eq = new EqBookTime();
                    BookRecord bookRecord = new BookRecord();
                    eq.setTimeOfN(i, user.getObjectId());
                    eq.update(st, new UpdateListener() {
                        @Override
                        public void done(BmobException e) {
                            if(e == null) {
                                //Toast.makeText(BookActivity.this, "suc：", Toast.LENGTH_SHORT).show();
                            }
                            else {
                                Toast.makeText(BookActivity.this, "error：xx\n" + e.toString(), Toast.LENGTH_LONG).show();
                            }
                        }
                    });

                    bookRecord.setUserID(user.getObjectId());
                    bookRecord.setEqName(na);
                    bookRecord.setTime(Times[i]);
                    bookRecord.setEqID(s);
                    SimpleDateFormat    sDateFormat    =   new SimpleDateFormat("yyyy-MM-dd");
                    String    date    =    sDateFormat.format(new    java.util.Date());
                    bookRecord.setDate(date);

                    bookRecord.save(new SaveListener<String>() {
                        @Override
                        public void done(String s, BmobException e) {

                        }
                    });
                }

            }
        };

        calThread = new CalThread();
        calThread.start();

    }

    private void findDia() {

        new AlertDialog.Builder(this).setTitle("器材名称").setIcon(R.drawable.ok).setSingleChoiceItems(
                equipmentType, 0,
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        Message msg = myWorkHandle.obtainMessage();
                        msg.what = 0x701;
                        Bundle bundle = new Bundle();
                        bundle.putInt("data", which);
                        msg.setData(bundle);
                        myWorkHandle.sendMessage(msg);
                        dialog.dismiss();
                    }
                })
                .setNegativeButton("取消", null).show();

    }

    private void showBookDia(final String id, final String[] s, final String na, final String eqid) {
        new AlertDialog.Builder(this)
                .setTitle("预约时段")
                .setIcon(R.drawable.ok)
                .setSingleChoiceItems(s, 0,
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,
                                                int which) {
                                String ts = Times[which] + "可约";
                                if(s[which].equals(ts)){
                                    Toast.makeText(BookActivity.this, "可约", Toast.LENGTH_SHORT).show();
                                    Message msg = myWorkHandle.obtainMessage();
                                    msg.what = 0x704;
                                    Bundle bundle = new Bundle();
                                    bundle.putInt("data", which);
                                    bundle.putString("id", eqid);
                                    bundle.putString("timeid", id);
                                    //Toast.makeText(BookActivity.this, "5器材id是：" + eqid, Toast.LENGTH_SHORT).show();
                                    bundle.putString("eqname", na);
                                    msg.setData(bundle);
                                    myWorkHandle.sendMessage(msg);
                                }
                                else {
                                    Toast.makeText(BookActivity.this,  s[which], Toast.LENGTH_SHORT).show();
                                }
                                dialog.dismiss();
                            }
                        }).setNegativeButton("取消", null).show();
    }

    private void getcurTimes(final String id, final String na) {
        final String[] mys = new String[15];
        BmobQuery<EqBookTime> query = new BmobQuery<EqBookTime>();
        query.addWhereEqualTo("EqID", id);
        query.findObjects(new FindListener<EqBookTime>() {
            @Override
            public void done(List<EqBookTime> list, BmobException e) {
                if(e == null) {

                    EqBookTime eqBookTime = list.get(0);
                    for(int i = 0; i < 15; i++) {
                        mys[i] = Times[i] + eqBookTime.getStringOfTimes(i);
                        //Toast.makeText(BookActivity.this, mys[i], Toast.LENGTH_SHORT).show();
                    }
                    Message msg = myWorkHandle.obtainMessage();
                    msg.what = 0x703;
                    Bundle bundle = new Bundle();
                    bundle.putString("data", eqBookTime.getObjectId());
                    bundle.putString("eqid", id);
                    bundle.putString("eqname", na);
                    //Toast.makeText(BookActivity.this, "3器材id是：" + id, Toast.LENGTH_SHORT).show();
                    msg.setData(bundle);
                    msg.obj = mys;
                    myWorkHandle.sendMessage(msg);
                }
                else {
                    Toast.makeText(BookActivity.this, "error", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }


}
