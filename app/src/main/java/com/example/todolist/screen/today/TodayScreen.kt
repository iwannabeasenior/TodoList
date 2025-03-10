package com.example.todolist.screen.today

import android.hardware.lights.Light
import android.media.Image
import android.widget.EditText
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.todolist.R
import com.example.todolist.data.model.Task
import com.example.todolist.navigation.TopLevelDestination
import com.example.todolist.ui.theme.PriorityFourColor
import com.example.todolist.ui.theme.PriorityOneColor
import com.example.todolist.ui.theme.PriorityThreeColor
import com.example.todolist.ui.theme.PriorityTwoColor
import java.util.Date

@Preview
@Composable
internal fun TodayScreen(viewModel: TodayViewModel = hiltViewModel()) {
    // we need to calculate today task here
    var count by remember {
        mutableStateOf(0)
    }
    var showSheet by remember {
        mutableStateOf(false)
    }
    var tasks = viewModel.allTasks.collectAsState()
    Box(
    ) {
        Column(
            modifier = Modifier.fillMaxSize().clickable {
                if (showSheet) {
                    // TODO: show dialog Do you want to discard
                }
            },
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
                items(tasks.value) { task ->
                    TaskItem(task)
                }
                item {
                    AddTask {
                        showSheet = !showSheet
                    }
                }
            }
        }
        if (showSheet) AddTaskBottomSheet(viewModel = viewModel)
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
            Text("Add Task", fontSize = 20.sp)
        }
    }
}

@Composable
private fun TaskItem(task: Task) {
    var isChecked by remember {
        mutableStateOf(false)
    }
    Surface(
        shadowElevation = 3.dp,
        shape = RoundedCornerShape(10)
    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(10.dp),
            verticalAlignment = Alignment.Top,
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(30))
                .padding(10.dp)
        ) {
            IconButton(
                onClick = {
                    isChecked = true
                    // 0.5 second after that will disappear

                }
            ) {
                Icon(
                    imageVector = ImageVector.vectorResource(if (isChecked) R.drawable.ic_checked else R.drawable.ic_unchecked),
                    "",
                    modifier = Modifier.size(30.dp)
                )
            }
            Column(
                verticalArrangement = Arrangement.spacedBy(5.dp)
            ) {
                Text(task.content, fontSize = 20.sp)

                if (task.description != null) Text(
                    task.description,
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Light
                )

                Row(
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(5.dp)
                    ) {
                        Icon(
                            imageVector = ImageVector.vectorResource(R.drawable.ic_today),
                            "",
                            modifier = Modifier.size(20.dp),
                            tint = Color.Green.copy(alpha = 0.5f)
                        )
                        Text("Today", color = Color.Green.copy(alpha = 0.5f))
                    }
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(5.dp)
                    ) {
                        Icon(
                            imageVector = ImageVector.vectorResource(R.drawable.ic_inbox),
                            "",
                            modifier = Modifier.size(20.dp)
                        )
                        Text("Inbox")
                    }
                }
            }
        }
    }
}


enum class Priority(val priority: Int, val color: Color) {
    One(1, PriorityOneColor),
    Two(2, PriorityTwoColor),
    Three(3, PriorityThreeColor),
    Four(4, PriorityFourColor)
}