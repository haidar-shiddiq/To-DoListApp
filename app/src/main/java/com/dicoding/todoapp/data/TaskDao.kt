package com.dicoding.todoapp.data

import androidx.lifecycle.LiveData
import androidx.paging.DataSource
import androidx.room.*
import androidx.sqlite.db.SupportSQLiteQuery
import com.dicoding.todoapp.utils.TABLE_NAME

//TODO 2 : Define data access object (DAO) done
@Dao
interface TaskDao {

    @RawQuery(observedEntities = [Task::class])
    fun getTasks(query: SupportSQLiteQuery): DataSource.Factory<Int, Task>

    @Query("SELECT * FROM $TABLE_NAME WHERE completed = 0 ORDER BY dueDateMillis ASC")
    fun getNearestActiveTask(): Task

    @Query("select * from $TABLE_NAME where id = :taskId")
    fun getTaskById(taskId: Int): LiveData<Task>

    @Insert
    suspend fun insertTask(task: Task): Long

    @Insert
    fun insertAll(vararg tasks: Task)

    @Delete
    suspend fun deleteTask(task: Task)

    @Query("update $TABLE_NAME set completed = :completed where id = :taskId")
    suspend fun updateCompleted(taskId: Int, completed: Boolean)
    
}
