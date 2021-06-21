package com.example.helloworld;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;



public class ChatWindow extends AppCompatActivity {
    ArrayList<String> ChatMessages;
    ListView ChatView;
    EditText ChatText;
    Button send;
    ChatAdapter  messageAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_window);

        ChatText = (EditText) findViewById(R.id.ChatBox);
        send = (Button) findViewById(R.id.SendChat);
        ChatView = (ListView) findViewById(R.id.ConversList);
        ChatMessages= new ArrayList<>();


        //in this case, “this” is the ChatWindow, which is-A Context object
        messageAdapter = new ChatAdapter( this );
        ChatView.setAdapter (messageAdapter);

    }

    public void sendChat(View v){
        ChatMessages.add(ChatText.getText().toString());
        messageAdapter.notifyDataSetChanged(); //this restarts the process of getCount()/
        ChatText.setText("");
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


