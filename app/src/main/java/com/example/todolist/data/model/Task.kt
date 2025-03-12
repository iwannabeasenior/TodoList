package com.example.todolist.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import com.example.todolist.screen.today.Priority
import java.util.Date


@Entity(tableName = "task",
    foreignKeys = [
        ForeignKey(
            entity = Task::class,
            parentColumns = ["uid"],
            childColumns = ["parent_task_id"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [Index("parent_task_id")]
)
data class Task(
    @PrimaryKey(autoGenerate = true) val uid: Int? = null,
    @ColumnInfo(name = "content") val content: String,
    @ColumnInfo(name = "description") val description: String? = null,
    @ColumnInfo(name = "state") var state: Boolean,
    @ColumnInfo(name = "date") val date: String? = null,
    @ColumnInfo(name = "dead_line") val deadline: String? = null,
    @ColumnInfo(name = "priority") val priority: Int? = null,
    @ColumnInfo(name = "reminder") val reminder: String? = null,
    @ColumnInfo(name = "parent_task_id") val parentTaskId: Int? = null
)
