package com.example.user.assignment_1;

import android.content.Intent;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.support.v4.content.ContextCompat;
import android.support.v4.app.ActivityCompat;
import android.content.pm.PackageManager;
import android.Manifest;


import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import android.os.Environment;
import android.content.Context;
import android.content.ContentValues;

public class Second_Activity extends MainActivity {

    private Button signOutButton;
    private Button takePhotoButton;
    private Button breakfastButton;
    private Button lunchButton;
    private Button dinnerButton;

    static final int REQUEST_IMAGE_CAPTURE = 1;
    static final int MY_PERMISSIONS_REQUEST_READ_CONTACTS = 1;

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

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            //Bundle extras = data.getExtras();
            //Bitmap imageBitmap = (Bitmap) extras.get("data");
            //mImageView.setImageBitmap(imageBitmap);
            galleryAddPic();
        }
    }

    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            File file = null;
            try {
                file = createImageFile();
            } catch (IOException e) {
                Log.d(TAG, e.toString());
                System.out.println("MUTHERFUCKER");
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
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        mCurrentPhotoPath = image.getAbsolutePath();
        //galleryAddPic();
        //addImageToGallery(mCurrentPhotoPath, getApplicationContext());
        //MediaStore.Images.Media.insertImage(getContentResolver(), mCurrentPhotoPath, imageFileName, "");
        return image;
//        // Create an image file name
//        String imageFileName = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date()) + "_photo.jpg";
//
//        String root = Environment.getExternalStorageDirectory().toString();

//        System.out.println(root + "/saved_images");
//        File image = new File(root);
//
//        System.out.println(image.mkdirs());
//        System.out.println("DEEEEZ");
//
//        File file = new File(image, imageFileName);
//        System.out.println(file.toString());
//        System.out.println(file.getAbsolutePath());
//        //file.createNewFile();
//        // Save a file: path for use with ACTION_VIEW intents
//        mCurrentPhotoPath = file.getAbsolutePath();
//        System.out.println("PHOTO PATH: " + mCurrentPhotoPath);
//        //galleryAddPic();
//        MediaStore.Images.Media.insertImage(getContentResolver(), mCurrentPhotoPath, imageFileName, "");
//        return image;
    }

    private void galleryAddPic() {
        Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        File f = new File(mCurrentPhotoPath);
        Uri contentUri = Uri.fromFile(f);
        mediaScanIntent.setData(contentUri);
        this.sendBroadcast(mediaScanIntent);
    }
//
//    public void addImageToGallery(final String filePath, final Context context) {
//        // Here, thisActivity is the current activity
//        if (ContextCompat.checkSelfPermission(this,
//                Manifest.permission.READ_CONTACTS)
//                != PackageManager.PERMISSION_GRANTED) {
//
//            // Should we show an explanation?
//            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
//                    Manifest.permission.READ_CONTACTS)) {
//
//                // Show an explanation to the user *asynchronously* -- don't block
//                // this thread waiting for the user's response! After the user
//                // sees the explanation, try again to request the permission.
//
//            } else {
//
//                // No explanation needed, we can request the permission.
//
//                ActivityCompat.requestPermissions(this,
//                        new String[]{Manifest.permission.READ_CONTACTS},
//                        MY_PERMISSIONS_REQUEST_READ_CONTACTS);
//
//                // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
//                // app-defined int constant. The callback method gets the
//                // result of the request.
//            }
//        }
//
//        ContentValues values = new ContentValues();
//
//        values.put(MediaStore.Images.Media.DATE_TAKEN, System.currentTimeMillis());
//        values.put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg");
//        values.put(MediaStore.MediaColumns.DATA, filePath);
//
//        context.getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
//    }

}