package com.example.messageapp.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.messageapp.Models.UserStatus;
import com.example.messageapp.R;
import com.example.messageapp.databinding.StatusviewBinding;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class topStatusAdapter extends  RecyclerView.Adapter<topStatusAdapter.topStatusViewHolder> {


    Context context;
    ArrayList<UserStatus> userStatuses;

    public topStatusAdapter(Context context, ArrayList<UserStatus> userStatuses) {
        this.context = context;
        this.userStatuses = userStatuses;
    }

    @NonNull
    @Override
    public topStatusViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.statusview,parent,false);

        return new topStatusViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull topStatusViewHolder holder, int position) {
            UserStatus userStatus  = userStatuses.get(position);
        /*    holder.binding.circularStatusView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(context, "Status clicked ", Toast.LENGTH_SHORT).show();
                }
            });
*/
    }

    @Override
    public int getItemCount() {
        return userStatuses.size();
    }

    public class  topStatusViewHolder extends RecyclerView.ViewHolder {
        StatusviewBinding binding;
        public topStatusViewHolder(@NonNull View itemView) {
            super(itemView);



        }
    }


}
