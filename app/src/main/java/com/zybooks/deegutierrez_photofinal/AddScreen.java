package com.zybooks.deegutierrez_photofinal;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

public class AddScreen extends AppCompatActivity {

    ImageView imgPhoto;
    EditText etPhotoTag;
    Button buttonSave, buttonLoad, buttonCapture;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_screen);

        imgPhoto = findViewById(R.id.imgPhoto);
        etPhotoTag = findViewById(R.id.etPhotoTag);
        buttonSave = findViewById(R.id.buttonSave);
        buttonLoad = findViewById(R.id.buttonLoad);
        buttonCapture = findViewById(R.id.buttonCapture);

        // Using https://www.youtube.com/watch?v=XRD-lVwlSjU as guide
        // for runtime permission request and image capture for intent
        if (ContextCompat.checkSelfPermission(AddScreen.this, android.Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(AddScreen.this, new String[]{
                    Manifest.permission.CAMERA
            }, 100);
        }

        // This will open the camera
        buttonCapture.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent, 100);
            }
        });

        // Used https://www.youtube.com/watch?v=-MhB-Frk0ag as a guide
        // and some chatgpt guidance
        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get the Drawable from the ImageView
                Drawable drawable = imgPhoto.getDrawable();

                // Check if the drawable is an instance of BitmapDrawable
                if (drawable instanceof BitmapDrawable) {
                    // Extract the Bitmap from the Drawable
                    Bitmap bitmap = ((BitmapDrawable) drawable).getBitmap();

                    // Convert the Bitmap to a byte array
                    byte[] photoData = BitmapUtils.getBytes(bitmap);

                    // Get the tag from the EditText
                    String photoTag = etPhotoTag.getText().toString().trim();

                    // Check if the photoData is not null and the tag is not empty
                    if (photoData != null && !photoTag.isEmpty()) {
                        // Instantiate the DatabaseHelper
                        DatabaseHelper myDB = new DatabaseHelper(AddScreen.this);

                        // Call the addPhoto method with the photoData and tag
                        myDB.addPhoto(photoData, photoTag);

                        // Clear the EditText and ImageView after adding the photo to database
                        etPhotoTag.setText("");
                        imgPhoto.setImageResource(0);
                    } else {
                        // Display a message if either the photo or tag is missing
                        Toast.makeText(AddScreen.this, "Please capture a photo and enter a tag", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    // If the Drawable is not an instance of BitmapDrawable,
                    // show toast message invalid photo format
                    // This may happen if the ImageView is set with a different type of Drawable
                    Toast.makeText(AddScreen.this, "Invalid photo format", Toast.LENGTH_SHORT).show();
                }
            }
        });

        // The load button will show all the images from my database on a new screen
        // with scroll function
        buttonLoad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AddScreen.this, DisplayActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data){
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100){
            Bitmap bitmap = (Bitmap) data.getExtras().get("data");
            imgPhoto.setImageBitmap(bitmap);
        }
    }
}