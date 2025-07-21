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

    @Query("SELECT * FROM Task WHERE userId = :userId")
    List<Task> getTasksByUser(int userId);

    // (opsional) tambahan lain jika kamu pakai update/delete:
    @Update
    void update(Task task);

    @Delete
    void delete(Task task);
}
