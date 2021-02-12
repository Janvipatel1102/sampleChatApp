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
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

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

        String senderId= FirebaseAuth.getInstance().getUid();

        String senderRoom = senderId+user.getUid();

        FirebaseDatabase.getInstance().getReference()
                .child("chats")
                .child(senderRoom)
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if(snapshot.exists()) {
                            String lastMsg = snapshot.child("lastMsg").getValue(String.class);
                            long time = snapshot.child("lastTime").getValue(Long.class);
                            holder.binding.lastMessage.setText(lastMsg);
                            holder.binding.messageTime.setText(Long.toString(time));
                        }
                        else
                            holder.binding.lastMessage.setText("Tap To Chat");
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

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
