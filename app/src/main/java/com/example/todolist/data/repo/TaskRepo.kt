package com.example.todolist.data.repo

import com.example.todolist.data.dao.TaskDao
import com.example.todolist.data.model.Task
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class TaskRepo @Inject constructor(private val taskDao: TaskDao) {

    val allTasks: Flow<List<Task>> = taskDao.getAll()

    suspend fun insert(task: Task) {
        taskDao.insertTask(task)
    }

    suspend fun delete(task: Task) {
        taskDao.deleteTask(task)
    }

    suspend fun update(task: Task) {
        taskDao.updateTask(task)
    }

    fun getSubTasks(parentTaskId: Int): Flow<List<Task>> {
        return taskDao.getSubTasks(parentTaskId)
    }
}