package com.example.uts;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface TaskDao {

    @Insert
    void insert(Task task);

    @Update
    void updateTask(Task task);

    @Delete
    void deleteTask(Task task);

    @Query("SELECT * FROM Task WHERE userId = :userId")
    List<Task> getTasksByUserId(int userId);

    @Query("SELECT * FROM Task WHERE id = :taskId LIMIT 1")
    Task getTaskById(int taskId);
}
