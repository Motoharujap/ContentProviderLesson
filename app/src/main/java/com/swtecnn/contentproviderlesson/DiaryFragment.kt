package com.swtecnn.contentproviderlesson

import android.database.Cursor
import android.os.AsyncTask
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.swtecnn.contentproviderlesson.content_providers.DbContentProvider
import com.swtecnn.contentproviderlesson.databinding.FragmentDiaryBinding
import com.swtecnn.contentproviderlesson.db.DiaryEntry

class DiaryFragment : Fragment() {
    private var _binding: FragmentDiaryBinding? = null
    private val binding: FragmentDiaryBinding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentDiaryBinding.inflate(inflater, container, false)
        val adapter = DiaryAdapter()
        binding.diaryRecyclerView.layoutManager = LinearLayoutManager(activity?.applicationContext)
        binding.diaryRecyclerView.adapter = adapter
        fillAdapter(adapter)
        return binding.root
    }

    private fun fillAdapter(adapter: DiaryAdapter) {
        QueryTask(adapter).execute(activity)
    }

    class QueryTask(private val adapter: DiaryAdapter): AsyncTask<FragmentActivity, Void, ArrayList<DiaryEntry>>(){
        override fun doInBackground(vararg activity: FragmentActivity): ArrayList<DiaryEntry> {
            val cursor: Cursor? =
                activity[0].contentResolver?.query(DbContentProvider.DIARY_TABLE_CONTENT_URI, null, null, null, null)
            val entries: ArrayList<DiaryEntry> = ArrayList()
            if (cursor != null) {
                while (cursor.moveToNext()) {
                    val entryId = cursor.getInt(cursor.getColumnIndex("id"))
                    val textEntry = cursor.getString(cursor.getColumnIndex("entry_text"))
                    val dateText = cursor.getString(cursor.getColumnIndex("entry_date"))
                    val diaryEntry = DiaryEntry(entryId, textEntry, dateText)
                    entries.add(diaryEntry)
                }
            }
            cursor?.close()
            return entries
        }

        override fun onPostExecute(result: ArrayList<DiaryEntry>?) {
            if (result != null) {
                adapter.setItems(result)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}