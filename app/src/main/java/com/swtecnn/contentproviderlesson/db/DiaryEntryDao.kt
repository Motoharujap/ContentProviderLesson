package com.swtecnn.contentproviderlesson.db

import android.database.Cursor
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface DiaryEntryDao {
    @Query("SELECT id, entry_text, entry_date from DiaryEntry")
    fun getAll(): Cursor

    @Insert
    fun addEntry(entry: DiaryEntry): Long?

    @Insert
    fun addAll(entries: List<DiaryEntry>)
}