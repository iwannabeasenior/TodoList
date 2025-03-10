package com.example.todolist.screen.today

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.snapping.SnapPosition
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.graphics.drawable.RoundedBitmapDrawable
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.todolist.R
import com.example.todolist.data.model.Task

@Preview
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddTaskBottomSheet(modifier: Modifier = Modifier, viewModel: TodayViewModel) {
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = false)
    val scope = rememberCoroutineScope()
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
    ModalBottomSheet(
        onDismissRequest = {

        },
        sheetState = sheetState
    ) {
        Column(
            modifier = Modifier
                .height(300.dp)
                .fillMaxWidth()
                .clip(RoundedCornerShape(topEnd = 10f, topStart = 10f))
                .background(Color.White)
        ) {
            TextField(
                value = taskName,
                onValueChange = {
                    taskName = it
                },
                label = {
                    Text(
                        "Task Name",
                        fontSize = 15.sp,
                        color = Color.Black.copy(0.5f)
                    )
                },
                modifier = Modifier.fillMaxWidth()
            )
            TextField(
                value = description,
                onValueChange = {
                    description = it
                },
                label = {
                    Text(
                        "Description",
                        fontSize = 10.sp,
                        color = Color.Black.copy(0.3f)
                    )
                },
                modifier = Modifier.fillMaxWidth()
            )
            Row {
                // pick a date
//                LazyRow {
//                    TaskAtributeContainter(
//
//                    )
//                }
                // priority
                // reminders
            }

            HorizontalDivider(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 10.dp),
                thickness = 1.dp,
                color = Color.Gray
            )

            Row (
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 10.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row (
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = ImageVector.vectorResource(R.drawable.ic_inbox), "", modifier = Modifier.size(20.dp)
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
                            viewModel.insert(Task(content = taskName, state = false))
                        }
                        .graphicsLayer(alpha = if (canAdd) 1f else 0.5f)
                        .wrapContentSize(Alignment.Center),
                )
            }
        }
    }

//    fun canAddTask() : Boolean {
//
//    }
}

@Composable
private fun TaskAtributeContainter(iconId: Int, titleId: Int) {
    Row(
        modifier = Modifier
            .clip(RoundedCornerShape(10))
            .padding(10.dp)
    ) {
        Icon(imageVector = ImageVector.vectorResource(iconId), "")
        Text(stringResource(titleId), fontSize = 10.sp)
    }
}
