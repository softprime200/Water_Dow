package net.vrgsoft.videocrop;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import net.vrgsoft.videcrop.VideoCropActivity1;


public class MainActivity extends AppCompatActivity {
    private static final int CROP_REQUEST = 200;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        String inputPath = "/storage/emulated/0/VidMate/download/1111.mp4";
        String inputPath = "/storage/emulated/0/WhatsApp/Media/WhatsApp Video/c.mp4";

        String outputPath = "/storage/emulated/0/plugsup.mp4";
        startActivityForResult(VideoCropActivity1.createIntent(this, inputPath, outputPath), CROP_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == CROP_REQUEST && resultCode == RESULT_OK){
            //crop successful
        }
    }
}
