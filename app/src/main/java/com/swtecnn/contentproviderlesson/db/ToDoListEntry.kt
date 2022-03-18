package com.swtecnn.contentproviderlesson.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "todo_list")
class ToDoListEntry(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    @ColumnInfo(name = "action")
    val action: String,
    @ColumnInfo(name = "due_date")
    val dueDate: String,
    @ColumnInfo(name = "is_completed")
    val isCompleted: Boolean
    )