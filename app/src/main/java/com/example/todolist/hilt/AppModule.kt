package com.example.todolist.hilt

import android.content.Context
import com.example.todolist.data.DaoDatabase
import com.example.todolist.data.dao.TaskDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun provideRoomDatabase(@ApplicationContext context: Context): DaoDatabase {
        return DaoDatabase.getDatabase(context)
    }

    @Provides
    fun provideTaskDao(db: DaoDatabase): TaskDao {
        return db.taskDao()
    }
}