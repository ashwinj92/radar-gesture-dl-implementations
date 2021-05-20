package com.example.covidsounds;


import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;


import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.mobile.client.AWSMobileClient;
import com.amazonaws.services.s3.AmazonS3Client;
import com.example.covidsounds.dto.DemographicDTO;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import android.view.View.OnClickListener;


/**
 * @author Abhishek
 */
public class EleventhActivity extends AppCompatActivity {





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_eleventh);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        String fileName = "sentence_" + System.currentTimeMillis() / 1000 + ".wav";
        AudioRecordActivity.fileName = fileName;
        AudioRecordActivity.fragment = TwelfthActivity.class.toString();
//      AudioRecordActivity.button = findViewById(R.id.button_eleventh);
        Intent myIntent = new Intent(getApplicationContext(), AudioRecordActivity.class);
        startActivity(myIntent);
    }
}

