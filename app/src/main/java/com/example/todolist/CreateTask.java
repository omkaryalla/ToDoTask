package com.example.todolist;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;

public class CreateTask extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_task);

        Button setdate = findViewById(R.id.setdate);
        Button cancel = findViewById(R.id.cancel);
        Button submit = findViewById(R.id.submit);
        final TextView date = findViewById(R.id.date);
         final EditText taskname = findViewById(R.id.taskname);
        final RadioGroup radiogroup = findViewById(R.id.radiogroup);
        final RadioButton[] radiobutton = new RadioButton[1];

        Calendar c = Calendar.getInstance();
        final DatePickerDialog datePickerDialog = new DatePickerDialog(
                this,
                this,
                c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH));

        setdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                datePickerDialog.show();
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();

            }
        });

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (taskname.getText().toString().length() != 0){
                    if (date.getText().toString()!=""){
                        int radiobuttonid = radiogroup.getCheckedRadioButtonId();
                        if (radiobuttonid != -1){
                            radiobutton[0] = findViewById(radiobuttonid);
                            todo taskitems = new todo(date.getText().toString(), taskname.getText().toString(), radiobutton[0].getText().toString());
                            Intent taskintent = new Intent();
                            taskintent.putExtra("task items", (Parcelable) taskitems);
                            setResult(RESULT_FIRST_USER, taskintent);
                            finish();
                        }
                        else {
                            Toast.makeText(getApplicationContext(), "Set priority", Toast.LENGTH_SHORT).show();
                        }
                    }
                    else {
                        Toast.makeText(getApplicationContext(), "Select date", Toast.LENGTH_SHORT).show();
                    }
                }
                else {
                    Toast.makeText(getApplicationContext(), "Enter task name", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        TextView date = findViewById(R.id.date);
        date.setText(dayOfMonth + "/" + (month+1) + "/" +year);
        if (date.getText().toString().length() == 9){
            date.setText(0+date.getText().toString());
        }
    }
}