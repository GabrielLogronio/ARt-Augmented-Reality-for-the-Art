package com.GabLog.museumar;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class AugmentedRealityActivity extends AppCompatActivity {

    String wornName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_augmented_reality);

        Intent intentCreator = getIntent();
        wornName = intentCreator.getStringExtra("com.GabLog.museumar.WORK_NAME");

        TextView workNameTextView = findViewById(R.id.workName_textView);
        workNameTextView.setText(wornName);

    }

}
