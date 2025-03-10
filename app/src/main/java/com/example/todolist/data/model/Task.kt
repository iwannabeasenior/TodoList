package com.example.todolist.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.todolist.screen.today.Priority
import java.util.Date


@Entity(tableName = "task")
data class Task(
    @PrimaryKey(autoGenerate = true) val uid: Int = 0,
    @ColumnInfo(name = "content") val content: String,
    @ColumnInfo(name = "description") val description: String? = null,
    @ColumnInfo(name = "state") val state: Boolean,
    @ColumnInfo(name = "date") val date: String? = null,
    @ColumnInfo(name = "dead_line") val deadline: String? = null,
    @ColumnInfo(name = "priority") val priority: Int? = null,
    @ColumnInfo(name = "reminder") val reminder: String? = null,
//    @ColumnInfo(name = "sub_tasks") val subTasks: List<Task>? = null,
)
