package com.swtecnn.contentproviderlesson.db

import android.database.Cursor
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface ToDoListEntryDao {
    @Insert
    fun addToDoAction(entry: ToDoListEntry): Long?

    @Query("SELECT * FROM todo_list order by due_date desc")
    fun getAll(): List<ToDoListEntry>

    @Query("SELECT * FROM todo_list order by due_date desc")
    fun getAllCursor(): Cursor

    @Query("SELECT * FROM todo_list WHERE id = :id")
    fun getById(id: Int): Cursor

    @Query("DELETE FROM todo_list WHERE id = :id")
    fun deleteById(id: Int): Int
    //update
}