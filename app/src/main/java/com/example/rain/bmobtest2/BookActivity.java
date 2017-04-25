package com.example.rain.bmobtest2;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
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
    String myEqID;

    class CalThread extends Thread {
        public Handler myHandler;

        public void run() {
            Looper.prepare();
            myHandler = new Handler() {
                @Override
                public void handleMessage(Message msg) {
                    super.handleMessage(msg);
                    SimpleDateFormat    sDateFormat    =   new SimpleDateFormat("yyyy-MM-dd    hh:mm:ss");
                    String    date    =    sDateFormat.format(new    java.util.Date());
                    if (msg.what == 0x001) {
                        myEqID = msg.getData().getString("data");
                        //Toast.makeText(BookActivity.this, "目标表ID：" + myEqID, Toast.LENGTH_SHORT).show();
                    }
                    else if(msg.what == 0x002) {
                        //Toast.makeText(BookActivity.this, "目标表ID：" + myEqID + "信息0x002" , Toast.LENGTH_SHORT).show();
                        int i = msg.getData().getInt("data");
                        User user = BmobUser.getCurrentUser(User.class);
                        EqBookTime eq = new EqBookTime();
                        BookRecord bookRecord = new BookRecord();
                        switch (i) {
                            case 0:
                                eq.setTime7(user.getObjectId());
                                eq.update(myEqID, new UpdateListener() {
                                    @Override
                                    public void done(BmobException e) {

                                    }
                                });
                                bookRecord.setEqID(eq.getEqID());
                                bookRecord.setUserID(user.getObjectId());
                                bookRecord.setTime("7-8");
                                bookRecord.setDate(date);
                                bookRecord.save(new SaveListener<String>() {
                                    @Override
                                    public void done(String s, BmobException e) {

                                    }
                                });
                                break;
                            case 1:
                                eq.setTime8(user.getObjectId());
                                eq.update(myEqID, new UpdateListener() {
                                    @Override
                                    public void done(BmobException e) {

                                    }
                                });
                                bookRecord.setEqID(eq.getEqID());
                                bookRecord.setUserID(user.getObjectId());
                                bookRecord.setTime("8-9");
                                bookRecord.setDate(date);
                                bookRecord.save(new SaveListener<String>() {
                                    @Override
                                    public void done(String s, BmobException e) {

                                    }
                                });
                                break;
                            case 2:
                                eq.setTime9(user.getObjectId());
                                eq.update(myEqID, new UpdateListener() {
                                    @Override
                                    public void done(BmobException e) {

                                    }
                                });
                                bookRecord.setEqID(eq.getEqID());
                                bookRecord.setUserID(user.getObjectId());
                                bookRecord.setTime("9-10");
                                bookRecord.setDate(date);
                                bookRecord.save(new SaveListener<String>() {
                                    @Override
                                    public void done(String s, BmobException e) {

                                    }
                                });
                                break;
                            case 3:
                                eq.setTime10(user.getObjectId());
                                eq.update(myEqID, new UpdateListener() {
                                    @Override
                                    public void done(BmobException e) {

                                    }
                                });
                                bookRecord.setEqID(eq.getEqID());
                                bookRecord.setUserID(user.getObjectId());
                                bookRecord.setTime("10-11");
                                bookRecord.setDate(date);
                                bookRecord.save(new SaveListener<String>() {
                                    @Override
                                    public void done(String s, BmobException e) {

                                    }
                                });
                                break;
                            case 4:
                                eq.setTime11(user.getObjectId());
                                eq.update(myEqID, new UpdateListener() {
                                    @Override
                                    public void done(BmobException e) {

                                    }
                                });
                                bookRecord.setEqID(eq.getEqID());
                                bookRecord.setUserID(user.getObjectId());
                                bookRecord.setTime("11-12");
                                bookRecord.setDate(date);
                                bookRecord.save(new SaveListener<String>() {
                                    @Override
                                    public void done(String s, BmobException e) {

                                    }
                                });
                                break;
                            case 5:
                                eq.setTime12(user.getObjectId());
                                eq.update(myEqID, new UpdateListener() {
                                    @Override
                                    public void done(BmobException e) {

                                    }
                                });
                                bookRecord.setEqID(eq.getEqID());
                                bookRecord.setUserID(user.getObjectId());
                                bookRecord.setTime("12-13");
                                bookRecord.setDate(date);
                                bookRecord.save(new SaveListener<String>() {
                                    @Override
                                    public void done(String s, BmobException e) {

                                    }
                                });
                                break;
                            case 6:
                                eq.setTime13(user.getObjectId());
                                eq.update(myEqID, new UpdateListener() {
                                    @Override
                                    public void done(BmobException e) {

                                    }
                                });
                                bookRecord.setEqID(eq.getEqID());
                                bookRecord.setUserID(user.getObjectId());
                                bookRecord.setTime("13-14");
                                bookRecord.setDate(date);
                                bookRecord.save(new SaveListener<String>() {
                                    @Override
                                    public void done(String s, BmobException e) {

                                    }
                                });
                                break;
                            case 7:
                                eq.setTime14(user.getObjectId());
                                eq.update(myEqID, new UpdateListener() {
                                    @Override
                                    public void done(BmobException e) {

                                    }
                                });
                                bookRecord.setEqID(eq.getEqID());
                                bookRecord.setUserID(user.getObjectId());
                                bookRecord.setTime("14-15");
                                bookRecord.setDate(date);
                                bookRecord.save(new SaveListener<String>() {
                                    @Override
                                    public void done(String s, BmobException e) {

                                    }
                                });
                                break;
                            case 8:
                                eq.setTime15(user.getObjectId());
                                eq.update(myEqID, new UpdateListener() {
                                    @Override
                                    public void done(BmobException e) {

                                    }
                                });
                                bookRecord.setEqID(eq.getEqID());
                                bookRecord.setUserID(user.getObjectId());
                                bookRecord.setTime("15-16");
                                bookRecord.setDate(date);
                                bookRecord.save(new SaveListener<String>() {
                                    @Override
                                    public void done(String s, BmobException e) {

                                    }
                                });
                                break;
                            case 9:
                                eq.setTime16(user.getObjectId());
                                eq.update(myEqID, new UpdateListener() {
                                    @Override
                                    public void done(BmobException e) {

                                    }
                                });
                                bookRecord.setEqID(eq.getEqID());
                                bookRecord.setUserID(user.getObjectId());
                                bookRecord.setTime("16-17");
                                bookRecord.setDate(date);
                                bookRecord.save(new SaveListener<String>() {
                                    @Override
                                    public void done(String s, BmobException e) {

                                    }
                                });
                                break;
                            case 10:
                                eq.setTime17(user.getObjectId());
                                eq.update(myEqID, new UpdateListener() {
                                    @Override
                                    public void done(BmobException e) {

                                    }
                                });
                                bookRecord.setEqID(eq.getEqID());
                                bookRecord.setUserID(user.getObjectId());
                                bookRecord.setTime("17-18");
                                bookRecord.setDate(date);
                                bookRecord.save(new SaveListener<String>() {
                                    @Override
                                    public void done(String s, BmobException e) {

                                    }
                                });
                                break;
                            case 11:
                                eq.setTime18(user.getObjectId());
                                eq.update(myEqID, new UpdateListener() {
                                    @Override
                                    public void done(BmobException e) {

                                    }
                                });
                                bookRecord.setEqID(eq.getEqID());
                                bookRecord.setUserID(user.getObjectId());
                                bookRecord.setTime("18-19");
                                bookRecord.setDate(date);
                                bookRecord.save(new SaveListener<String>() {
                                    @Override
                                    public void done(String s, BmobException e) {

                                    }
                                });
                                break;
                            case 12:
                                eq.setTime19(user.getObjectId());
                                eq.update(myEqID, new UpdateListener() {
                                    @Override
                                    public void done(BmobException e) {

                                    }
                                });
                                bookRecord.setEqID(eq.getEqID());
                                bookRecord.setUserID(user.getObjectId());
                                bookRecord.setTime("19-20");
                                bookRecord.setDate(date);
                                bookRecord.save(new SaveListener<String>() {
                                    @Override
                                    public void done(String s, BmobException e) {

                                    }
                                });
                                break;
                            case 13:
                                eq.setTime20(user.getObjectId());
                                eq.update(myEqID, new UpdateListener() {
                                    @Override
                                    public void done(BmobException e) {

                                    }
                                });
                                bookRecord.setEqID(eq.getEqID());
                                bookRecord.setUserID(user.getObjectId());
                                bookRecord.setTime("20-21");
                                bookRecord.setDate(date);
                                bookRecord.save(new SaveListener<String>() {
                                    @Override
                                    public void done(String s, BmobException e) {

                                    }
                                });
                                break;
                            case 14:
                                eq.setTime21(user.getObjectId());
                                eq.update(myEqID, new UpdateListener() {
                                    @Override
                                    public void done(BmobException e) {

                                    }
                                });
                                bookRecord.setEqID(eq.getEqID());
                                bookRecord.setUserID(user.getObjectId());
                                bookRecord.setTime("21-22");
                                bookRecord.setDate(date);
                                bookRecord.save(new SaveListener<String>() {
                                    @Override
                                    public void done(String s, BmobException e) {

                                    }
                                });
                                break;
                            default:
                                break;
                        }
                    }
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
        dateEditText = (EditText) findViewById(R.id.eqID);
        textView = (TextView) findViewById(R.id.test);
        imageBack = (ImageView) findViewById(R.id.backImageBack);
        bookListView = (ListView) findViewById(R.id.bookList);

        Equipment equipment = new Equipment();

        imageBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                Toast.makeText(BookActivity.this, "go back！", Toast.LENGTH_SHORT).show();
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
                    public void done(final List<Equipment> object, BmobException e) {
                        if(e==null){
                            //toast("查询成功：共"+object.size()+"条数据。");
                            Toast.makeText(BookActivity.this, "查询成功：共"+object.size()+"条数据。", Toast.LENGTH_SHORT).show();
                            for (Equipment equipment : object) {
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
                                    String id = object.get(i).getObjectId();
                                    showBookDia(i, id);
                                    //Toast.makeText(BookActivity.this, "你选择了器材编号为：" + id + " / " + inId + "的器材", Toast.LENGTH_SHORT).show();
                                }
                            });
                        }else{
                            Toast.makeText(BookActivity.this, "查询失败。", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });


        calThread = new CalThread();
        calThread.start();

    }

    private void showBookDia(final int postion, final String id) {
        builder = new AlertDialog.Builder(this);
        builder.setIcon(R.drawable.ok);
        builder.setTitle(R.string.operate);


        //final EqBookTime eqBookTime = eqBookTimes.get(0);
        //myEqBookTime = eqBookTimes.get(0);
        BmobQuery<EqBookTime> query = new BmobQuery<EqBookTime>();
        query.addWhereEqualTo("EqID", id);
        query.findObjects(new FindListener<EqBookTime>() {
            @Override
            public void done(List<EqBookTime> list, BmobException e) {
                if(e == null){
                    for(EqBookTime eqBookTime : list) {
                        Toast.makeText(getApplicationContext(), "ObjectId: "+ eqBookTime.getObjectId(), Toast.LENGTH_SHORT).show();
                        String eqBid = eqBookTime.getObjectId();
                        Message msg = new Message();
                        msg.what = 0x001;
                        Bundle bundle = new Bundle();
                        bundle.putString("data", eqBid);
                        msg.setData(bundle);
                        calThread.myHandler.sendMessage(msg);
                    }
                }
                else {
                    Toast.makeText(getApplicationContext(), "fail to find the eq: " + id, Toast.LENGTH_SHORT).show();
                }
            }
        });

        final String[] Items={"7-8","8-9","9-10","10-11","11-12","12-13","13-14",
                "14-15", "15-16", "16-17", "17-18", "18-19", "19-20", "20-21", "21-22"};


        //final String[] Items = Items1;

        builder.setItems(Items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                //Toast.makeText(getApplicationContext(), "You clicked "+Items[i], Toast.LENGTH_SHORT).show();
                Message msg = new Message();
                msg.what = 0x002;
                Bundle bundle = new Bundle();
                bundle.putInt("data", i);
                msg.setData(bundle);
                calThread.myHandler.sendMessage(msg);

                //Toast.makeText(BookActivity.this, "预约成功："+""+ "时间为：" + Items[i] + "预约客户：" + id, Toast.LENGTH_SHORT).show();
            }
        });

        builder.setCancelable(true);
        AlertDialog dialog=builder.create();
        dialog.show();
    }

}
