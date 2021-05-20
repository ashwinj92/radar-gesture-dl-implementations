package com.example.recordbuttonpress;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button button1 = findViewById(R.id.button11); //1
        Button button2 = findViewById(R.id.button12); //2
        Button button3 = findViewById(R.id.button10); //3
        Button button5 = findViewById(R.id.button4); //4
        Button button4 = findViewById(R.id.button5); //5

        Button button6 = findViewById(R.id.button); //6
        Button button7 = findViewById(R.id.button6); //7
        Button button8 = findViewById(R.id.button7); //8
        Button button9 = findViewById(R.id.button2); //9
        Button buttonstar = findViewById(R.id.button9); // *
        Button buttonzero = findViewById(R.id.button8); //0
        Button buttonhash = findViewById(R.id.button3); //#

        button1.setOnClickListener(this);
        button2.setOnClickListener(this);
        button3.setOnClickListener(this);
        button4.setOnClickListener(this);
        button5.setOnClickListener(this);
        button6.setOnClickListener(this);
        button7.setOnClickListener(this);
        button8.setOnClickListener(this);
        button9.setOnClickListener(this);
        buttonstar.setOnClickListener(this);
        buttonzero.setOnClickListener(this);
        buttonhash.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button11:
                Toast.makeText(this, "Button 1 clicked", Toast.LENGTH_SHORT).show();
                break;
            case R.id.button12:
                Toast.makeText(this, "Button 2 clicked", Toast.LENGTH_SHORT).show();
                break;
            case R.id.button10:
                Toast.makeText(this, "Button 3 clicked", Toast.LENGTH_SHORT).show();
                break;
            case R.id.button5:
                Toast.makeText(this, "Button 4 clicked", Toast.LENGTH_SHORT).show();
                break;
            case R.id.button4:
                Toast.makeText(this, "Button 5 clicked", Toast.LENGTH_SHORT).show();
                break;
            case R.id.button:
                Toast.makeText(this, "Button 6 clicked", Toast.LENGTH_SHORT).show();
                break;
            case R.id.button7:
                Toast.makeText(this, "Button 8 clicked", Toast.LENGTH_SHORT).show();
                break;
            case R.id.button6:
                Toast.makeText(this, "Button 7 clicked", Toast.LENGTH_SHORT).show();
                break;
            case R.id.button2:
                Toast.makeText(this, "Button 9 clicked", Toast.LENGTH_SHORT).show();
                break;
            case R.id.button8:
                Toast.makeText(this, "Button 0 clicked", Toast.LENGTH_SHORT).show();
                break;
            case R.id.button9:
                Toast.makeText(this, "Button * clicked", Toast.LENGTH_SHORT).show();
                break;
            case R.id.button3:
                Toast.makeText(this, "Button # clicked", Toast.LENGTH_SHORT).show();
                break;
        }
    }
}