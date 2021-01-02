package com.example.truthordare.database.dao;

import androidx.room.Dao;
import androidx.room.Query;

import com.example.truthordare.database.model.DareOrder;
import com.example.truthordare.database.model.TruthQuestions;

import io.reactivex.Single;

@Dao
public  interface QuestionsDao {
    @Query("SELECT * FROM truth_table ORDER BY RANDOM() LIMIT 1")
    Single<TruthQuestions> getQuestion();

    @Query("SELECT * FROM dare_table ORDER BY RANDOM() LIMIT 1")
    Single<DareOrder> getOrder();
}
