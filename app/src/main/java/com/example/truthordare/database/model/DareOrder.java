package com.example.truthordare.database.model;

import androidx.annotation.Nullable;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "dare_table")
public class DareOrder {
    @PrimaryKey(autoGenerate = true)
    private int id;

    @Nullable
    private String  order;

    public DareOrder(){

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getOrder() {
        return order;
    }

    public void setOrder(String order) {
        this.order = order;
    }
}
