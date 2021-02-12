package com.example.messageapp.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import com.example.messageapp.Models.Message;
import com.example.messageapp.R;
import com.example.messageapp.databinding.ReceiverBinding;
import com.example.messageapp.databinding.SenderBinding;
import com.github.pgreze.reactions.ReactionPopup;
import com.github.pgreze.reactions.ReactionsConfig;
import com.github.pgreze.reactions.ReactionsConfigBuilder;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class MessageAdapter extends  RecyclerView.Adapter{

    Context context;
    ArrayList<Message> messages;
    final int itemSent =1;
    final int itemReceive=2;


String senderRoom,recevierRoom;


    public MessageAdapter(Context context, ArrayList<Message> messages,String senderRoom,String recevierRoom) {
        this.context=context;
        this.messages=messages;
        this.senderRoom=senderRoom;
        this.recevierRoom=recevierRoom;
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if(viewType==itemSent)
        {
            View view= LayoutInflater.from(context).inflate(R.layout.sender,parent,false);
            return new sendViewHolder(view);
        }
        else {
            View view = LayoutInflater.from(context).inflate(R.layout.receiver, parent, false);
            return new ReceiverViewHolder (view);
        }
    }

    @Override
    public int getItemViewType(int position) {
        Message messgae=messages.get(position);
        if(FirebaseAuth.getInstance().getUid().equals(messgae.getSenderid()))
            return  itemSent;
        else
            return itemReceive;

    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Message message=messages.get(position);
        int reactions []= new int[]{
                R.drawable.ic_fb_like,
                R.drawable.ic_fb_love,
                R.drawable.ic_fb_laugh,
                R.drawable.ic_fb_wow,
                R.drawable.ic_fb_sad,
                R.drawable.ic_fb_angry
        };

        ReactionsConfig config = new ReactionsConfigBuilder(context)
                .withReactions(reactions)
                .build();

        ReactionPopup popup = new ReactionPopup(context, config, (pos) -> {

            if(holder.getClass()==sendViewHolder.class)
            {
                sendViewHolder senderViewHolder= (sendViewHolder)holder;
                senderViewHolder.binding.feeling.setImageResource(reactions[pos]);
                senderViewHolder.binding.feeling.setVisibility(View.VISIBLE);
            }
            else
            {
                ReceiverViewHolder receiverViewHolder= (ReceiverViewHolder)holder;
                receiverViewHolder.binding.feeling.setImageResource(reactions[pos]);
                receiverViewHolder.binding.feeling.setVisibility(View.VISIBLE);

            }
            message.setFeelling(pos);
            FirebaseDatabase.getInstance().getReference()
                    .child("chats")
                    .child(senderRoom)
                    .child("messages")
                    .child(message.getMessageId()).setValue(message);

            FirebaseDatabase.getInstance().getReference()
                    .child("chats")
                    .child(recevierRoom)
                    .child("messages")
                    .child(message.getMessageId()).setValue(message);



            return true; // true is closing popup, false is requesting a new selection
        });

        if(holder.getClass()==sendViewHolder.class)
        {
            sendViewHolder senderViewHolder= (sendViewHolder)holder;
            senderViewHolder.binding.message.setText(message.getMessage());

            if(message.getFeelling()>=0)
            {
             // message.setFeelling(reactions[(int)message.getFeelling()]);
              senderViewHolder.binding.feeling.setImageResource(reactions[(int)message.getFeelling()]);
              senderViewHolder.binding.feeling.setVisibility(View.VISIBLE);
            }
            else
                senderViewHolder.binding.feeling.setVisibility(View.GONE);




            senderViewHolder.binding.message.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    popup.onTouch(v,event);
                    return false;
                }
            });
        }
        else
        {
            ReceiverViewHolder receiverViewHolder= (ReceiverViewHolder)holder;
            receiverViewHolder.binding.message.setText(message.getMessage());



            if(message.getFeelling()>=0)
            {
                receiverViewHolder.binding.feeling.setImageResource(reactions[(int)message.getFeelling()]);

                receiverViewHolder.binding.feeling.setVisibility(View.VISIBLE);
            }
            else
                receiverViewHolder.binding.feeling.setVisibility(View.GONE);

            receiverViewHolder.binding.message.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    popup.onTouch(v,event);
                    return false;
                }
            });
        }





    }

    @Override
    public int getItemCount() {

        return messages.size();
    }

    public  class  sendViewHolder extends RecyclerView.ViewHolder{
        SenderBinding binding;
        public sendViewHolder(@NonNull View itemView) {
            super(itemView);
            binding =SenderBinding.bind(itemView);

        }
    }

    public class ReceiverViewHolder extends RecyclerView.ViewHolder{
        ReceiverBinding binding;
        public ReceiverViewHolder(@NonNull View itemView) {
            super(itemView);
            binding=ReceiverBinding.bind(itemView);


        }
    }
}
