package com.example.todolist.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.todolist.data.model.Task
import kotlinx.coroutines.flow.Flow


@Dao
interface TaskDao {

    @Query("SELECT * FROM task WHERE uid = :uid")
    fun getTaskByUID(uid: Int): Flow<Task>

    @Query("SELECT * FROM task")
    fun getAll(): Flow<List<Task>>

    @Update
    suspend fun updateTask(task: Task)

    @Delete
    suspend fun deleteTask(task: Task)

    @Insert
    suspend fun insertTask(task: Task)

    @Query("SELECT * FROM task WHERE parent_task_id = :parentTaskId")
    fun getSubTasks(parentTaskId: Int): Flow<List<Task>>
}