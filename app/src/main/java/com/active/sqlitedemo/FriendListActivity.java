package com.active.sqlitedemo;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.active.sqlitedemo.adapter.FriendListAdapter;
import com.active.sqlitedemo.data.FriendDB;
import com.active.sqlitedemo.model.Friend;
import com.active.sqlitedemo.model.ListFriend;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by active 37 on 05-12-2017.
 */

public class FriendListActivity extends AppCompatActivity {

    ListView lv_friend_list;
    FriendListAdapter adapter;
    ListFriend dataListFriend = null;
    ArrayList<Friend> friend_list = new ArrayList<>();
    ArrayList<Friend> search_friend_list = new ArrayList<>();
    Friend friend_info;


    TextView tv_add_friend;

    RelativeLayout rl_search_friend;
    EditText et_search_frined;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.friend_list_activity);

        ActionBar action = getSupportActionBar();
        action.hide();

        tv_add_friend = (TextView) findViewById(R.id.tv_add_friend);
        lv_friend_list = (ListView) findViewById(R.id.lv_friend_list);
        et_search_frined = (EditText) findViewById(R.id.et_search_frined);
        rl_search_friend = (RelativeLayout) findViewById(R.id.rl_search_friend);
        friend_list.clear();
        dataListFriend = FriendDB.getInstance(FriendListActivity.this).getListFriend();


        et_search_frined.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                et_search_frined.setFocusable(true);
                et_search_frined.requestFocus();
                return false;
            }
        });

        if (dataListFriend.getListFriend().size() > 0) {

            for (Friend friend : dataListFriend.getListFriend()) {

                Log.v("FriendListActivity", "friend=" + friend.name + " email:" + friend.email + " nu" + friend.mobile);
                friend_list.add(friend);
            }

            Log.v("FriendListActivity", "size=" + friend_list.size());
            adapter = new FriendListAdapter(FriendListActivity.this, R.layout.item_friend_info_list, friend_list);
            lv_friend_list.setAdapter(adapter);


        }


        tv_add_friend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent gotoMain = new Intent(FriendListActivity.this, MainActivity.class);
                startActivity(gotoMain);
                finish();

            }
        });

        rl_search_friend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (et_search_frined.getText().toString().isEmpty()) {
                    Toast.makeText(FriendListActivity.this, "Please enter name", Toast.LENGTH_SHORT).show();
                    return;
                } else {

                    Friend friendInfo = new Friend();
                    friendInfo.name = et_search_frined.getText().toString().trim();
                    dataListFriend = FriendDB.getInstance(FriendListActivity.this).SearchFriendByName(friendInfo);

                    if (dataListFriend.getListFriend().size() > 0) {
                        search_friend_list.clear();
                        for (Friend friend : dataListFriend.getListFriend()) {

                            Log.v("FriendListActivity", "friend=" + friend.name + " email:" + friend.email + " nu" + friend.mobile);
                            search_friend_list.add(friend);
                        }

                        Log.v("FriendListActivity", "size=" + friend_list.size());
                        adapter = new FriendListAdapter(FriendListActivity.this, R.layout.item_friend_info_list, search_friend_list);
                        lv_friend_list.setAdapter(adapter);


                    }
                }

            }
        });


        et_search_frined.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.length() == 0) {
                    search_friend_list.clear();
                    if (adapter != null) {
                        adapter = new FriendListAdapter(FriendListActivity.this, R.layout.item_friend_info_list, friend_list);
                        lv_friend_list.setAdapter(adapter);

                    }

                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        registerForContextMenu(lv_friend_list);


        lv_friend_list
                .setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {

                    @Override
                    public boolean onItemLongClick(AdapterView<?> parent,
                                                   View view, int position, long id) {
                        // TODO Auto-generated method stub

                        friend_info = (Friend) parent.getAdapter().getItem(position);

                        return false;

                    }
                });
    }


    @Override
    public void onCreateContextMenu(ContextMenu menu, View v,
                                    ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        menu.setHeaderTitle("Select The Action");
        menu.add(0, v.getId(), 0, "Edit");
        menu.add(0, v.getId(), 0, "Delete");
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        if (item.getTitle() == "Edit") {


            Intent gotoMain = new Intent(FriendListActivity.this, MainActivity.class);
            HashMap map = new HashMap();

            map.put("id", "" + friend_info.id);
            map.put("name", "" + friend_info.name);
            map.put("email", "" + friend_info.email);
            map.put("number", "" + friend_info.mobile);

            Bundle extras = new Bundle();
            extras.putSerializable("frined_info", map);
            gotoMain.putExtras(extras);
            startActivity(gotoMain);
            finish();

        } else if (item.getTitle() == "Delete") {


            long result = FriendDB.getInstance(FriendListActivity.this).deleteFriend(friend_info);

            if (result != -1) {
                Toast.makeText(FriendListActivity.this, "Deleted successfully", Toast.LENGTH_SHORT).show();


                friend_list.remove(friend_info);
                adapter.notifyDataSetChanged();


            }
        } else {
            return false;
        }
        return true;
    }

    @Override
    public void onBackPressed() {
        //super.onBackPressed();
    }
}


