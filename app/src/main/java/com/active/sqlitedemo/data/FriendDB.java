package com.active.sqlitedemo.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;
import android.util.Log;

import com.active.sqlitedemo.model.Friend;
import com.active.sqlitedemo.model.ListFriend;

import java.util.ArrayList;


public final class FriendDB {
    private static final String TEXT_TYPE = " TEXT";
    private static final String COMMA_SEP = ",";
    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + FeedEntry.TABLE_NAME + " (" +
                    FeedEntry.COLUMN_NAME_ID + " integer primary key autoincrement" + COMMA_SEP +
                    FeedEntry.COLUMN_NAME_NAME + TEXT_TYPE + COMMA_SEP +
                    FeedEntry.COLUMN_NAME_EMAIL + TEXT_TYPE + COMMA_SEP +
                    FeedEntry.COLUMN_NAME_MOBILE_NO + TEXT_TYPE + " )";
    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + FeedEntry.TABLE_NAME;
    private static FriendDBHelper mDbHelper = null;
    private static FriendDB instance = null;

    private FriendDB() {
    }

    public static FriendDB getInstance(Context context) {
        if (instance == null) {
            instance = new FriendDB();
            mDbHelper = new FriendDBHelper(context);
        }
        return instance;
    }

    public long addFriend(Friend friend) {
        SQLiteDatabase db = mDbHelper.getWritableDatabase();
        // Create a new map of values, where column names are the keys
        ContentValues values = new ContentValues();
        values.put(FeedEntry.COLUMN_NAME_NAME, friend.name);
        values.put(FeedEntry.COLUMN_NAME_EMAIL, friend.email);
        values.put(FeedEntry.COLUMN_NAME_MOBILE_NO, friend.mobile);

        // Insert the new row, returning the primary key value of the new row
        return db.insert(FeedEntry.TABLE_NAME, null, values);
    }

    public Friend getFriendInfo(ArrayList<CharSequence> friendId) {

        Friend friendinfo = new Friend();


        SQLiteDatabase db = mDbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + FeedEntry.TABLE_NAME + " WHERE " + FeedEntry.COLUMN_NAME_ID + "=?", new String[]{friendId.get(0) + ""});
        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            friendinfo.id = cursor.getString(cursor.getColumnIndex(FeedEntry.COLUMN_NAME_ID));
        }


        return friendinfo;
    }

    public boolean updateFriendDetails(Friend friend) {
        SQLiteDatabase db = mDbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        // AppLog.Log("checking_chile_event","While updating-id"+friend.id+" \n online_status="+friend.online_status+" time:"+friend.online_timestamp);
        values.put(FeedEntry.COLUMN_NAME_NAME, friend.name);
        values.put(FeedEntry.COLUMN_NAME_EMAIL, friend.email);
        values.put(FeedEntry.COLUMN_NAME_MOBILE_NO, friend.mobile);

        int i = db.update(FeedEntry.TABLE_NAME, values, FeedEntry.COLUMN_NAME_ID + " = ? ", new String[]{friend.id});


        if (i == 0) {
            return false;
        }


        return true;
    }

    public ListFriend getListFriend() {
        ListFriend listFriend = new ListFriend();
        SQLiteDatabase db = mDbHelper.getReadableDatabase();
        // Define a projection that specifies which columns from the database
        // you will actually use after this query.
        try {
            Cursor cursor = db.rawQuery("select * from " + FeedEntry.TABLE_NAME, null);
            Log.v("", "" + cursor.getCount());
            while (cursor.moveToNext()) {
                Friend friend = new Friend();

                friend.id = cursor.getString(cursor.getColumnIndex(FeedEntry.COLUMN_NAME_ID));
                friend.name = cursor.getString(cursor.getColumnIndex(FeedEntry.COLUMN_NAME_NAME));
                friend.email = cursor.getString(cursor.getColumnIndex(FeedEntry.COLUMN_NAME_EMAIL));
                friend.mobile = cursor.getString(cursor.getColumnIndex(FeedEntry.COLUMN_NAME_MOBILE_NO));
                listFriend.getListFriend().add(friend);
            }
            cursor.close();
        } catch (Exception e) {
            return new ListFriend();
        }
        return listFriend;
    }

    public void dropDB() {
        SQLiteDatabase db = mDbHelper.getWritableDatabase();
        db.execSQL(SQL_DELETE_ENTRIES);
        db.execSQL(SQL_CREATE_ENTRIES);
    }

    public long deleteFriend(Friend friend) {

        SQLiteDatabase db = mDbHelper.getWritableDatabase();


        return db.delete(FeedEntry.TABLE_NAME, FeedEntry.COLUMN_NAME_ID + "=?", new String[]{friend.id + ""});

    }

    public ListFriend SearchFriendByName(Friend friendinfo) {
        ListFriend listFriend = new ListFriend();
        SQLiteDatabase db = mDbHelper.getReadableDatabase();
        // Define a projection that specifies which columns from the database
        // you will actually use after this query.
        try {
            String query = "select * from " + FeedEntry.TABLE_NAME + " where " + FeedEntry.COLUMN_NAME_NAME + " LIKE ?";
            Cursor cursor = db.rawQuery(query, new String[]{"%" + friendinfo.name + "%"});
            Log.v("", "" + cursor.getCount());
            while (cursor.moveToNext()) {
                Friend friend = new Friend();

                friend.id = cursor.getString(cursor.getColumnIndex(FeedEntry.COLUMN_NAME_ID));
                friend.name = cursor.getString(cursor.getColumnIndex(FeedEntry.COLUMN_NAME_NAME));
                friend.email = cursor.getString(cursor.getColumnIndex(FeedEntry.COLUMN_NAME_EMAIL));
                friend.mobile = cursor.getString(cursor.getColumnIndex(FeedEntry.COLUMN_NAME_MOBILE_NO));
                listFriend.getListFriend().add(friend);
            }
            cursor.close();
        } catch (Exception e) {
            return new ListFriend();
        }
        return listFriend;
    }

    /* Inner class that defines the table contents */
    public static class FeedEntry implements BaseColumns {
        static final String TABLE_NAME = "friend";
        static final String COLUMN_NAME_ID = "friendID";
        static final String COLUMN_NAME_NAME = "name";
        static final String COLUMN_NAME_EMAIL = "email";
        static final String COLUMN_NAME_MOBILE_NO = "mobile_no";

    }

    private static class FriendDBHelper extends SQLiteOpenHelper {
        // If you change the database schema, you must increment the database version.
        static final int DATABASE_VERSION = 1;
        static final String DATABASE_NAME = "FriendChat.db";

        FriendDBHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        public void onCreate(SQLiteDatabase db) {
            db.execSQL(SQL_CREATE_ENTRIES);
        }

        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            // This database is only a cache for online data, so its upgrade policy is
            // to simply to discard the data and start over
            db.execSQL(SQL_DELETE_ENTRIES);
            onCreate(db);
        }

        public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            onUpgrade(db, oldVersion, newVersion);
        }
    }

}
