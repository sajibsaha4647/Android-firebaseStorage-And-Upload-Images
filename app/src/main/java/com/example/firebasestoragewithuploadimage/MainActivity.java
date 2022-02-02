package com.example.firebasestoragewithuploadimage;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.github.drjacky.imagepicker.ImagePicker;
import com.google.firebase.database.annotations.NotNull;
import com.squareup.picasso.Picasso;

import java.net.URI;

import de.hdodenhof.circleimageview.CircleImageView;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    CircleImageView circleImageView;
    ImageView imageView;
    Button imageupload,imageprev;
    public static final int IMAGE_REQUEST = 1;
    private Uri imageuri;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        circleImageView = findViewById(R.id.profileIV);
        imageView = findViewById(R.id.cameraIV);
        imageupload = findViewById(R.id.imageupload);
        imageprev = findViewById(R.id.imagepreview);


        imageView.setOnClickListener(this);
        imageupload.setOnClickListener(this);
        imageprev.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {

        if(view.getId() == R.id.cameraIV){
            OpenFIleChooser();
            System.out.println("click");
        }else if(view.getId() == R.id.imageupload){

        }else if(view.getId() == R.id.imagepreview){

        }

    }

    void OpenFIleChooser(){
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(intent.ACTION_GET_CONTENT);
        startActivityForResult(intent,IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null ){
            imageuri = data.getData();
            Picasso.with(this).load(imageuri).into(circleImageView);
        }
    }
}