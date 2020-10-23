package com.GabLog.museumar;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Adapter;
import android.widget.ExpandableListView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    ExpandableListView AuthorsWorksExpandableListView;
    HashMap<String, List<String>> authorWorksList;

    AuthorWorksAdapter viewAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        AuthorsWorksExpandableListView = (ExpandableListView) findViewById(R.id.authorsWorks_expandableListView);
        authorWorksList = new HashMap<>();

        initiateListData();

        AuthorWorksAdapter adapter = new AuthorWorksAdapter(this, new ArrayList<String>(authorWorksList.keySet()), authorWorksList);
        AuthorsWorksExpandableListView.setAdapter(adapter);

    }

    void initiateListData() {
        authorWorksList.put(getString(R.string.author1name), arrayToListString(getResources().getStringArray(R.array.author1works_names)));
        authorWorksList.put(getString(R.string.author2name), arrayToListString(getResources().getStringArray(R.array.author2works_names)));
        authorWorksList.put(getString(R.string.author3name), arrayToListString(getResources().getStringArray(R.array.author3works_names)));

    }

    List<String> arrayToListString(String[] allWorks){
        List<String> list = new ArrayList<>();
        for(String work: allWorks) list.add(work);
        return  list;
    }


}
