package com.example.firebasestoragewithuploadimage;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.firebasestoragewithuploadimage.Modelclass.ImageUpload;
import com.github.drjacky.imagepicker.ImagePicker;
import com.google.android.gms.tasks.OnCanceledListener;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.annotations.NotNull;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
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
    UploadTask uploadTask;


    DatabaseReference databaseReference;
    StorageReference storageReference ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        databaseReference = FirebaseDatabase.getInstance().getReference("upload");
        storageReference = FirebaseStorage.getInstance().getReference("upload");

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
            if(uploadTask != null && uploadTask.isInProgress()){
                Toast.makeText(MainActivity.this,"Upload in progress",Toast.LENGTH_LONG).show();
            }else {
                SaveImageFile();
            }

        }else if(view.getId() == R.id.imagepreview){

        }

    }

    //this is for image picker
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

    public String getImageFileextension(Uri uri){

        ContentResolver contentResolver = getContentResolver();
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        return mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(uri));

    } ;

    //this is for save image file to firebase
    void SaveImageFile(){

        StorageReference ref = storageReference.child(System.currentTimeMillis()+"."+getImageFileextension(imageuri));

        ref.putFile(imageuri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Toast.makeText(MainActivity.this,"Firle upload successfull",Toast.LENGTH_LONG).show();

//                ImageUpload upload = new ImageUpload(taskSnapshot.getStorage().getDownloadUrl().toString());

//                String Uploadid = databaseReference.push().getKey();


                taskSnapshot.getStorage().getDownloadUrl().addOnCompleteListener(new OnCompleteListener<Uri>() {
                    @Override
                    public void onComplete(@NonNull Task<Uri> task) {
                        String fileLink = task.getResult().toString();
                        databaseReference.child(System.currentTimeMillis()+"img").setValue(fileLink);
                    }
                });

                circleImageView.setImageResource(R.drawable.ic_launcher_background);

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(MainActivity.this,"Firle upload failed",Toast.LENGTH_LONG).show();
            }
        }).addOnCanceledListener(new OnCanceledListener() {
            @Override
            public void onCanceled() {
                Toast.makeText(MainActivity.this,"cancel upload",Toast.LENGTH_LONG).show();
            }
        });

    }
}