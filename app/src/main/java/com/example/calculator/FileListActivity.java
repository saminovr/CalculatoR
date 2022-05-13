package com.example.calculator;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.nio.file.Files;

public class FileListActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private TextView noFilesTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_file_list);

        recyclerView = findViewById(R.id.recyclerView);
        noFilesTextView = findViewById(R.id.noFilesTextView);

        String path = getIntent().getStringExtra("path");

        File root = new File(path);
        File[] filesAndFolders = root.listFiles();

        if ((filesAndFolders == null) || (filesAndFolders.length == 0)) {
            noFilesTextView.setVisibility(View.VISIBLE);
        }



        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(new MyAdapter(getApplicationContext(), filesAndFolders));

    }
}