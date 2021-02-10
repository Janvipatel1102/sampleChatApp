package com.example.messageapp.Activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;

import com.example.messageapp.Models.User;
import com.example.messageapp.databinding.ActivityUserProfileBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class userProfileActivity extends AppCompatActivity {

    ActivityUserProfileBinding binding;
    FirebaseAuth auth;
    FirebaseDatabase database;
    FirebaseStorage storage;
    Uri selectedImg;
    ProgressDialog dialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityUserProfileBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        auth=FirebaseAuth.getInstance();
        database=FirebaseDatabase.getInstance();
        storage=FirebaseStorage.getInstance();
        getSupportActionBar().hide();

        dialog=new ProgressDialog(this);
        dialog.setMessage("Updating Profile....");
        dialog.setCancelable(false);


        binding.profileImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent();
                intent.setAction(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                startActivityForResult(intent,45);
            }
        });
        binding.continueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name =binding.phoneBox.getText().toString();
                if(name.isEmpty())
                {
                    binding.phoneBox.setError("Please Enter Name");

                }
                dialog.show();
                if(selectedImg!=null)
                {
                    StorageReference referance = storage.getReference().child("Profiles").child(auth.getUid());
                    referance.putFile(selectedImg).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                            if(task.isSuccessful())
                            {
                                referance.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                    @Override
                                    public void onSuccess(Uri uri) {
                                        String url=uri.toString();
                                        String uid=auth.getUid();
                                        String phone=auth.getCurrentUser().getPhoneNumber();
                                        String name=binding.phoneBox.getText().toString();
                                        User user=new User(uid,name,phone,url);
                                        database.getReference().child("users").child(uid).setValue(user).addOnSuccessListener(
                                                new OnSuccessListener<Void>() {
                                                    @Override
                                                    public void onSuccess(Void aVoid) {
                                                        dialog.dismiss();

                                                        Intent intent=new Intent(userProfileActivity.this, MainActivity.class);
                                                        startActivity(intent);
                                                        finish();
                                                    }
                                                }
                                        );
                                    }
                                });
                            }
                        }
                    });
                }else
                {

                    String uid=auth.getUid();
                    String phone=auth.getCurrentUser().getPhoneNumber();
                    User user=new User(uid,name,phone,"No Image");
                    database.getReference().child("users").child(uid).setValue(user).addOnSuccessListener(
                            new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    dialog.dismiss();

                                    Intent intent=new Intent(userProfileActivity.this,MainActivity.class);
                                    startActivity(intent);
                                    finish();
                                }
                            }
                    );
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(data!=null)
        {
            if(data.getData()!=null)
            {
                binding.profileImage.setImageURI(data.getData());
                selectedImg=data.getData();
            }
        }
    }
}