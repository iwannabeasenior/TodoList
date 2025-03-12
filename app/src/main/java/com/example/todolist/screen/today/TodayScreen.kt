package com.example.todolist.screen.today

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.todolist.screen.today.component.TaskItem
import kotlinx.coroutines.ExperimentalCoroutinesApi

@OptIn(ExperimentalCoroutinesApi::class)
@Preview
@Composable
internal fun TodayScreen(viewModel: TodayViewModel = hiltViewModel()) {
    // we need to calculate today task here
    var showAddTask by remember {
        mutableStateOf(false)
    }

    val undoTasks = viewModel.undoTasks.collectAsState()

    val subTasks = viewModel.subTasks.collectAsState()

    val count by remember {
        derivedStateOf {
            undoTasks.value.size
        }
    }

    var showEditTask by remember {
        run {
            val state = mutableStateOf(false)
            object : MutableState<Boolean> by state {
                override var value: Boolean
                    get() = state.value
                    set(newValue) {
                        state.value = newValue
                        if (!newValue) {
                             viewModel.clearSelectedTask()
                        }
                    }
            }
        }
    }

    var selectedTask = viewModel.selectedTask.collectAsState()

    Box(
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            Row(
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                Text("Inbox", fontSize = 30.sp, fontWeight = FontWeight.Bold)
                Text(count.toString(), fontSize = 30.sp, fontWeight = FontWeight.Light)
            }
            LazyColumn(
                userScrollEnabled = true,
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                items(undoTasks.value, key = { task -> task.uid!! }) { task ->
                    TaskItem(
                        task,
                        onItemClicked = {
                            showEditTask = true
                            viewModel.selectTask(task)
                        }
                    ) {
                        viewModel.onTaskChecked(it)
                    }
                }
                item {
                    AddTask {
                        showAddTask = !showAddTask
                    }
                }
            }
        }

        if (showAddTask) AddTaskBottomSheet(viewModel = viewModel) {
            showAddTask = false
        }

        if (showEditTask) {
            EditTaskBottomSheet(
                viewModel = viewModel,
                task = selectedTask.value!!,
                onClickShowSheet = {
                    showAddTask = !showAddTask
                }
            ) {
                showEditTask = false
            }
        }
    }

}


@Composable
fun AddTask(modifier: Modifier = Modifier, onClickShowSheet: () -> Unit) {
    Surface(
        shape = RoundedCornerShape(10),
        shadowElevation = 3.dp
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(20))
                .padding(horizontal = 15.dp, vertical = 10.dp)
                .clickable {
                    // show dialog
                    onClickShowSheet.invoke()
                },
            horizontalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            Icon(
                Icons.Default.Add,
                tint = Color.Red,
                contentDescription = ""
            )
            Text("Add Task / SubTask", fontSize = 20.sp)
        }
    }
}

