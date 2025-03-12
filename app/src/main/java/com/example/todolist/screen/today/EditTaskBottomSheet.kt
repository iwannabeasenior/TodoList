package com.example.todolist.screen.today

import androidx.compose.animation.Crossfade
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.todolist.R
import com.example.todolist.data.model.Task
import com.example.todolist.screen.today.component.CheckButton
import com.example.todolist.screen.today.component.MyDatePickerDialog
import com.example.todolist.screen.today.component.PriorityContainer
import com.example.todolist.screen.today.component.TaskItem
import com.example.todolist.screen.today.component.TopBarEditTextMode
import com.example.todolist.screen.today.component.TopBarNonEditTextMode
import kotlinx.coroutines.ExperimentalCoroutinesApi


enum class EditMode {
    EditText,
    NonEditText
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalCoroutinesApi::class)
@Composable
fun EditTaskBottomSheet(
    viewModel: TodayViewModel,
    task: Task,
    onClickShowSheet: () -> Unit,
    onDismiss: () -> Unit,
) {
    var subTasks = viewModel.subTasks.collectAsState()

    var selectedDate by remember {
        run {
            val state = mutableStateOf(task.date!!)
            object: MutableState<String> by state {
                override var value: String
                    get() = state.value
                    set(newValue) {
                        if (newValue != state.value) {
                            state.value = newValue
                            val updatedTask = task.copy(date = state.value)
                            viewModel.update(updatedTask)
                        }

                    }
            }
        }
    }

    var openDatePickerDialog by remember {
        mutableStateOf(false)
    }


    var selectedPriority by remember  {
        run {
            val state = mutableStateOf(Priority.from(task.priority))
            object: MutableState<Priority> by state {
                override var value: Priority
                    get() = state.value
                    set(newValue) {
                        if (newValue != state.value){
                            state.value = newValue
                            val updatedTask = task.copy(priority = value.priority)
                            viewModel.update(updatedTask)
                        }
                    }
            }
        }
    }

    var editMode by remember {
        mutableStateOf(EditMode.NonEditText)
    }

    val focusManager = LocalFocusManager.current

    val keyboardController = LocalSoftwareKeyboardController.current

    var content by remember {
        mutableStateOf(task.content)
    }

    var description by remember {
        mutableStateOf(task.description)
    }

    var tempContent by remember {
        mutableStateOf(task.content)
    }

    var tempDescription by remember  {
        mutableStateOf(task.description)
    }

    val enabledSaveButton by remember {
        derivedStateOf {
            content != tempContent || description != tempDescription
        }
    }

    fun unFocusEverything() {
        focusManager.clearFocus()
        keyboardController?.hide()
    }

    fun onSaveClick() {
        content = tempContent
        description = tempDescription
        val updatedTask = task.copy(content = content, description = description)
        viewModel.update(updatedTask)
//        editMode = EditMode.NonEditText
        unFocusEverything()
    }

    fun onCancelClick() {
        tempContent = content
        tempDescription = description
//        editMode = EditMode.NonEditText
        unFocusEverything()
    }


    ModalBottomSheet(onDismissRequest = { onDismiss() }, sheetState = rememberModalBottomSheetState()) {
        Box(
            modifier = Modifier
                .pointerInput(null) {
                    detectTapGestures {
                        unFocusEverything()
                    }
                }
        ) {
            Column {
                Crossfade(targetState = editMode) { mode ->
                    if (mode == EditMode.NonEditText) {
                        TopBarNonEditTextMode()
                    } else {
                        TopBarEditTextMode(
                            enabled = enabledSaveButton,
                            onSaveClick = ::onSaveClick,
                            onCancleClick = ::onCancelClick
                        )
                    }
                }
                Column {
                    Row {
                        CheckButton(task = task) {
                            onDismiss()
                            viewModel.onTaskChecked(task)
                        }
                        TextField(
                            value = tempContent,
                            onValueChange = {
                                tempContent = it
                            },
                            textStyle = TextStyle(
                                fontWeight = FontWeight.Bold,
                                fontSize = 15.sp
                            ),
                            modifier = Modifier
                                .fillMaxWidth()
                                .onFocusChanged { focusState ->
                                    editMode = if (focusState.isFocused) {
                                        EditMode.EditText
                                    } else {
                                        EditMode.NonEditText
                                    }
                                },
                            colors = TextFieldDefaults.colors(
                                focusedContainerColor = Color.White,
                                unfocusedContainerColor = Color.White,
                                disabledIndicatorColor = Color.Transparent,
                                unfocusedIndicatorColor = Color.Transparent,
                                focusedIndicatorColor = Color.Transparent
                            )
                        )
                    }
                    tempDescription?.let {
                        TextField(
                            value = it,
                            onValueChange = {
                                tempDescription = it
                            },
                            textStyle = TextStyle(
                                fontWeight = FontWeight.Medium,
                                fontSize = 10.sp
                            ),
                            modifier = Modifier
                                .fillMaxWidth()
                                .onFocusChanged { focusState ->
                                    editMode = if (focusState.isFocused) {
                                        EditMode.EditText
                                    } else {
                                        EditMode.NonEditText
                                    }
                                },
                            colors = TextFieldDefaults.colors(
                                focusedContainerColor = Color.White,
                                unfocusedContainerColor = Color.White,
                                disabledIndicatorColor = Color.Transparent,
                                unfocusedIndicatorColor = Color.Transparent,
                                focusedIndicatorColor = Color.Transparent
                            )

                        )
                    }
                }
                Crossfade(targetState = editMode) { mode ->
                    if (mode == EditMode.NonEditText) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(15.dp)
                        ) {
                            DatePickerContainer(
                                iconId = R.drawable.ic_today,
                                titleId = R.string.title_today,
                                date = selectedDate
                            ) {
                                openDatePickerDialog = true
                            }

                            PriorityContainer(
                                iconId = R.drawable.ic_today,
                                titleId = R.string.title_menu,
                                selectedOption = selectedPriority
                            ) {
                                selectedPriority = it
                            }
                        }
                    }
                }

                // SubTasks Section
                LazyColumn {
                    items(subTasks.value) { subTask ->
                        TaskItem(task = subTask, onItemClicked = { viewModel.selectTask(subTask) }) {
                            viewModel.onTaskChecked(subTask)
                        }
                    }
                    item {
                        AddTask {
                            onClickShowSheet()
                        }
                    }
                }
            }
            // select date section
            if (openDatePickerDialog) {
                MyDatePickerDialog(
                    selectedDate = selectedDate,
                    changeDate = { selectedDate = it }
                ) {
                    openDatePickerDialog = false
                }
            }
        }
    }
}






