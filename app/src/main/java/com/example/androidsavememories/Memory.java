package com.example.androidsavememories;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Memory {
    @PrimaryKey(autoGenerate = true)
    public int id;
    public String description;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
