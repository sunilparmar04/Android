package com.active.sqlitedemo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.active.sqlitedemo.data.FriendDB;
import com.active.sqlitedemo.model.Friend;

import java.util.HashMap;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    EditText et_name, et_email, et_number;
    Button btn_addFriend;
    HashMap<String, String> frined_info;
    String friend_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);


        et_name = (EditText) findViewById(R.id.et_name);
        et_email = (EditText) findViewById(R.id.et_email);
        et_number = (EditText) findViewById(R.id.et_number);


        btn_addFriend = (Button) findViewById(R.id.btn_addFriend);
        //to drop or delete or clear all values form db
        // FriendDB.getInstance(MainActivity.this).dropDB();
        btn_addFriend.setOnClickListener(this);
        ActionBar action = getSupportActionBar();
        action.hide();
        Intent intent = getIntent();
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {


            if (intent.hasExtra("frined_info")) {

                if (bundle != null) {
                    frined_info = (HashMap<String, String>) bundle.getSerializable("frined_info");
                }

                if (!frined_info.isEmpty()) {
                    friend_id = frined_info.get("id");
                    et_name.setText(frined_info.get("name"));
                    et_email.setText(frined_info.get("email"));
                    et_number.setText(frined_info.get("number"));
                    btn_addFriend.setText("Update");


                }
            }


        }

    }

    @Override
    public void onClick(View view) {
        //add or update friend information
        if (view.getId() == R.id.btn_addFriend) {

            String name, email, number;
            name = et_name.getText().toString().trim();
            email = et_email.getText().toString().trim();
            number = et_number.getText().toString().trim();
            if (name.isEmpty()) {
                Toast.makeText(MainActivity.this, "Please enter Name", Toast.LENGTH_SHORT).show();
                return;
            } else if (email.isEmpty()) {
                Toast.makeText(MainActivity.this, "Please enter Email", Toast.LENGTH_SHORT).show();
                return;
            } else if (number.isEmpty()) {
                Toast.makeText(MainActivity.this, "Please enter Mobile number", Toast.LENGTH_SHORT).show();
                return;
            }


            Friend friendinfo = new Friend();
            friendinfo.name = name;
            friendinfo.email = email;
            friendinfo.mobile = number;
            friendinfo.id = friend_id;

            if (btn_addFriend.getText() == "Update") {

                boolean status = FriendDB.getInstance(MainActivity.this).updateFriendDetails(friendinfo);

                if (status) {
                    Toast.makeText(MainActivity.this, "Updated  successfully.", Toast.LENGTH_SHORT).show();
                    Intent gotoList = new Intent(MainActivity.this, FriendListActivity.class);
                    startActivity(gotoList);
                    finish();
                } else {
                    Toast.makeText(MainActivity.this, "Getting error!", Toast.LENGTH_SHORT).show();
                }

            } else {

                long rowInserted = FriendDB.getInstance(MainActivity.this).addFriend(friendinfo);
                if (rowInserted != -1) {
                    Toast.makeText(MainActivity.this, "Inserted successfully.", Toast.LENGTH_SHORT).show();
                    Intent gotoList = new Intent(MainActivity.this, FriendListActivity.class);
                    startActivity(gotoList);
                    finish();
                } else {
                    Toast.makeText(MainActivity.this, "Getting error!", Toast.LENGTH_SHORT).show();
                }
            }


        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
