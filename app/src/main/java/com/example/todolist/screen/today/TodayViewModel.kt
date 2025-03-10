package com.example.todolist.screen.today

import android.app.Application
import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.room.RoomDatabase
import com.example.todolist.data.DaoDatabase
import com.example.todolist.data.model.Task
import com.example.todolist.data.repo.TaskRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class TodayViewModel @Inject constructor(private val repo: TaskRepo) : ViewModel() {

    val allTasks = repo.allTasks
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(1000), // flow fun after has subscriber, delay 1s
            initialValue = emptyList()
        )
    fun delete(task: Task) = viewModelScope.launch {
        repo.delete(task)
    }

    fun update(task: Task) = viewModelScope.launch {
        repo.update(task)
    }

    fun insert(task: Task) = viewModelScope.launch {
        repo.insert(task)
    }
}