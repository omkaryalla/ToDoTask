package com.example.todolist;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class DiplayTask extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.diplay_task);

        Button infocancel = findViewById(R.id.infocancel);
        Button delete = findViewById(R.id.delete);
        TextView name = findViewById(R.id.infoname);
        TextView date = findViewById(R.id.infodate);
        TextView priority = findViewById(R.id.priority);

        Intent intent = getIntent();
        todo task = intent.getParcelableExtra("items to show");
        final int index = intent.getIntExtra("index", 0);

        name.setText(task.getname());
        date.setText(task.getdate());
        priority.setText(task.getpriority());

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent delete = new Intent();
                delete.putExtra("index", index);
                setResult(2, delete);
                finish();
            }
        });

        infocancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }
}