package com.example.rain.bmobtest2;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by rain on 2017/5/9.
 */

public class ChatAdapter extends MyBaseAdapter {
    private List<ChatEntity> mChatData;
    private LayoutInflater mInflater;

    public ChatAdapter(Context mContext, List<ChatEntity> mChatItemDatas) {
        super(mChatItemDatas, mContext);
        this.mChatData = mChatItemDatas;
        mInflater = LayoutInflater.from(mContext);
    }

    @Override
    public int getItemViewType(int position) {
        // 因为在Chat里面添加了类型,判断position上对应的类型
        ChatEntity data = mChatData.get(position);
        return data.getmType();
    }

    @Override
    public int getViewTypeCount() {
        return 2;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            // 根据类型来选择加载哪个布局
            if (getItemViewType(position) == 0) {
                viewHolder = new ViewHolder();
                convertView = mInflater.inflate(R.layout.chat_in_item, null);
                viewHolder.mImageView = (ImageView) convertView
                        .findViewById(R.id.chat_in_image);
                viewHolder.mTextView = (TextView) convertView
                        .findViewById(R.id.chat_in_text);
            } else {
                viewHolder = new ViewHolder();
                convertView = mInflater.inflate(R.layout.chat_out_item, null);
                viewHolder.mImageView = (ImageView) convertView
                        .findViewById(R.id.chat_out_image);
                viewHolder.mTextView = (TextView) convertView
                        .findViewById(R.id.chat_out_text);
            }
            convertView.setTag(viewHolder);

        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.mImageView.setImageResource(mChatData.get(position)
                .getmPic());
        viewHolder.mTextView.setText(mChatData.get(position).getmText());
        return convertView;
    }

    public class ViewHolder {
        public ImageView mImageView;
        public TextView mTextView;
    }
}
