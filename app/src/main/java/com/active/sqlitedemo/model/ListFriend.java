package com.active.sqlitedemo.model;

import java.util.ArrayList;


public class ListFriend {
    private ArrayList<Friend> listFriend;

    public ListFriend() {
        listFriend = new ArrayList<>();
    }

    public ArrayList<Friend> getListFriend() {
        return listFriend;
    }

    public void setListFriend(ArrayList<Friend> listFriend) {
        this.listFriend = listFriend;
    }
}
