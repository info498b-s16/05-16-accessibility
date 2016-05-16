package edu.uw.accessdemo;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    public static final String TAG = "**Main**";

    public static final String EXTRA_MESSAGE = "edu.uw.accessdemo.message";

    private static final int REQUEST_IMAGE_CAPTURE = 1;

    private Uri pictureFileUri = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        View launchButton = findViewById(R.id.btnLaunch);
        launchButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Log.v(TAG, "Launch button pressed");
                Intent intent = new Intent(MainActivity.this, SecondActivity.class);
                intent.putExtra(EXTRA_MESSAGE, "Greetings from sunny MainActivity!");
                startActivity(intent);
            }
        });
    }

    public void callNumber(View v) {
        Log.v(TAG, "Call button pressed");
        Intent intent = new Intent(Intent.ACTION_DIAL);
        intent.setData(Uri.parse("tel:206-685-1622"));
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }

    public void takePicture(View v) {
        Log.v(TAG, "Camera button pressed");

        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {

            File file = null;
            try {
                String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date()); //include timestamp

                File dir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
                file = new File(dir, "PIC_"+timestamp);
                boolean created = file.createNewFile(); //actually make the file!
                Log.v(TAG, "File created: "+created);

            } catch (IOException ioe) {
                Log.d(TAG, Log.getStackTraceString(ioe));
            }

            if(file != null){ //make sure we can save the file!
                pictureFileUri = Uri.fromFile(file);
                Log.v(TAG, "Uri: "+pictureFileUri);

                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, pictureFileUri);
                startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
            }
        }    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            ImageView imageView = (ImageView)findViewById(R.id.imgThumbnail);
            imageView.setImageURI(pictureFileUri);
        }
    }

    public void sendMessage(View v) {
        Log.v(TAG, "Message button pressed");

        SmsManager smsManager = SmsManager.getDefault();

        smsManager.sendTextMessage("5554", null, "This is a test message!", null, null); //no notification

    }

}
