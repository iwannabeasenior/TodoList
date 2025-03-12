package com.example.todolist.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.todolist.data.dao.TaskDao
import com.example.todolist.data.model.Task


@Database(entities = [Task::class], version = 1, exportSchema = false)
abstract class DaoDatabase : RoomDatabase() {
    abstract fun taskDao(): TaskDao
    companion object {
        /// guarantee that if one thread change INSTANCE, others thread can see the changes, avoid race-condition
        ///@Volatile đảm bảo mọi thread luôn thấy giá trị mới nhất của biến, tránh việc một thread đọc giá trị cũ do bộ nhớ đệm (cache).
        @Volatile
        private var INSTANCE: DaoDatabase? = null

        fun getDatabase(context: Context) : DaoDatabase {
            return INSTANCE ?: synchronized(this) { // guarantee only one thread can execute this block at one time
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    DaoDatabase::class.java,
                    "dao_database"
                ).fallbackToDestructiveMigration().build()
                INSTANCE = instance
                instance
            }
        }
    }

}