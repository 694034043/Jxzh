package com.bocop.zyt.added;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.bocop.zyt.R;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by android_sant on 2016/5/3.
 */
public class Attention_Dialog_adapter extends BaseAdapter {

    private List<String> list = new ArrayList<String>();
    private Context context;
    private LayoutInflater inflater;

    public Attention_Dialog_adapter(List<String> list, Context context) {
        this.list = list;
        inflater = LayoutInflater.from(context);
        this.context = context;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int arg0) {
        return list.get(arg0);
    }

    @Override
    public long getItemId(int arg0) {
        return arg0;
    }

    @Override
    public View getView(int arg0, View cv, ViewGroup arg2) {
        // TODO Auto-generated method stub
        Holder hd = null;
        if (cv == null) {
            hd = new Holder();
            cv = inflater.inflate(R.layout.item_dialog_list, null);
            hd.titleText=(TextView)cv.findViewById(R.id.item_dialoglist_titleText);
            cv.setTag(hd);
        } else {
            hd = (Holder) cv.getTag();
        }
        hd.titleText.setText(list.get(arg0));
        return cv;
    }

    private class Holder {
        private TextView titleText,numText;

    }
}
