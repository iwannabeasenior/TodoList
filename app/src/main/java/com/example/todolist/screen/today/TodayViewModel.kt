package com.example.todolist.screen.today

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.todolist.data.model.Task
import com.example.todolist.data.repo.TaskRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject


@ExperimentalCoroutinesApi
@HiltViewModel
class TodayViewModel @Inject constructor(private val repo: TaskRepo) : ViewModel() {

    val undoTasks = repo.allTasks
        .map { tasks -> tasks.filter { task -> task.parentTaskId == null && !task.state } }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(1000), // flow fun after has subscriber, delay 1s
            initialValue = emptyList()
        )
    private val _selectedTask = MutableStateFlow<Task?>(null)

    val selectedTask: StateFlow<Task?> = _selectedTask

    val subTasks: StateFlow<List<Task>> = _selectedTask
        .filterNotNull()
        .flatMapLatest { task ->
            repo.getSubTasks(task.uid!!)
        }
        .stateIn(viewModelScope, SharingStarted.Lazily, emptyList())

    fun selectTask(task: Task) {
        _selectedTask.value = task
    }

    fun clearSelectedTask() {
        _selectedTask.value = null
    }

    fun delete(task: Task) = viewModelScope.launch(Dispatchers.IO) {
        repo.delete(task)
    }

    fun update(task: Task) = viewModelScope.launch(Dispatchers.IO) {
        repo.update(task)
    }

    fun insert(task: Task) = viewModelScope.launch(Dispatchers.IO) {
        repo.insert(task)
    }

    fun onTaskChecked(task: Task) {
        viewModelScope.launch(Dispatchers.IO) {
            val updatedTask = task.copy(state = true)
            repo.update(updatedTask)
        }
    }
}