package com.example.jefferson.mytaskassistant;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private ArrayList<Task> mTasksData;
    private TaskAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Initialize the RecyclerView
        mRecyclerView = (RecyclerView)findViewById(R.id.recyclerView);
        //Set the Layout Manager
        mRecyclerView.setLayoutManager (new LinearLayoutManager(this));

        //Initialize the ArrayLIst that will contain the data
        mTasksData = new ArrayList<>();
        //Initialize the adapter and set it ot the RecyclerView
        mAdapter = new TaskAdapter(this, mTasksData);
        mRecyclerView.setAdapter(mAdapter);

        //Get the data
        initializeData();

    }

    private void initializeData() {
        //Get the resources from the XML file
        String[] tasksList = getResources().getStringArray(R.array.tasks_titles);

        mTasksData.clear();

        for (int i = 0; i<tasksList.length;i++){
            mTasksData.add(new Task( tasksList[i],getResources().getString(R.string.detalle), new Date(),false));
        }

        //Notify the adapter of the change
        mAdapter.notifyDataSetChanged();
    }
}
