package com.GabLog.museumar;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.HashMap;
import java.util.List;

public class AuthorWorksAdapter extends BaseExpandableListAdapter {

    Context context;
    List<String> authorsList;
    HashMap<String, List<String>> worksList;

    LayoutInflater myInflater;
    int screenWidth;

    public AuthorWorksAdapter(Context contextToSet, List<String> authorsListToSet, HashMap<String, List<String>> worksListToSet)
    {
        context = contextToSet;
        authorsList = authorsListToSet;
        worksList = worksListToSet;

        myInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display screen = windowManager.getDefaultDisplay();
        screenWidth = screen.getWidth() / 3;

    }

    @Override
    public int getGroupCount() {
        return authorsList.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return worksList.get(authorsList.get(groupPosition)).size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return worksList.get(authorsList.get(groupPosition));
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return worksList.get(authorsList.get(groupPosition)).get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return 0;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return 0;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        String authorHeader = authorsList.get(groupPosition);

        View groupView  = myInflater.inflate(R.layout.author_expandablelistview_header, null);

        TextView authorsNameTextView = (TextView)groupView.findViewById(R.id.authorName_textView);
        authorsNameTextView.setText(authorHeader);

        return groupView;

    }

    @Override
    public View getChildView(final int groupPosition, final int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {

        final String workTitle = (String) getChild(groupPosition, childPosition);
        View groupView;
        groupView = myInflater.inflate(R.layout.authorworks_expandablelistview_detail, null);

        String uri = "drawable/" + workNameToResourceName(workTitle)+ "_main";
        int imageIndex = context.getResources().getIdentifier(uri, null, context.getPackageName());

        BitmapFactory.Options bfOptions = new BitmapFactory.Options();
        bfOptions.inJustDecodeBounds = true;
        bfOptions.inScaled = true;
        BitmapFactory.decodeResource(context.getResources(), imageIndex, bfOptions);

        if(bfOptions.outHeight > bfOptions.outWidth)
        //Portrait: max height = 192sp
        {
            bfOptions.inDensity = bfOptions.outHeight;
            bfOptions.inTargetDensity = screenWidth;

        }

        else//Landscape: max width = 192sp
        {
            bfOptions.inDensity = bfOptions.outWidth;
            bfOptions.inTargetDensity = screenWidth;

        }

        bfOptions.inJustDecodeBounds = false;
        Bitmap scaledImage = BitmapFactory.decodeResource(context.getResources(), imageIndex, bfOptions);

        TextView workNameTextView = (TextView)groupView.findViewById(R.id.workName_textView);
        workNameTextView.setText(workTitle);

        ImageButton workImageImageButton = (ImageButton) groupView.findViewById(R.id.workImage_imageButton);
        workImageImageButton.setImageBitmap(scaledImage);

        workImageImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent loadWorkDetailsActivity = new Intent(context, WorkDetailsActivity.class);

                loadWorkDetailsActivity.putExtra("com.GabLog.museumar.GROUP_INDEX", groupPosition);
                loadWorkDetailsActivity.putExtra("com.GabLog.museumar.CHILD_INDEX", childPosition);

                context.startActivity(loadWorkDetailsActivity);

            }
        });

        ImageButton toARImageButton = (ImageButton) groupView.findViewById(R.id.toAR_imageButton);
        toARImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent loadWorkDetailsActivity = new Intent(context, AugmentedRealityActivity.class);

                loadWorkDetailsActivity.putExtra("com.GabLog.museumar.WORK_NAME", workTitle);

                context.startActivity(loadWorkDetailsActivity);

            }
        });

        return groupView;

    }

    String workNameToResourceName(String workName)
    {
        String modified = workName.toLowerCase().replace(" ", "_");
        return modified;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return false;
    }

}
