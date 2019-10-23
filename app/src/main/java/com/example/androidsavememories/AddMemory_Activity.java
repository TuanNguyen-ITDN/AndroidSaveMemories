package com.example.androidsavememories;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.app.DatePickerDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class AddMemory_Activity extends AppCompatActivity {
    AppDatabase db;
    MemoryAdapter memoryAdapter;
    String Memory, newDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_memory_);

        db = Room.databaseBuilder(getApplicationContext(),
                AppDatabase.class, "database-name").build();

        final Button btn_Add_Task = (Button) findViewById(R.id.btn_AddMemory);

        btn_Add_Task.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addMemory();
                finish();
            }
        });

        final Button btnAddDate = (Button) findViewById(R.id.btn_AddDate);
        btnAddDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDateTimePicker();
            }
        });
    }

    public void addMemory() {
        final EditText edit_text_memory = (EditText) findViewById(R.id.edit_text_Memory);
        Memory = edit_text_memory.getText().toString();

        final EditText edit_text_Date = (EditText) findViewById(R.id.edit_date);
        newDate = edit_text_Date.getText().toString();

        if (Memory.isEmpty()) {
            Toast.makeText(this, "Memory must not null", Toast.LENGTH_SHORT).show();
            return;
        }
        Toast.makeText(this, Memory + "has been added successfully", Toast.LENGTH_SHORT).show();

        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                Memory newMemory = new Memory();
                newMemory.setDescription(Memory);
                newMemory.setDate(newDate);
                db.memoryDao().insert(newMemory);
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                //Toast.makeText(AddMemory_Activity.this, Memory + " has been added successfully", Toast.LENGTH_SHORT).show();

            }
        }.execute();
    }

    void showDateTimePicker() {
        final EditText edit_text_Date = (EditText) findViewById(R.id.edit_date);
        DatePickerDialog dialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                edit_text_Date.setText(dayOfMonth + "/" + month + "/" + year);
            }
        }, 2019, 01 - 1, 01);
        dialog.show();
    }
}
