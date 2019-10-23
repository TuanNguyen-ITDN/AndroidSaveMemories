package com.example.androidsavememories;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

public class UpdateActivity extends AppCompatActivity {
    AppDatabase db;
    EditText editMemory;
    TextView editDate;
    Button btnUpdate, btnChooseDate;
    int memoryId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);

        db = Room.databaseBuilder(getApplicationContext(),
                AppDatabase.class, "database-name").build();

        editMemory = findViewById(R.id.edit_text_Memory);
        editDate = findViewById(R.id.edit_date);

        btnUpdate = findViewById(R.id.btn_Update);
        btnChooseDate = findViewById(R.id.btn_AddDate);

        int id = getIntent().getIntExtra("id", 0);
        memoryId = id;
        String memory = getIntent().getStringExtra("memory");
        editMemory.setText(memory);

        String date = getIntent().getStringExtra("date");
        editDate.setText(date);

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateTodoToDatabase();

            }
        });

        btnChooseDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDateTimePicker();
            }
        });
    }

    private void updateTodoToDatabase() {
        final String memory = editMemory.getText().toString();
        final String date = editDate.getText().toString();

        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                Memory newMemory = new Memory();
                newMemory.setDescription(memory);
                newMemory.setDate(date);
                newMemory.setId(memoryId); // thinking about why we need to set id here
                db.memoryDao().updateOne(newMemory);
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                showSuccessDialog();
            }
        }.execute();
    }

    private void showSuccessDialog() {
        new AlertDialog.Builder(this)
                .setTitle("Message")
                .setMessage("Update Success")
                .setPositiveButton("Got it", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        finish();
                    }
                })
                .show();
    }

    void showDateTimePicker() {
        final TextView edit_text_Date = (TextView) findViewById(R.id.edit_date);
        DatePickerDialog dialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                edit_text_Date.setText(dayOfMonth + "/" + month + "/" + year);
            }
        }, 2019, 01 - 1, 01);
        dialog.show();
    }
}
