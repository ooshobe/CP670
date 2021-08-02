package com.example.helloworld;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;



public class ChatWindow extends AppCompatActivity {
    String ACTIVITY_NAME;
    ArrayList<String> ChatMessages;
    ListView ChatView;
    EditText ChatText;
    Button send;
    ChatAdapter  messageAdapter;
    ChatDatabaseHelper MessageDBHelper;
    SQLiteDatabase messagesDB;



    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_window);

        ACTIVITY_NAME = this.getLocalClassName();
        ChatText = (EditText) findViewById(R.id.ChatBox);
        send = (Button) findViewById(R.id.SendChat);
        ChatView = (ListView) findViewById(R.id.ConversList);
        MessageDBHelper = new ChatDatabaseHelper(this);
        messagesDB = MessageDBHelper.getWritableDatabase();
        ChatMessages= this.getAllMessages(messagesDB,MessageDBHelper);



        //in this case, “this” is the ChatWindow, which is-A Context object
        messageAdapter = new ChatAdapter( this );
        ChatView.setAdapter (messageAdapter);

    }

    @Override
    protected void onDestroy()  {

        messagesDB.close();
        super.onDestroy();


    }

    public void sendChat(View v){

        String[] allItems = { MessageDBHelper.KEY_ID,
                MessageDBHelper.KEY_MESSAGE };
        ContentValues values = new ContentValues();
        values.put(MessageDBHelper.KEY_MESSAGE, ChatText.getText().toString());
        long insertMessages = messagesDB.insert(MessageDBHelper.TABLE_MESSAGES, null,
                values);

        Cursor cursor = messagesDB.query(MessageDBHelper.TABLE_MESSAGES,
                allItems, MessageDBHelper.KEY_MESSAGE + " = " + insertMessages, null,
                null, null, null);
        cursor.moveToFirst();


        messageAdapter.notifyDataSetChanged(); //this restarts the process of getCount()/
        ChatText.setText("");
    }
    public ArrayList<String> getAllMessages(SQLiteDatabase db,ChatDatabaseHelper helper) {
        ArrayList<String> items = new ArrayList<String>();

        String[] allItems = { helper.KEY_ID,
                helper.KEY_MESSAGE };

        Cursor cursor = db.query(helper.TABLE_MESSAGES,
                allItems, null, null, null, null, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            String item = cursor.getString(1);
            Log.i(ACTIVITY_NAME, "SQL MESSAGE:" + cursor.getString( cursor.getColumnIndex( ChatDatabaseHelper.KEY_MESSAGE) ) );
            items.add(item);
            cursor.moveToNext();
        }
        // Make sure to close the cursor
        cursor.close();
        return items;
    }


    private String getText(Cursor cursor) {
        return  cursor.getString(1);
    }

   public class ChatAdapter extends ArrayAdapter<String> {


       public ChatAdapter(Context ctx) {
           super(ctx, 0);
       }

       public int getCount(){

           return ChatMessages.size();
       }

       public String getItem(int position){
           return ChatMessages.get(position);
       }

       public View getView(int position, View convertView, ViewGroup parent){
           LayoutInflater inflater = ChatWindow.this.getLayoutInflater();

           View result = null ;

           if(position%2 == 0) {

               result = inflater.inflate(R.layout.chat_row_outgoing, null);
               TextView message = (TextView)result.findViewById(R.id.message_text);
               message.setText(getItem(position)); // get the string at position

           }
           else {
               result = inflater.inflate(R.layout.chat_row_incoming, null);
               TextView incoming = (TextView)result.findViewById(R.id.incoming_text);
               incoming.setText(getItem(position)); // get the string at position

           }

           return result;
       }

   }







}


