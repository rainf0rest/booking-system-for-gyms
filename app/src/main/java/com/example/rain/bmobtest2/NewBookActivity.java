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
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;
import cn.bmob.v3.socketio.callback.StringCallback;

/**
 * Created by rain on 2017/5/1.
 */

public class NewBookActivity extends Activity {

    private Button bookbtn, bookedbtn, findbtn;
    private EditText dateEditText;
    private TextView textView;
    ImageView imageBack;
    private ListView bookListView;
    private AlertDialog.Builder builder;
    //static EqBookTime myEqBookTime;
    private NewBookActivity.CalThread calThread;
    String myEqID, toEquipmentID;
    boolean flag;//booked or book
    private String equipmentName;
    private final String[] Times={"7-8","8-9","9-10","10-11","11-12","12-13","13-14",
            "14-15", "15-16", "16-17", "17-18", "18-19", "19-20", "20-21", "21-22"};
    private final String[] TimeOfclock = {"70000","80000","90000","100000","110000","120000","130000",
            "140000","150000","160000","170000","180000","190000","200000","210000"};
    private final String[] equipmentType = {"跑步机", "哑铃", "杠铃"};
    private Handler myWorkHandle;
    private final List<Map<String, Object>> listems = new ArrayList<Map<String, Object>>();


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
        setContentView(R.layout.new_book_layout);

        bookbtn = (Button) findViewById(R.id.book_btn);
        bookedbtn = (Button) findViewById(R.id.booked_btn);
        findbtn = (Button) findViewById(R.id.type_btn);
        //dateEditText = (EditText) findViewById(R.id.eqID);
        //textView = (TextView) findViewById(R.id.test);
        //imageBack = (ImageView) findViewById(R.id.backImageBack);
        bookListView = (ListView) findViewById(R.id.book_list);

        Equipment equipment = new Equipment();


        flag = true;

        bookbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                flag = true;
                Toast.makeText(NewBookActivity.this, "预约界面", Toast.LENGTH_SHORT).show();
                Resources res = getResources( );
                Drawable shape1 = res.getDrawable(R.drawable.shape_normal);
                Drawable shape2 = res.getDrawable(R.drawable.shape_pressed);
                bookbtn.setBackground(shape2);
                bookedbtn.setBackground(shape1);
                findAllBookList();
            }
        });

        bookedbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                flag = false;
                Toast.makeText(NewBookActivity.this, "已约界面", Toast.LENGTH_SHORT).show();
                Resources res = getResources( );
                Drawable shape1 = res.getDrawable(R.drawable.shape_normal);
                Drawable shape2 = res.getDrawable(R.drawable.shape_pressed);
                bookbtn.setBackground(shape1);
                bookedbtn.setBackground(shape2);
                refreshBooked();
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
                    findDiax();

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
                    //Toast.makeText(NewBookActivity.this, "选择的器材是：" + equipmentType[te], Toast.LENGTH_SHORT).show();
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
                                SimpleAdapter simplead = new SimpleAdapter(NewBookActivity.this, listems,
                                        R.layout.book_list_item, new String[] { "ID", "InID", "Name", "Price", "Free" },
                                        new int[] {R.id.eqID,R.id.eqInID,R.id.eqName, R.id.eqPrice, R.id.eqFree});
                                bookListView.setAdapter(simplead);

                                bookListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                    @Override
                                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                                        //int id = object.get(i).getEqID();
                                        //int inId = object.get(i).getEqInID();
                                        String id = list.get(i).getObjectId();
                                        //Toast.makeText(NewBookActivity.this, "1器材id是：" + id, Toast.LENGTH_SHORT).show();
                                        String na = list.get(i).getEqName() + "" + list.get(i).getEqID() + "-" + list.get(i).getEqInID();
                                        int pri = list.get(i).getEqprice();

                                        Message msg = myWorkHandle.obtainMessage();
                                        msg.what = 0x702;
                                        Bundle bundle = new Bundle();
                                        bundle.putString("data", id);
                                        bundle.putString("eqname", na);
                                        bundle.putInt("price", pri);
                                        msg.setData(bundle);
                                        myWorkHandle.sendMessage(msg);
                                        //Toast.makeText(NewBookActivity.this, "你选择了器材编号为：" + id + " / " + inId + "的器材", Toast.LENGTH_SHORT).show();
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
                    int pri = msg.getData().getInt("price");
                    //Toast.makeText(NewBookActivity.this, "0x702", Toast.LENGTH_SHORT).show();
                    //Toast.makeText(NewBookActivity.this, "2器材id是：" + id, Toast.LENGTH_SHORT).show();
                    getcurTimes(id, na, pri);
                }
                else if(msg.what == 0x703) {
                    String[] temp = (String[]) msg.obj;
                    String id = msg.getData().getString("data");
                    String eqid = msg.getData().getString("eqid");
                    String na = msg.getData().getString("eqname");
                    int pri = msg.getData().getInt("price");
                    //Toast.makeText(NewBookActivity.this, "0x703", Toast.LENGTH_SHORT).show();
                    //Toast.makeText(NewBookActivity.this, "4器材id是：" + id, Toast.LENGTH_SHORT).show();
                    showBookDia(id, temp, na, eqid, pri);
                }
                else if(msg.what == 0x704) {
                    int i = msg.getData().getInt("data");
                    String s = msg.getData().getString("id");
                    String st = msg.getData().getString("timeid");
                    String na = msg.getData().getString("eqname");
                    int pri = msg.getData().getInt("price");
                    User user = BmobUser.getCurrentUser(User.class);
                    EqBookTime eq = new EqBookTime();
                    BookRecord bookRecord = new BookRecord();
                    eq.setTimeOfN(i, user.getObjectId());
                    eq.update(st, new UpdateListener() {
                        @Override
                        public void done(BmobException e) {
                            if(e == null) {
                                //Toast.makeText(NewBookActivity.this, "suc：", Toast.LENGTH_SHORT).show();
                            }
                            else {
                                Toast.makeText(NewBookActivity.this, "error：xx\n" + e.toString(), Toast.LENGTH_LONG).show();
                            }
                        }
                    });

                    bookRecord.setUserID(user.getObjectId());
                    bookRecord.setEqName(na);
                    bookRecord.setTime(Times[i]);
                    bookRecord.setEqID(s);
                    bookRecord.setPrice(pri);
                    SimpleDateFormat sDateFormat    =   new SimpleDateFormat("yyyyMMdd");
                    String    date    =    sDateFormat.format(new    java.util.Date());
                    bookRecord.setDate(date);

                    bookRecord.save(new SaveListener<String>() {
                        @Override
                        public void done(String s, BmobException e) {

                        }
                    });

                    int tp = user.getMoney() - pri;
                    user.setMoney(tp);
                    final String logid = user.getObjectId();
                    final String logContent = bookRecord.getEqName();
                    user.update(user.getObjectId(), new UpdateListener() {
                        @Override
                        public void done(BmobException e) {
                            if(e == null) {
                                //日志
                                AppLog appLog = new AppLog();
                                appLog.setUserID(logid);
                                appLog.setOperate("用户预约：" + logContent);
                                appLog.save(new SaveListener<String>() {
                                    @Override
                                    public void done(String s, BmobException e) {

                                    }
                                });
                                Toast.makeText(NewBookActivity.this, "预约成功", Toast.LENGTH_SHORT).show();
                            }
                            else {

                            }
                        }
                    });

                }
                else if(msg.what == 0x705) {
                    int te = msg.getData().getInt("data");
                    listems.clear();
                    BmobQuery<BookRecord> query = new BmobQuery<BookRecord>();
                    final User user = BmobUser.getCurrentUser(User.class);
                    query.addWhereEqualTo("UserID", user.getObjectId());
                    query.addWhereEqualTo("EqName", equipmentType[te]);
                    query.findObjects(new FindListener<BookRecord>() {
                        @Override
                        public void done(final List<BookRecord> list, BmobException e) {
                            if(e == null) {
                                for(BookRecord bookRecord : list) {
                                    //Toast.makeText(NewBookActivity.this, "list.size():" + list.size(), Toast.LENGTH_SHORT).show();
                                    if(list.isEmpty()) {
                                        Toast.makeText(NewBookActivity.this, "没有该项器材的预约记录", Toast.LENGTH_SHORT).show();
                                    }
                                    else {
                                        Map<String, Object> listem = new HashMap<String, Object>();
                                        listem.put("EqName", "" + bookRecord.getEqName());
                                        //Toast.makeText(NewBookActivity.this, equipmentName, Toast.LENGTH_SHORT).show();

                                        listem.put("Time", "时段:" + bookRecord.getTime());
                                        listem.put("Date", "日期:" + bookRecord.getDate());
                                        listems.add(listem);
                                        SimpleAdapter simplead = new SimpleAdapter(NewBookActivity.this, listems,
                                                R.layout.book_list_item2, new String[] { "EqName","Time", "Date"},
                                                new int[] {R.id.list_item_eqName,R.id.list_item_time, R.id.list_item_date});
                                        bookListView.setAdapter(simplead);

                                        bookListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                            @Override
                                            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                                                showbookedDia(list.get(i));
                                                //Toast.makeText(NewBookActivity.this, "你选择了器材编号为：" + id + " / " + inId + "的器材", Toast.LENGTH_SHORT).show();
                                            }
                                        });
                                    }


                                }
                            }
                            else{
                                Toast.makeText(NewBookActivity.this, "没有该项器材的预约记录2", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
                else if (msg.what == 0x706){
                    final String logContent = msg.getData().getString("eqName");
                    String id = msg.getData().getString("recordId");
                    final int t = msg.getData().getInt("time");
                    int pri = msg.getData().getInt("price");
                    EqBookTime eqBookTime = (EqBookTime) msg.obj;
                    eqBookTime.setTimeOfN(t, "-1");
                    eqBookTime.update(new UpdateListener() {
                        @Override
                        public void done(BmobException e) {
                            if(e == null) {
                                //Toast.makeText(NewBookActivity.this, "succ: \nindex:" + t , Toast.LENGTH_SHORT).show();
                            }
                            else {
                                Toast.makeText(NewBookActivity.this, "error：\n" + e.toString(), Toast.LENGTH_LONG).show();
                            }
                        }
                    });

                    BookRecord bookRecord = new BookRecord();
                    bookRecord.setObjectId(id);
                    bookRecord.delete(new UpdateListener() {
                        @Override
                        public void done(BmobException e) {
                            if(e == null) {

                            }
                            else {

                            }
                        }
                    });

                    User user = BmobUser.getCurrentUser(User.class);
                    user.setMoney(user.getMoney() + pri);
                    final String logid = user.getObjectId();
                    user.update(user.getObjectId(), new UpdateListener() {
                        @Override
                        public void done(BmobException e) {
                            if(e == null) {
                                //日志
                                AppLog appLog = new AppLog();
                                appLog.setUserID(logid);
                                appLog.setOperate("用户取消预约：" + logContent);
                                appLog.save(new SaveListener<String>() {
                                    @Override
                                    public void done(String s, BmobException e) {

                                    }
                                });
                                Toast.makeText(NewBookActivity.this, "取消预约成功", Toast.LENGTH_SHORT).show();
                            }
                            else {

                            }
                        }
                    });


                    refreshBooked();

                }
                else if(msg.what == 0x707) {
                    //refresh booked
                    //int te = msg.getData().getInt("data");

                }

            }
        };

        calThread = new NewBookActivity.CalThread();
        calThread.start();

    }

    private void findAllBookList() {
        listems.clear();
        BmobQuery<Equipment> query = new BmobQuery<Equipment>();
        query.addWhereEqualTo("Eqfree", 1);
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
                    SimpleAdapter simplead = new SimpleAdapter(NewBookActivity.this, listems,
                            R.layout.book_list_item, new String[] { "ID", "InID", "Name", "Price", "Free" },
                            new int[] {R.id.eqID,R.id.eqInID,R.id.eqName, R.id.eqPrice, R.id.eqFree});
                    bookListView.setAdapter(simplead);

                    bookListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                            //int id = object.get(i).getEqID();
                            //int inId = object.get(i).getEqInID();
                            String id = list.get(i).getObjectId();
                            //Toast.makeText(NewBookActivity.this, "1器材id是：" + id, Toast.LENGTH_SHORT).show();
                            String na = list.get(i).getEqName() + "" + list.get(i).getEqID() + "-" + list.get(i).getEqInID();
                            int pri = list.get(i).getEqprice();

                            Message msg = myWorkHandle.obtainMessage();
                            msg.what = 0x702;
                            Bundle bundle = new Bundle();
                            bundle.putString("data", id);
                            bundle.putString("eqname", na);
                            bundle.putInt("price", pri);
                            msg.setData(bundle);
                            myWorkHandle.sendMessage(msg);
                            //Toast.makeText(NewBookActivity.this, "你选择了器材编号为：" + id + " / " + inId + "的器材", Toast.LENGTH_SHORT).show();
                        }
                    });

                }
                else {

                }
            }
        });
    }

    private void refreshBooked() {
        listems.clear();
        BmobQuery<BookRecord> query = new BmobQuery<BookRecord>();
        final User user = BmobUser.getCurrentUser(User.class);
        query.addWhereEqualTo("UserID", user.getObjectId());
        //query.addWhereEqualTo("EqName", equipmentType[te]);
        query.findObjects(new FindListener<BookRecord>() {
            @Override
            public void done(final List<BookRecord> list, BmobException e) {
                if(e == null) {
                    for(BookRecord bookRecord : list) {
                        Map<String, Object> listem = new HashMap<String, Object>();
                        listem.put("EqName", "" + bookRecord.getEqName());
                        //Toast.makeText(NewBookActivity.this, equipmentName, Toast.LENGTH_SHORT).show();

                        listem.put("Time", "时段:" + bookRecord.getTime());
                        listem.put("Date", "日期:" + bookRecord.getDate());
                        listems.add(listem);
                        SimpleAdapter simplead = new SimpleAdapter(NewBookActivity.this, listems,
                                R.layout.book_list_item2, new String[] { "EqName","Time", "Date"},
                                new int[] {R.id.list_item_eqName,R.id.list_item_time, R.id.list_item_date});
                        bookListView.setAdapter(simplead);

                        bookListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                                showbookedDia(list.get(i));
                                //Toast.makeText(NewBookActivity.this, "你选择了器材编号为：" + id + " / " + inId + "的器材", Toast.LENGTH_SHORT).show();
                            }
                        });

                    }
                }
                else{

                }
            }
        });
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

    private void findDiax() {
        new AlertDialog.Builder(this).setTitle("器材名称").setIcon(R.drawable.ok).setSingleChoiceItems(
                equipmentType, 0,
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        Message msg = myWorkHandle.obtainMessage();
                        msg.what = 0x705;
                        Bundle bundle = new Bundle();
                        bundle.putInt("data", which);
                        msg.setData(bundle);
                        myWorkHandle.sendMessage(msg);
                        dialog.dismiss();
                    }
                })
                .setNegativeButton("取消", null).show();
    }

    private void showbookedDia(final BookRecord bookRecord) {
        builder = new AlertDialog.Builder(this);
        builder.setIcon(R.drawable.ok);
        builder.setTitle(R.string.booked_operate);
        builder.setMessage("确定取消预约吗？");
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(final DialogInterface dialogInterface, int i) {
                final int ix = getindex(bookRecord.getTime());

                SimpleDateFormat    sDateFormat1    =   new SimpleDateFormat("HHmmss");
                String    cur   =    sDateFormat1.format(new    java.util.Date());
                String mud = TimeOfclock[ix];

                SimpleDateFormat    sDateFormat2    =   new SimpleDateFormat("yyyyMMdd");
                String curye = sDateFormat2.format(new    java.util.Date());
                String mudye = bookRecord.getDate();

                int a = Integer.parseInt(mud);
                int b = Integer.parseInt(cur);
                int c = Integer.parseInt(mudye);
                int d = Integer.parseInt(curye);

                if(c > d && a > b) {
                    //Toast.makeText(NewBookActivity.this, "可以", Toast.LENGTH_LONG).show();
                    BmobQuery<EqBookTime> query = new BmobQuery<EqBookTime>();
                    query.addWhereEqualTo("EqID", bookRecord.getEqID());
                    query.findObjects(new FindListener<EqBookTime>() {
                        @Override
                        public void done(List<EqBookTime> list, BmobException e) {
                            if(e == null) {
                                Message msg = myWorkHandle.obtainMessage();
                                msg.what = 0x706;
                                msg.obj = list.get(0);
                                Bundle bundle = new Bundle();
                                bundle.putString("eqName", bookRecord.getEqName());
                                bundle.putInt("time", ix);
                                bundle.putInt("price", bookRecord.getPrice());
                                bundle.putString("recordId", bookRecord.getObjectId());
                                msg.setData(bundle);
                                myWorkHandle.sendMessage(msg);
                                dialogInterface.dismiss();
                            }
                            else {

                            }
                        }
                    });

                }
                else {
                    Toast.makeText(NewBookActivity.this, "预约时段已过，不可以取消", Toast.LENGTH_LONG).show();
                }

            }
        });
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        builder.create().show();

    }

    private int getindex(String s) {
        int t = 0;
        for(int i = 0; i < Times.length; i++) {
            if(s.equals(Times[i])) {
                t = i;
            }
        }
        return t;
    }

    private void showBookDia(final String id, final String[] s, final String na, final String eqid, final int p) {
        new AlertDialog.Builder(this)
                .setTitle(R.string.operate)
                .setIcon(R.drawable.ok)
                .setSingleChoiceItems(s, 0,
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,
                                                int which) {
                                User user = BmobUser.getCurrentUser(User.class);
                                if(user.getMoney() < p) {
                                    Toast.makeText(NewBookActivity.this, "余额不足请及时充值", Toast.LENGTH_SHORT).show();
                                    dialog.dismiss();
                                }
                                else {
                                    String ts = Times[which] + "可约";
                                    if(s[which].equals(ts)){
                                        //Toast.makeText(NewBookActivity.this, "可约", Toast.LENGTH_SHORT).show();
                                        Message msg = myWorkHandle.obtainMessage();
                                        msg.what = 0x704;
                                        Bundle bundle = new Bundle();
                                        bundle.putInt("data", which);
                                        bundle.putString("id", eqid);
                                        bundle.putString("timeid", id);
                                        //Toast.makeText(NewBookActivity.this, "5器材id是：" + eqid, Toast.LENGTH_SHORT).show();
                                        bundle.putString("eqname", na);
                                        bundle.putInt("price", p);
                                        msg.setData(bundle);
                                        myWorkHandle.sendMessage(msg);
                                    }
                                    else {
                                        Toast.makeText(NewBookActivity.this,  s[which], Toast.LENGTH_SHORT).show();
                                    }
                                    dialog.dismiss();
                                }

                            }
                        }).setNegativeButton("取消", null).show();
    }

    private void getcurTimes(final String id, final String na, final int p) {
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
                        //Toast.makeText(NewBookActivity.this, mys[i], Toast.LENGTH_SHORT).show();
                    }
                    Message msg = myWorkHandle.obtainMessage();
                    msg.what = 0x703;
                    Bundle bundle = new Bundle();
                    bundle.putString("data", eqBookTime.getObjectId());
                    bundle.putString("eqid", id);
                    bundle.putString("eqname", na);
                    bundle.putInt("price", p);
                    //Toast.makeText(NewBookActivity.this, "3器材id是：" + id, Toast.LENGTH_SHORT).show();
                    msg.setData(bundle);
                    msg.obj = mys;
                    myWorkHandle.sendMessage(msg);
                }
                else {
                    Toast.makeText(NewBookActivity.this, "error", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }


}
