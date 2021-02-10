package com.example.messageapp.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.messageapp.Models.Message;
import com.example.messageapp.R;
import com.example.messageapp.databinding.ReceiverBinding;
import com.example.messageapp.databinding.SenderBinding;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class MessageAdapter extends  RecyclerView.Adapter{

    Context context;
    ArrayList<Message> messages;
    final int itemSent =1;
    final int itemReceive=2;
    public MessageAdapter(Context context,ArrayList<Message> messages) {
        this.context=context;
        this.messages=messages;

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
        if(holder.getClass()==sendViewHolder.class)
        {
            sendViewHolder senderViewHolder= (sendViewHolder)holder;
            senderViewHolder.binding.message.setText(message.getMessage());
        }
        else
        {
            ReceiverViewHolder receiverViewHolder= (ReceiverViewHolder)holder;
            receiverViewHolder.binding.message.setText(message.getMessage());
        }

    }

    @Override
    public int getItemCount() {
        return 0;
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
