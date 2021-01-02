package com.example.truthordare.database.model;

import androidx.annotation.Nullable;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "truth_table")
public class TruthQuestions {
    @PrimaryKey(autoGenerate = true)
    private int id;
    @Nullable
    private String question;

    public TruthQuestions(){}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }
}
