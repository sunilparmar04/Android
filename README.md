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

// following method define in FriendDB Class to add values in SQLite

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
//call Update method by following way:

boolean status = FriendDB.getInstance(MainActivity.this).updateFriendDetails(friendinfo);

if(status){
//data updated successfully
}{
//getting error
}

// following method define in FriendDB Class to Update values in SQLite

  public boolean updateFriendDetails(Friend friend) {
        SQLiteDatabase db = mDbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(FeedEntry.COLUMN_NAME_NAME, friend.name);
        values.put(FeedEntry.COLUMN_NAME_EMAIL, friend.email);
        values.put(FeedEntry.COLUMN_NAME_MOBILE_NO, friend.mobile);

        int i = db.update(FeedEntry.TABLE_NAME, values, FeedEntry.COLUMN_NAME_ID + " = ? ", new String[]{friend.id});
        if (i == 0) {
            return false;
        }
        return true;
    }

```
#### Select All data from SQLite Table

Select query use to get the data from table.

```
//call following method from FriendDB class to get the friend list

   public ListFriend getListFriend() {
        ListFriend listFriend = new ListFriend();
        SQLiteDatabase db = mDbHelper.getReadableDatabase();
        // Define a projection that specifies which columns from the database
        // you will actually use after this query.
        try {
            Cursor cursor = db.rawQuery("select * from " + FeedEntry.TABLE_NAME, null);
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


```

#### Delete or drop(Table) from SQLite Table

```
//call following method from FriendDB class to delete or drop table.

  public void dropDB() {
        SQLiteDatabase db = mDbHelper.getWritableDatabase();
        db.execSQL(SQL_DELETE_ENTRIES);
        db.execSQL(SQL_CREATE_ENTRIES);
    }

```


