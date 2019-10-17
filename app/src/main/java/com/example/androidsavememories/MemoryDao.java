package com.example.androidsavememories;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;
@Dao
public interface MemoryDao {
    @Query("SELECT * FROM Memory")
    List<Memory> getAll();

    @Insert
    void insert(Memory memory);

    @Delete
    void delete(Memory memory);

    @Update
    void updateOne(Memory memory);
}
