package com.example.messageapp.Activities;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.messageapp.Adapters.MessageAdapter;
import com.example.messageapp.Models.Message;
import com.example.messageapp.databinding.ActivityChatBinding;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

public class chatActivity extends AppCompatActivity   {
    ActivityChatBinding binding;
    ArrayList<com.example.messageapp.Models.Message> messages;
    MessageAdapter messageAdapter;
    String senderRoom,receiverRoom;
    FirebaseDatabase database;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityChatBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        String name=getIntent().getStringExtra("username").toString();
        String receiveruid=getIntent().getStringExtra("userid").toString();
        String senderuid= FirebaseAuth.getInstance().getUid();

        senderRoom=senderuid+receiveruid;
        receiverRoom=receiveruid+senderuid;
        messages=new ArrayList<>();
        messageAdapter=new MessageAdapter(this,messages,senderRoom,receiverRoom);
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(this));
        binding.recyclerView.setAdapter(messageAdapter);



        database = FirebaseDatabase.getInstance();
        database.getReference()
                .child("chats")
                .child(senderRoom)
                .child("messages").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                messages.clear();
                for(DataSnapshot snapshot1:snapshot.getChildren())
                {
                    Message message=snapshot1.getValue(Message.class);
                    messages.add(message);
                    message.setMessageId(snapshot1.getKey());
                    Log.d("myDebugTag",message.getMessage());

                }
                messageAdapter.notifyDataSetChanged();
                Toast.makeText(chatActivity.this, "Hello World", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        binding.sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String messageText=binding.messageBox.getText().toString();
                Date date=new Date();
                String randomKey = database.getReference().push().getKey();
                Message message =new Message(messageText,senderuid,date.getTime());
                binding.messageBox.setText("");



                HashMap<String,Object> lastObj = new HashMap<>();
                lastObj.put("lastMsg",message.getMessage());
                lastObj.put("lastTime",date.getTime());

                database.getReference().child("chats").child(senderRoom).updateChildren(lastObj);
                database.getReference().child("chats").child(receiverRoom).updateChildren(lastObj);



                database.getReference()
                        .child("chats")
                        .child(senderRoom)
                        .child("messages")
                        .child(randomKey)
                        .setValue(message).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                        public void onSuccess(Void aVoid) {
                        database.getReference()
                                .child("chats")
                                .child(receiverRoom)
                                .child("messages")
                                .child(randomKey)
                                .setValue(message).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {

                            }
                        });

                    }
                });



            }
        });

        getSupportActionBar().setTitle(name);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


    }

    @Override
    protected void onStart() {
        super.onStart();

    }

    @Override
    protected void onStop() {
        super.onStop();

    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return super.onSupportNavigateUp();
    }
}