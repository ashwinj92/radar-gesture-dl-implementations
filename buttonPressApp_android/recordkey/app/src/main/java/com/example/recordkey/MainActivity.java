package com.example.recordkey;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    List<String> timeStampButton = new ArrayList<String>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button one = findViewById(R.id.onebutton); //1
        Button two = findViewById(R.id.twobutton); //2
        Button three = findViewById(R.id.threebutton); //3
        Button four = findViewById(R.id.fourbutton); //4
        Button five = findViewById(R.id.fivebutton); //5

        Button six = findViewById(R.id.sixbutton); //6
        Button seven = findViewById(R.id.sevenbutton); //7
        Button eight = findViewById(R.id.eightbutton); //8
        Button nine = findViewById(R.id.ninebutton); //9
        Button star = findViewById(R.id.starbutton); // *
        Button zero = findViewById(R.id.zerobutton); //0
        Button hash = findViewById(R.id.hashbutton); //#
        Button record = findViewById(R.id.record); //0
        Button stop = findViewById(R.id.stop); //#

        one.setOnClickListener(this);
        two.setOnClickListener(this);
        three.setOnClickListener(this);
        four.setOnClickListener(this);
        five.setOnClickListener(this);
        six.setOnClickListener(this);
        seven.setOnClickListener(this);
        eight.setOnClickListener(this);
        nine.setOnClickListener(this);
        zero.setOnClickListener(this);
        hash.setOnClickListener(this);
        star.setOnClickListener(this);
        record.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.onebutton:
                timeStampButton.add("one" + System.currentTimeMillis());
                Toast.makeText(this, "Button 1 clicked", Toast.LENGTH_SHORT).show();
                break;
            case R.id.twobutton:
                timeStampButton.add("two" + System.currentTimeMillis());
                Toast.makeText(this, "Button 2 clicked", Toast.LENGTH_SHORT).show();
                break;
            case R.id.threebutton:
                timeStampButton.add("three" + System.currentTimeMillis());
                Toast.makeText(this, "Button 3 clicked", Toast.LENGTH_SHORT).show();
                break;
            case R.id.fourbutton:
                timeStampButton.add("four" + System.currentTimeMillis());
                Toast.makeText(this, "Button 4 clicked", Toast.LENGTH_SHORT).show();
                break;
            case R.id.fivebutton:
                timeStampButton.add("five" + System.currentTimeMillis());
                Toast.makeText(this, "Button 5 clicked", Toast.LENGTH_SHORT).show();
                break;
            case R.id.sixbutton:
                timeStampButton.add("six" + System.currentTimeMillis());
                Toast.makeText(this, "Button 6 clicked", Toast.LENGTH_SHORT).show();
                break;
            case R.id.sevenbutton:
                timeStampButton.add("seven" + System.currentTimeMillis());
                Toast.makeText(this, "Button 7 clicked", Toast.LENGTH_SHORT).show();
                break;
            case R.id.eightbutton:
                timeStampButton.add("seven" + System.currentTimeMillis());
                Toast.makeText(this, "Button 8 clicked", Toast.LENGTH_SHORT).show();
                break;
            case R.id.ninebutton:
                timeStampButton.add("nine" + System.currentTimeMillis());
                Toast.makeText(this, "Button 9 clicked", Toast.LENGTH_SHORT).show();
                break;
            case R.id.zerobutton:
                timeStampButton.add("zero" + System.currentTimeMillis());

                Toast.makeText(this, "Button 0 clicked", Toast.LENGTH_SHORT).show();
                break;
            case R.id.starbutton:
                timeStampButton.add("*" + System.currentTimeMillis());
                Toast.makeText(this, "Button * clicked", Toast.LENGTH_SHORT).show();
                break;
            case R.id.hashbutton:
                timeStampButton.add("#" + System.currentTimeMillis());
                Toast.makeText(this, "Button # clicked", Toast.LENGTH_SHORT).show();
                break;
            case R.id.record:

                StringBuilder str = new StringBuilder("");

                // Traversing the ArrayList
                for (String eachstring : timeStampButton) {

                    // Each element in ArrayList is appended
                    // followed by comma
                    str.append(eachstring).append(",");
                }

                // StringBuffer to String conversion
                String commaseparatedlist = str.toString();

                // By following condition you can remove the last
                // comma
                if (commaseparatedlist.length() > 0)
                    commaseparatedlist
                            = commaseparatedlist.substring(
                            0, commaseparatedlist.length() - 1);

                Toast.makeText(this, "saved keystrokes", Toast.LENGTH_SHORT).show();
                writeFileExternalStorage(commaseparatedlist);
                break;
        }
    }

    public void writeFileExternalStorage(String data) {

        //Text of the Document


        //Checking the availability state of the External Storage.
        String state = Environment.getExternalStorageState();
        if (!Environment.MEDIA_MOUNTED.equals(state)) {

            //If it isn't mounted - we can't write into it.
            return;
        }

        String timeFileName = "/log_" + System.currentTimeMillis() / 1000 + ".txt";
        //Create a new file that points to the root directory, with the given name:
        File file = new File(getExternalFilesDir(null), timeFileName);

        //This point and below is responsible for the write operation
        FileOutputStream outputStream = null;
        try {
            file.createNewFile();
            //second argument of FileOutputStream constructor indicates whether
            //to append or create new file if one exists
            outputStream = new FileOutputStream(file, true);

            outputStream.write(data.getBytes());
            outputStream.flush();
            outputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}