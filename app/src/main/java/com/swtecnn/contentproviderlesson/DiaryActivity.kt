package com.swtecnn.contentproviderlesson

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.swtecnn.contentproviderlesson.databinding.ActivityDiaryBinding

class DiaryActivity : AppCompatActivity() {
    private var _binding: ActivityDiaryBinding? = null
    private val binding: ActivityDiaryBinding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityDiaryBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}