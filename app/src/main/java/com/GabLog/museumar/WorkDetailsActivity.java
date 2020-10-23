package com.GabLog.museumar;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.List;

public class WorkDetailsActivity extends AppCompatActivity {

    int groupIndex, childIndex;
    String authorName, workName, workDescription;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_work_details);

        Intent intentCreator = getIntent();
        groupIndex = intentCreator.getIntExtra("com.GabLog.museumar.GROUP_INDEX", -1);
        childIndex = intentCreator.getIntExtra("com.GabLog.museumar.CHILD_INDEX", -1);

        context = getApplicationContext();

        UpdateStrings();

        ResizeImage();

    }

    void ResizeImage()
    {
        String uri = "drawable/" + workNameToResourceName(workName)+ "_main";
        int imageIndex = context.getResources().getIdentifier(uri, null, context.getPackageName());

        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display screen = windowManager.getDefaultDisplay();
        int requiredWidth = (int) (screen.getWidth() * 0.7);

        Bitmap oldBitmap = BitmapFactory.decodeResource(getResources(), imageIndex);
        Log.d("mytag" ,oldBitmap.getHeight() + ", " + oldBitmap.getWidth());

        float ratio = oldBitmap.getWidth() / requiredWidth;
        int requiredHeight = (int) (oldBitmap.getHeight() / ratio);
        Bitmap resizedBitmap = Bitmap.createScaledBitmap(oldBitmap, requiredWidth, requiredHeight, false);

        ImageButton workImageImageButton = findViewById(R.id.workImage_imageButton);
        workImageImageButton.setImageBitmap(resizedBitmap);

        ImageButton toARImageButton = (ImageButton) findViewById(R.id.toAR_imageButton);
        toARImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent loadWorkDetailsActivity = new Intent(context, AugmentedRealityActivity.class);

                loadWorkDetailsActivity.putExtra("com.GabLog.museumar.WORK_NAME", workName);

                context.startActivity(loadWorkDetailsActivity);

            }
        });

        /*
        BitmapFactory.Options newOptions = new BitmapFactory.Options();
        newOptions.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(context.getResources(), imageIndex, newOptions);
        int srcWidth = newOptions.outWidth;



        newOptions.inScaled = true;
        newOptions.inDensity = srcWidth;
        newOptions.inTargetDensity = requiredWidth;

        newOptions .inJustDecodeBounds = false;

        Bitmap scaledImage = BitmapFactory.decodeResource(context.getResources(), imageIndex, newOptions);

        ImageButton workImageImageButton = findViewById(R.id.workImage_imageButton);
        workImageImageButton.setImageBitmap(scaledImage);
*/
    }

    void UpdateStrings()
    {
        int authorNameID = getResources().getIdentifier( "author"+ (groupIndex + 1) + "name", "string", context.getPackageName()),
                worksNameArrayID = getResources().getIdentifier( "author"+ (groupIndex + 1) + "works_names", "array", context.getPackageName()),
                worksDescriptionArrayID = getResources().getIdentifier( "author"+ (groupIndex + 1) + "works_descriptions", "array", context.getPackageName());

        authorName = getResources().getString(authorNameID);
        TextView workNameTextView = findViewById(R.id.authorName_textView);
        workNameTextView.setText(authorName);


        workName = getResources().getStringArray(worksNameArrayID)[childIndex];
        TextView authorNameTextView = findViewById(R.id.workName_textView);
        authorNameTextView.setText(workName);

        workDescription = getResources().getStringArray(worksDescriptionArrayID)[childIndex];
        TextView workDescriptionTextView = findViewById(R.id.workDescription_textView);
        workDescriptionTextView.setText(workDescription);

    }

    String workNameToResourceName(String workName)
    {
        String modified = workName.toLowerCase().replace(" ", "_");
        return modified;
    }
}
