package com.example.rain.bmobtest2;

import android.content.Context;
import android.widget.BaseAdapter;

import java.util.List;


/**
 * Created by rain on 2017/5/9.
 */

public abstract class MyBaseAdapter<T, Q> extends BaseAdapter {
    private List<T> mList;
    private Context mContext;
    private Q view;

    public MyBaseAdapter() {
        super();
    }

    public MyBaseAdapter(List<T> mList, Context mContext) {
        super();
        this.mList = mList;
        this.mContext = mContext;
    }

    public MyBaseAdapter(List<T> mList, Context mContext, Q view) {
        super();
        this.mList = mList;
        this.mContext = mContext;
        this.view = view;
    }

    @Override
    public int getCount() {
        return mList == null ? 0 : mList.size();
    }

    @Override
    public Object getItem(int position) {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }


}
