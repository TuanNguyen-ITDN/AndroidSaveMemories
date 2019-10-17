package com.example.androidsavememories;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class UpdateActivity extends AppCompatActivity {
    AppDatabase db;
    EditText editMemory;
    Button btnUpdate;
    int memoryId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);

        db = Room.databaseBuilder(getApplicationContext(),
                AppDatabase.class, "database-name").build();

        editMemory = findViewById(R.id.edit_text_Memory);
        btnUpdate = findViewById(R.id.btn_Update);

        int id = getIntent().getIntExtra("id", 0);
        memoryId = id;
        String memory = getIntent().getStringExtra("memory");

        editMemory.setText(memory);

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateTodoToDatabase();

            }
        });
    }

    private void updateTodoToDatabase() {
        final String memory = editMemory.getText().toString();

        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                Memory newMemory = new Memory();
                newMemory.setDescription(memory);
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
}
