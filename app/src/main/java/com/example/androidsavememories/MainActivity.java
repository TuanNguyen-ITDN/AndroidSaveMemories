package com.example.androidsavememories;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.List;

public class MainActivity extends AppCompatActivity implements MemoryAdapter.OnItemClicked {
    RecyclerView recyclerviewMemory;
    AppDatabase db;
    MemoryAdapter memoryAdapter;
    public static List<Memory> memories;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        db = Room.databaseBuilder(getApplicationContext(),
                AppDatabase.class, "database-name").build();

        recyclerviewMemory = findViewById(R.id.recyclerview_id);
        recyclerviewMemory.setLayoutManager(new LinearLayoutManager((this)));

        final Button btn_Add = (Button) findViewById(R.id.btn_ADD);
        btn_Add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showAlertConfirm("Cofirm", "Would you like to add a new memory ");
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        getandDisplayTask();
    }

    public void getandDisplayTask() {
        new AsyncTask<Void, Void, List<Memory>>() {
            @Override
            protected List<Memory> doInBackground(Void... voids) {
                memories = db.memoryDao().getAll();

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        memoryAdapter = new MemoryAdapter(this, memories);
                        memoryAdapter.setOnClick(MainActivity.this);
                        recyclerviewMemory.setAdapter(memoryAdapter);
                        Toast.makeText(MainActivity.this, "size" + memories.size(), Toast.LENGTH_SHORT).show();
                    }
                });
                return null;
            }
        }.execute();
    }

    private void openUpdateTodoScreen(Memory memory) {
        Intent intent = new Intent(MainActivity.this, UpdateActivity.class);
        intent.putExtra("id", memory.getId());
        intent.putExtra("memory", memory.getDescription());
        startActivity(intent);
    }

    @Override
    public void onClickItemUpdate(int position) {
        openUpdateTodoScreen(memories.get(position));
    }

    @Override
    public void onClickItemDelete(final int position) {
        Log.i("TAG", "clicked at " + position);

        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                db.memoryDao().delete(memories.get(position));
                Log.i("TAG", "delete success");
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                memoryAdapter.memory.remove(position);
                memoryAdapter.notifyDataSetChanged();
            }
        }.execute();
    }


    private void showAlertConfirm(String title, String message) {
        new AlertDialog.Builder(this)
                .setTitle(title)
                .setMessage(message)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        openAddMemoryScreen();
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //Lam chi do
                    }
                })
                .show();
    }

    private void openAddMemoryScreen() {
        Intent intent = new Intent(MainActivity.this, AddMemory_Activity.class);
        startActivity(intent);
    }
}
