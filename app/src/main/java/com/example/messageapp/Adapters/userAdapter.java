package com.example.messageapp.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.example.messageapp.Activities.chatActivity;
import com.example.messageapp.Models.User;
import com.example.messageapp.R;
import com.example.messageapp.databinding.RowConversationBinding;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class userAdapter extends RecyclerView.Adapter<userAdapter.userViewHolder> {

    Context context;
    ArrayList<User> users;

    public userAdapter(Context context,ArrayList<User> users){
        this.context=context;
        this.users=users;

    }
    @NonNull
    @Override
    public userViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.row_conversation,parent,false);
        return new userViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull userViewHolder holder, int position) {
        User user=users.get(position);
        holder.binding.userName.setText(user.getUname());
        Glide.with(context).load(user.getProfileImage()).placeholder(R.drawable.avatar).into(holder.binding.profile);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context, chatActivity.class);
                intent.putExtra("username",user.getUname());
                intent.putExtra("userid",user.getUid());
                context.startActivity(intent);


            }
        });


    }

    @Override
    public int getItemCount() {
        return users.size();
    }

    public class userViewHolder extends RecyclerView.ViewHolder {
        RowConversationBinding binding;
        public userViewHolder(@NonNull View itemView) {
            super(itemView);
            binding=RowConversationBinding.bind(itemView);

        }
    }
}
