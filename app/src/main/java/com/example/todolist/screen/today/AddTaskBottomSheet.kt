package com.example.todolist.screen.today

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuBoxScope
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MenuItemColors
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.todolist.R
import com.example.todolist.data.model.Task
import com.example.todolist.screen.today.component.MyDatePickerDialog
import com.example.todolist.screen.today.component.PriorityContainer
import kotlinx.coroutines.ExperimentalCoroutinesApi
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import kotlin.math.exp



// view type: add, edit, add subtask

@OptIn(ExperimentalMaterial3Api::class, ExperimentalCoroutinesApi::class)
@Composable
fun AddTaskBottomSheet(modifier: Modifier = Modifier, viewModel: TodayViewModel, onDismissSheet: () -> Unit) {


    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = false)

    val scope = rememberCoroutineScope()

    val sdf = SimpleDateFormat("dd//MM//yyyy", Locale.getDefault())

    var selectedDate by remember {
        mutableStateOf(sdf.format(System.currentTimeMillis()))
    }

    var taskName by remember {
        mutableStateOf("")
    }

    var description by remember {
        mutableStateOf("")
    }

    val canAdd by remember {
        derivedStateOf {
            taskName.isNotEmpty()
        }
    }

    var openDatePickerDialog by remember {
        mutableStateOf(false)
    }

    var selectedPriority by remember {
        mutableStateOf(Priority.Four)
    }

    fun hasData() : Boolean{
        return !(taskName.isEmpty() && description.isEmpty())
    }

    ModalBottomSheet(
        onDismissRequest = {
            onDismissSheet()
        },
        sheetState = sheetState,
    ) {
        Box(
        ){
            Column(
                modifier = Modifier
                    .height(300.dp)
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(topEnd = 10f, topStart = 10f))
                    .background(Color.White)
                    .padding(horizontal = 10.dp, vertical = 5.dp)
            ) {
                TextField(
                    value = taskName,
                    onValueChange = {
                        taskName = it
                    },
                    placeholder = {
                        Text(
                            "Task Name",
                            fontSize = 15.sp,
                            color = Color.Black.copy(0.5f)
                        )
                    },
                    modifier = Modifier.fillMaxWidth(),
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = Color.White,
                        unfocusedContainerColor = Color.White,
                        disabledIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent,
                        focusedIndicatorColor = Color.Transparent
                    )
                )
                TextField(
                    value = description,
                    onValueChange = {
                        description = it
                    },
                    placeholder = {
                        Text(
                            "Description",
                            fontSize = 10.sp,
                            color = Color.Black.copy(0.3f)
                        )
                    },
                    modifier = Modifier.fillMaxWidth(),
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = Color.White,
                        unfocusedContainerColor = Color.White,
                        disabledIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent,
                        focusedIndicatorColor = Color.Transparent
                    )
                )
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    DatePickerContainer(
                        iconId = R.drawable.ic_today,
                        titleId = R.string.title_today,
                        date = selectedDate
                    ) {
                        openDatePickerDialog = true
                    }

                    PriorityContainer(iconId = R.drawable.ic_today, titleId = R.string.title_menu, selectedOption = selectedPriority) {
                        selectedPriority = it
                    }
                }

                HorizontalDivider(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 10.dp),
                    thickness = 1.dp,
                    color = Color.Gray
                )

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 10.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = ImageVector.vectorResource(R.drawable.ic_inbox),
                            "",
                            modifier = Modifier.size(20.dp)
                        )
                        Text("Inbox", fontSize = 10.sp)

                        Icon(Icons.Default.ArrowDropDown, "")
                    }
                    Icon(
                        painter = painterResource(R.drawable.ic_up_arrow),
                        "",
                        tint = Color.White,
                        modifier = Modifier
                            .size(30.dp)
                            .clip(CircleShape)
                            .background(Color.Red)
                            .clickable(
                                enabled = canAdd
                            ) {
                                viewModel.insert(task = Task(parentTaskId = viewModel.selectedTask.value?.uid, content = taskName, description = description, state = false, date = selectedDate, priority = selectedPriority.priority))
                            }
                            .graphicsLayer(alpha = if (canAdd) 1f else 0.5f)
                            .wrapContentSize(Alignment.Center),
                    )
                }
            }
            if (openDatePickerDialog) {
                MyDatePickerDialog(selectedDate = selectedDate, changeDate = { date -> selectedDate = date}) {
                    openDatePickerDialog = false
                }
            }
        }
    }
}

@Composable
internal fun DatePickerContainer(iconId: Int, titleId: Int, date: String, openDialog: () -> Unit) {
    Row(
        modifier = Modifier
            .clip(RoundedCornerShape(30))
            .border(1.dp, Color.Gray)
            .clickable {
                openDialog()
            }
            .padding(10.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(5.dp)
    ) {
        Icon(imageVector = ImageVector.vectorResource(iconId), "", modifier = Modifier.size(20.dp))
        Text(date, fontSize = 10.sp)
    }
}




