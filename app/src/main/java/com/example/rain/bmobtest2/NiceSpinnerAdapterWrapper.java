package com.example.rain.bmobtest2;

import android.content.Context;
import android.widget.ListAdapter;

/**
 * Created by rain on 2017/5/2.
 */

public class NiceSpinnerAdapterWrapper extends NiceSpinnerBaseAdapter {

    private final ListAdapter mBaseAdapter;

    public NiceSpinnerAdapterWrapper(Context context, ListAdapter toWrap) {
        super(context);
        mBaseAdapter = toWrap;
    }

    @Override
    public int getCount() {
        return mBaseAdapter.getCount() - 1;
    }

    @Override
    public Object getItem(int position) {
        if (position >= mSelectedIndex) {
            return mBaseAdapter.getItem(position + 1);
        } else {
            return mBaseAdapter.getItem(position);
        }
    }

    @Override
    public Object getItemInDataset(int position) {
        return mBaseAdapter.getItem(position);
    }

}
