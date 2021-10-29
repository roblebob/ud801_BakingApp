package com.roblebob.ud801_bakingapp.model;


import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(tableName = "AppState")
public class AppState {

    @PrimaryKey(autoGenerate = false)
    @NonNull
    private String key;

    @ColumnInfo(name = "value")
    private String value;

    public AppState(@NonNull String key, String value) {
        this.key = key;
        this.value = value;
    }

    public String getKey() { return this.key; }
    public String getValue() { return this.value; }

    public void setKey(String key) { this.key = key; }
    public void setValue(String value) {this.value = value; }
}
