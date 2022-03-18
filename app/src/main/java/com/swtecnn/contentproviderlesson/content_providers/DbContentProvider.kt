package com.swtecnn.contentproviderlesson.content_providers

import android.content.ContentProvider
import android.content.ContentValues
import android.content.UriMatcher
import android.database.Cursor
import android.net.Uri
import com.swtecnn.contentproviderlesson.db.AppDatabase
import com.swtecnn.contentproviderlesson.db.DiaryEntry
import com.swtecnn.contentproviderlesson.db.DiaryEntryDao
import com.swtecnn.contentproviderlesson.db.ToDoListEntryDao

class DbContentProvider: ContentProvider() {
    // content://<authority>/<path>/<id>
    companion object {
        const val AUTHORITY = "com.swtecnn.contentproviderlesson.DbContentProvider"
        private val DIARY_ENTRY_TABLE = "DiaryEntry"
        private val TO_DO_LIST_ENTRY_TABLE = "todo_list"
        val DIARY_TABLE_CONTENT_URI: Uri = Uri.parse("content://" +
        AUTHORITY + "/" + DIARY_ENTRY_TABLE)
    }

    private val DIARY_ALL_ENTRIES = 1
    private val DIARY_ENTRY_BY_ID = 2

    private val TODO_LIST_GET_ALL = 11
    private val TODO_LIST_GET_BY_ID = 12
    private var diaryEntryDao: DiaryEntryDao? = null
    private  var toDoListDao: ToDoListEntryDao? = null
    private val sUriMatcher = UriMatcher(UriMatcher.NO_MATCH)

    init {
        sUriMatcher.addURI(AUTHORITY, DIARY_ENTRY_TABLE, DIARY_ALL_ENTRIES)
        sUriMatcher.addURI(AUTHORITY, TO_DO_LIST_ENTRY_TABLE, TODO_LIST_GET_ALL)
        sUriMatcher.addURI(AUTHORITY, "$TO_DO_LIST_ENTRY_TABLE/#", TODO_LIST_GET_BY_ID)
    }


    override fun onCreate(): Boolean {
        diaryEntryDao = context?.let { AppDatabase.getInstance(it).diaryEntryDao() }
        toDoListDao = context?.let { AppDatabase.getInstance(it).toDoListDao() }
        return true
    }

    override fun query(
        uri: Uri,
        projection: Array<out String>?,
        selection: String?,
        selectionArgs: Array<out String>?,
        sortOrder: String?
    ): Cursor? {
        val cursor: Cursor? = when(sUriMatcher.match(uri)){
            DIARY_ALL_ENTRIES -> {
                diaryEntryDao?.getAll()
            }
            TODO_LIST_GET_ALL ->{
                toDoListDao?.getAllCursor()
            }
            TODO_LIST_GET_BY_ID ->{
                uri.lastPathSegment?.toInt()?.let { toDoListDao?.getById(it) }
            }
            else -> throw IllegalArgumentException("Uknown Uri")
        }
        return cursor
    }

    override fun getType(uri: Uri): String {
        return "text"
    }

    override fun insert(uri: Uri, values: ContentValues?): Uri? {
        val uriType = sUriMatcher.match(uri)
        val id: Long?
        when (uriType) {
            DIARY_ALL_ENTRIES -> {
                val newEntry = DiaryEntry.fromContentValues(values)
                id = newEntry?.let { diaryEntryDao?.addEntry(it) }
            }
            else -> throw UnsupportedOperationException()
        }
        context?.contentResolver?.notifyChange(uri, null)
        return Uri.parse("$DIARY_ENTRY_TABLE/$id")
    }

    override fun delete(uri: Uri, selection: String?, selectionArgs: Array<out String>?): Int {
        TODO("Not yet implemented")
    }

    override fun update(
        uri: Uri,
        values: ContentValues?,
        selection: String?,
        selectionArgs: Array<out String>?
    ): Int {
        TODO("Not yet implemented")
    }
}