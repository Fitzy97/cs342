package com.example.user.assignment_1;

import android.content.Intent;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import android.os.Environment;

public class Second_Activity extends MainActivity {

    private Button signOutButton;
    private Button takePhotoButton;
    private Button breakfastButton;
    private Button lunchButton;
    private Button dinnerButton;

    static final int REQUEST_IMAGE_CAPTURE = 1;

    String mCurrentPhotoPath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        signOutButton = (Button) findViewById(R.id.sign_out_button);

        signOutButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.sign_out_button:
                        startActivity(new Intent(Second_Activity.this, MainActivity.class));
                        break;
                    // ...
                }
            }
        });


        takePhotoButton = (Button) findViewById(R.id.take_photo_button);

        takePhotoButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.take_photo_button:
                        dispatchTakePictureIntent();
                        break;
                }
            }
        });

        breakfastButton = (Button) findViewById(R.id.breakfast_button);

        breakfastButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.breakfast_button:
                        startActivity(new Intent(Second_Activity.this, BreakfastActivity.class));;
                        break;
                }
            }
        });

        lunchButton = (Button) findViewById(R.id.lunch_button);

        lunchButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.lunch_button:
                        startActivity(new Intent(Second_Activity.this, LunchActivity.class));
                        break;
                }
            }
        });

        dinnerButton = (Button) findViewById(R.id.dinner_button);

        dinnerButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.dinner_button:
                        startActivity(new Intent(Second_Activity.this, DinnerActivity.class));
                        break;
                }
            }
        });
    }

    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            File file = null;
            try {
                file = createImageFile();
            } catch (IOException e) {
                Log.d(TAG, e.toString());
            }

            if (file != null) {
                Uri photoURI = FileProvider.getUriForFile(this, this.getApplicationContext().getPackageName() + ".provider", file);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
            }
        }
    }


    private File createImageFile() throws IOException {
        // Create an image file name
        String imageFileName = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date()) + "_photo.jpg";

        String root = Environment.getExternalStorageDirectory().toString();

        File image = new File(root + "/saved_images");
        image.mkdirs();

        File file = new File(image, imageFileName);
        file.createNewFile();
        // Save a file: path for use with ACTION_VIEW intents
        mCurrentPhotoPath = file.getAbsolutePath();
        System.out.println("PHOTO PATH: " + mCurrentPhotoPath);
        //galleryAddPic();
        MediaStore.Images.Media.insertImage(getContentResolver(), mCurrentPhotoPath, imageFileName, "");
        return image;
    }

    private void galleryAddPic() {
        Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        File f = new File(mCurrentPhotoPath);
        Uri contentUri = Uri.fromFile(f);
        mediaScanIntent.setData(contentUri);
        this.sendBroadcast(mediaScanIntent);
    }

}