package com.example.todolist.screen.today.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.todolist.R
import com.example.todolist.data.model.Task

@Composable
fun TaskItem(task: Task, onItemClicked: () -> Unit, onTaskChecked: (Task) -> Unit) {
    Surface(
        shadowElevation = 3.dp,
        shape = RoundedCornerShape(10),
        onClick = onItemClicked
    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(10.dp),
            verticalAlignment = Alignment.Top,
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(30))
                .padding(10.dp)
        ) {
            CheckButton(task = task, onTaskChecked = onTaskChecked)

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
                        Text(task.date!!, color = Color.Green.copy(alpha = 0.5f))
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
