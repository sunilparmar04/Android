# SQLiteDemo
This is the simplest example of SQLite for All CURD operations.

Enter friend details

<img  src="https://github.com/sunilparmar04/SQLiteDemo/blob/master/ScreenShots/output.png " width="40%">


## FriendDB
This is the Main database Handler class.


### Insert data in SQLite Table
 On the button click,set  value of friend.
```
 Friend friendinfo = new Friend();
            friendinfo.name = name;
            friendinfo.email = email;
            friendinfo.mobile = number;
            friendinfo.id = friend_id;

// call this method to add data
long rowInserted = FriendDB.getInstance(MainActivity.this).addFriend(friendinfo);

if(rowInserted!=-1){
//data inserted into SQLite
}else{
//getting error
}

// following methods define in FriendDB Class for add values in SQLite

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

```

### Update data in SQLite Table
set updated values to Data model like
```
Friend friendinfo = new Friend();
            friendinfo.name = name;
            friendinfo.email = email;
            friendinfo.mobile = number;
            friendinfo.id = friend_id;

// here we need ID to update table, Becuase i am updating values using ID

```
