package com.example.rain.bmobtest2;

import android.app.Activity;
import android.app.DownloadManager;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;

/**
 * Created by rain on 2017/5/9.
 */

public class ChatActivity extends Activity {
    private TextView chatbtn;
    private EditText etChat;
    private List<ChatEntity> mChatData;
    private ChatAdapter mChatAdapter;
    private ListView mListView;
    private int[] images = new int[] {
            R.drawable.head,
            R.drawable.head1,
            R.drawable.head2,
            R.drawable.head3,
            R.drawable.head4,
            R.drawable.head5
    };
    private String curUserObjID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chat_layout);

        //chatList = (TextView) findViewById(R.id.chat_test);
        chatbtn = (TextView) findViewById(R.id.chat_btn);
        etChat = (EditText) findViewById(R.id.et_chat_content);
        initData();
        mListView = (ListView) findViewById(R.id.myChatList);
        mChatAdapter = new ChatAdapter(this, mChatData);
        mListView.setAdapter(mChatAdapter);
        mChatData = new ArrayList<ChatEntity>();

        chatbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String s = etChat.getText().toString();
                if(!s.isEmpty()) {
                    User user = BmobUser.getCurrentUser(User.class);
                    ChatItem chatItem = new ChatItem();
                    chatItem.setUserID(user.getObjectId());
                    chatItem.setUserName(user.getUsername());
                    chatItem.setUserHead(user.getHeadID());
                    chatItem.setId(0);
                    chatItem.setContent(s);
                    chatItem.save(new SaveListener<String>() {
                        @Override
                        public void done(String s, BmobException e) {
                            if(e == null) {
                                Toast.makeText(ChatActivity.this, "发送数据成功", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });

                }
            }
        });

        final Handler handler=new Handler();
        Runnable runnable=new Runnable() {
            @Override
            public void run() {
                // TODO Auto-generated method stub
                //要做的事情
                //chatList.setText("");
                mChatData.clear();
                BmobQuery<ChatItem> query = new BmobQuery<ChatItem>();
                query.addWhereEqualTo("Id", 0);
                final User user = BmobUser.getCurrentUser(User.class);
                final String id = curUserObjID;
                query.findObjects(new FindListener<ChatItem>() {
                    @Override
                    public void done(List<ChatItem> list, BmobException e) {
                        if(e == null) {
                            for(ChatItem chatItem : list) {
                                //chatList.append(chatItem.getUserName() + ":" + chatItem.getContent() + "\n");
                                ChatEntity chat = new ChatEntity();
                                if(chatItem.getUserID().equals(id)) {
                                    chat.setmType(0);
                                }
                                else {
                                    chat.setmType(1);
                                }
                                chat.setmPic(images[chatItem.getUserHead()]);
                                chat.setmText(chatItem.getContent());
                                mChatData.add(chat);
                            }
                            ChatAdapter xChatAdapter = new ChatAdapter(ChatActivity.this, mChatData);
                            mListView.setAdapter(xChatAdapter);
                        }
                    }
                });

                handler.postDelayed(this, 2000);
            }
        };

        //handler.postDelayed(runnable, 5000);//每两秒执行一次runnable.

    }


    private void getChatListFromBmob() {
        //chatList.setText("");
        BmobQuery<ChatItem> query = new BmobQuery<ChatItem>();
        query.addWhereEqualTo("Id", 0);
        query.findObjects(new FindListener<ChatItem>() {
            @Override
            public void done(List<ChatItem> list, BmobException e) {
                if(e == null) {
                    for(ChatItem chatItem : list) {
                        //chatList.append(chatItem.getUserName() + ":" + chatItem.getContent() + "\n");
                    }
                }
            }
        });
    }

    private void initData() {
        //mChatData.clear();
        BmobQuery<ChatItem> query = new BmobQuery<ChatItem>();
        query.addWhereEqualTo("Id", 0);
        final User xxUser = BmobUser.getCurrentUser(User.class);
        query.findObjects(new FindListener<ChatItem>() {
            @Override
            public void done(List<ChatItem> list, BmobException e) {
                if(e == null) {
                    for(ChatItem chatItem : list) {
                        //chatList.append(chatItem.getUserName() + ":" + chatItem.getContent() + "\n");
                        ChatEntity chat = new ChatEntity();
                        if(chatItem.getUserID().equals(xxUser.getObjectId())) {
                            chat.setmType(1);
                        }
                        else {
                            chat.setmType(0);
                        }
                        Toast.makeText(ChatActivity.this, "id:" + xxUser.getObjectId() + "\n" + chatItem.getUserID(), Toast.LENGTH_SHORT).show();
                        chat.setmPic(images[chatItem.getUserHead()]);
                        chat.setmText(chatItem.getContent());
                        mChatData.add(chat);
                    }
                    ChatAdapter xChatAdapter = new ChatAdapter(ChatActivity.this, mChatData);
                    mListView.setAdapter(xChatAdapter);
                }
            }
        });
    }


}
