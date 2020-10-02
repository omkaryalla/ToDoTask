package com.example.todolist;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Parcelable;
import android.renderscript.Sampler;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

public class MainActivity extends AppCompatActivity{

    ArrayList<todo> tasks = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button createtask = findViewById(R.id.createtask);
        Button viewtask = findViewById(R.id.viewtask);
        TextView taskcount = findViewById(R.id.taskcount);
        TextView name = findViewById(R.id.name);
        TextView date = findViewById(R.id.date);
        TextView priority = findViewById(R.id.priority);
        LinearLayout linearLayout = findViewById(R.id.linearlayout);

        SharedPreferences sharedPreferences = getSharedPreferences("shared preferences", MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sharedPreferences.getString("task list", null);
        Type type = new TypeToken<ArrayList<todo>>(){}.getType();
        tasks = gson.fromJson(json, type);

        taskcount.setText("You have "+tasks.size()+" tasks");
        if (tasks.size() != 0) {
            Collections.sort(tasks, todo.comparator);

            name.setText(tasks.get(0).getname());
            date.setText(tasks.get(0).getdate());
            priority.setText(tasks.get(0).getpriority());

            linearLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showtask(0);
                }
            });

            String[] midarry = new String[tasks.size()];
            for (int i = 0; i < tasks.size(); i++) {
                midarry[i] = tasks.get(i).getname();
            }
            final String[] tasknames = midarry;
            viewtask.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AlertDialog.Builder list = new AlertDialog.Builder(MainActivity.this);
                    list.setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Log.i("TAG", ""+which);
                        }
                    });
                    list.setTitle("Select Task").setItems(tasknames, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            showtask(which);
                        }
                    });

                    AlertDialog dialog = list.create();
                    dialog.show();

                }
            });
        }

        else {
            viewtask.setClickable(false);
        }

        createtask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), CreateTask.class);
                startActivityForResult(intent, 1);
            }
        });
    }

    private void showtask(int i) {
        Intent intent = new Intent(getApplicationContext(), DiplayTask.class);
        intent.putExtra("items to show", (Parcelable)tasks.get(i));
        intent.putExtra("index", i);
        startActivityForResult(intent, 2);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        SharedPreferences sharedPreferences = getSharedPreferences("shared preferences", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();

        if (requestCode==1){
            if (resultCode == RESULT_FIRST_USER){
                todo task = data.getParcelableExtra("task items");
                tasks.add(task);
                String json = gson.toJson(tasks);
                editor.putString("task list", json);
                editor.apply();
                finish();
                startActivity(getIntent());
            }
        }

        else if (requestCode ==2){
            if (requestCode == 2){
                int index = data.getIntExtra("index",0);
                tasks.remove(index);
                String json = gson.toJson(tasks);
                editor.putString("task list", json);
                editor.apply();
                finish();
                startActivity(getIntent());
            }
        }
    }
}