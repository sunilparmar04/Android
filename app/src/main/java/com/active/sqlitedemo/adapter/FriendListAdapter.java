package com.active.sqlitedemo.adapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.Typeface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.active.sqlitedemo.R;
import com.active.sqlitedemo.model.Friend;

import java.util.ArrayList;

/**
 * Created by active 37 on 05-12-2017.
 */

public class FriendListAdapter extends ArrayAdapter<Friend> {


    Activity activity;
    ArrayList<Friend> arr_list;


    Typeface regular, medium;


    public FriendListAdapter(Activity activity, int resource, ArrayList<Friend> arr_list) {
        super(activity, resource, arr_list);
        // TODO Auto-generated constructor stub
        this.activity = activity;
        this.arr_list = arr_list;

    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return arr_list.size();
    }


    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        ViewHolder holder = null;
        if (convertView == null) {

            LayoutInflater infalter = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalter.inflate(R.layout.item_friend_info_list, null);
            holder = new ViewHolder();
            holder.tv_Name = (TextView) convertView.findViewById(R.id.tv_Name);
            holder.tv_email = (TextView) convertView.findViewById(R.id.tv_email);
            holder.tv_number = (TextView) convertView.findViewById(R.id.tv_number);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        Friend friend = arr_list.get(position);
        Log.v("FriendListActivity", "from adapter  name=" + friend.name + " emai;=" + friend.email + " numbier=" + friend.mobile);
        holder.tv_Name.setText(friend.name);
        holder.tv_email.setText(friend.email);
        holder.tv_number.setText(friend.mobile);


        return convertView;
    }

    private class ViewHolder {

        TextView tv_Name, tv_email, tv_number;

    }


}