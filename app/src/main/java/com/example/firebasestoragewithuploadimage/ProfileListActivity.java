package com.example.firebasestoragewithuploadimage;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Toast;

import com.example.firebasestoragewithuploadimage.AdaptarClass.RecycleAdapter;
import com.example.firebasestoragewithuploadimage.Modelclass.ImageRetrive;
import com.example.firebasestoragewithuploadimage.Modelclass.ImageUpload;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class ProfileListActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RecycleAdapter recycleAdapter;
    private ArrayList<String>imageList;
    DatabaseReference databaseReference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_list);
        databaseReference = FirebaseDatabase.getInstance().getReference("upload");

        recyclerView = findViewById(R.id.RecycleId);
        imageList = new ArrayList<String>();

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for (DataSnapshot dataSnapshot1:snapshot.getChildren()){
                    imageList.add(dataSnapshot1.getValue().toString());
                }

                recycleAdapter = new RecycleAdapter(ProfileListActivity.this,imageList);
                recyclerView.setHasFixedSize(true);
                recyclerView.setLayoutManager(new LinearLayoutManager(ProfileListActivity.this));
                recyclerView.setAdapter(recycleAdapter);


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(ProfileListActivity.this, "database error"+error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });



    }
}