package com.example.helloworld;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class ChatDatabaseHelper extends SQLiteOpenHelper {

    public static final String TABLE_MESSAGES = "MESSAGES";
    public static final String KEY_ID = "_ID" ;
    public static final String KEY_MESSAGE = "Message";

    private static final String DATABASE_NAME = "Messages.db";
    private static final int VERSION_NUM = 1 ;

    private static final String DATABASE_CREATE= "create table "
            + TABLE_MESSAGES + "(" + KEY_ID +
            " integer primary key autoincrement, " + KEY_MESSAGE + " text not null);";

    public ChatDatabaseHelper(Context ctx) {
        super(ctx, DATABASE_NAME, null, VERSION_NUM);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(DATABASE_CREATE);
        Log.i("ChatDatabaseHelper","Calling on Create");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.w(ChatDatabaseHelper.class.getName(),
                "Upgrading database from version " + oldVersion + " to "
                        + newVersion);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_MESSAGES);
        onCreate(db);
        Log.i("ChatDatabaseHelper","Calling on Upgrade, oldVersion= "+oldVersion+"newVersion=" + newVersion);
    }
}
