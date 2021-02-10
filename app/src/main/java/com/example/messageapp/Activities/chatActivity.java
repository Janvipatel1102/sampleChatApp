package com.example.messageapp.Activities;

import android.annotation.SuppressLint;
import android.os.Bundle;

import com.example.messageapp.databinding.ActivityChatBinding;

import androidx.appcompat.app.AppCompatActivity;

public class chatActivity extends AppCompatActivity {
ActivityChatBinding binding;
    @SuppressLint("RestrictedApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityChatBinding.inflate(getLayoutInflater());

        setContentView(binding.getRoot());
        String name=getIntent().getStringExtra("username").toString();
        String uid=getIntent().getStringExtra("userid").toString();
        getSupportActionBar().setTitle(name);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return super.onSupportNavigateUp();
    }
}